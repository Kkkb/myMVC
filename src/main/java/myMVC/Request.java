package myMVC;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Request {
    public String rawData;
    public String path;
    public String method;
    public String body;

    public HashMap<String, String> query;
    public HashMap<String, String> form;

    public HashMap<String, String> headers;
    public HashMap<String, String> cookies;


    private static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public Request(String rawRequest) {
        this.rawData = rawRequest;
        String[] parts = rawRequest.split("\r\n\r\n", 2);

        this.body = parts[1];

        String headers = parts[0];
        String[] lines = headers.split("\r\n");
        String requestLine = lines[0];
        String[] requestLineData = requestLine.split(" ");
        this.method = requestLineData[0];

        this.parseHeaders(headers);

        this.parsePath(requestLineData[1]);
        this.parseForm(this.body);
        log("query: %s", this.query);
        log("form: %s", this.form);

        this.parseCookie(this.headers);
        log("cookie: %s", this.cookies);
    }

    private void parseHeaders(String headerString) {
        this.headers = new HashMap<>();

        String[] lines = headerString.split("\r\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(":", 2);
            String k = parts[0].strip();
            String v = parts[1].strip();
            this.headers.put(k, v);
        }
    }

    private void  parseCookie(HashMap<String, String> headers) {
        if (headers.containsKey("Cookie")) {
            this.cookies = new HashMap<>();
            String cookie = headers.get("Cookie");
            String[] kv = cookie.split(";");
            for (String kvString: kv) {
                String[] args = kvString.split("=");
                if (args.length >= 2) {
                    String k = args[0].strip();
                    String v = args[1].strip();
                    this.cookies.put(k, v);
                } else {
                    String k = args[0].strip();
                    String v = args[0].strip();
                    this.cookies.put(k, v);
                }
            }
        } else {
            this.cookies = new HashMap<>();
        }

    }

    private void parsePath(String path) {
        // "/message?author=&message=111"
        Integer index = path.indexOf("?");

        if (index.equals(-1)) {
            this.path = path;
            this.query = null;
        } else {
            this.path = path.substring(0, index);
            String queryString = path.substring(index + 1);

            String[] args = queryString.split("&");

            this.query = new HashMap<>();
            // author=
            for (String e:args) {
                String[] kv = e.split("=", 2);
                String k = kv[0];
                String v = kv[1];
                v = URLDecoder.decode(v, StandardCharsets.UTF_8);
                this.query.put(k, v);
            }
        }
    }

    private void parseForm(String body) {
        String contentType = this.headers.get("Content-Type");
        if (contentType == null) {
            this.form = new HashMap<>();
        } else if (contentType.equals("application/x-www-form-urlencoded")) {
            if (body.strip().length() > 0) {
                // author=143&message=1234
                String[] args = body.split("&");
                this.form = new HashMap<>();

                // author=143
                for (String e:args) {
                    String[] kv = e.split("=", 2);
                    String k = kv[0];
                    String v = kv[1];
                    v = URLDecoder.decode(v, StandardCharsets.UTF_8);
                    this.form.put(k, v);
                }
            }
        } else if (contentType.equals("application/json")) {
            this.form = new HashMap<>();
        } else {
            this.form = new HashMap<>();
        }
    }
}

