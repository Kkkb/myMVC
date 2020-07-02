package myMVC.Route;

import myMVC.freemarkerTemplate;
import myMVC.Request;
import myMVC.Utility;
import myMVC.models.Message;
import myMVC.models.User;
import myMVC.service.MessageService;
import myMVC.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class Route {

    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/", Route::routeIndex);
        map.put("/message", Route::routeMessage);
        map.put("/static", Route::routeImage);
        return map;
    }

    public static byte[] routeMessage(Request request) {
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String response;
        HashMap<String, String> data;

        if (request.method.equals("GET")) {
            data = request.query;
        } else if (request.method.equals("POST")) {
            data = request.form;
        } else {
            String e = String.format("unknow method: <%s>", request.method);
            throw new RuntimeException(e);
        }

        User curUser = UserService.currentUser(request);

        if (data != null) {
            data.put("author", curUser.username);
            if (data.get("message").strip().length() > 0) {
                MessageService.add(data);
            }
        }

        ArrayList<Message> messageList = MessageService.load();
        HashMap<String, Object> d = new HashMap<>();
        d.put("messageList", messageList);
        d.put("curUser", curUser);
        String body = freemarkerTemplate.render(d, "message.ftl" );

        response = header + body;
        return response.getBytes();
    }

    public static byte[] routeIndex(Request request) {
        User curUser = UserService.currentUser(request);

        ArrayList<Message> messageList = MessageService.load();

        HashMap<String, Object> d = new HashMap<>();
        d.put("curUser", curUser);
        d.put("messageList", messageList);
        String body = freemarkerTemplate.render(d, "index.ftl");

        String response = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] routeImage(Request request) {
        HashMap<String, String> data = request.query;
        String fileName = data.get("file");
        String dir = "static";
        String filePath = String.format("%s/%s", dir, fileName);

        String contentType = "";

        if (fileName.endsWith("css")) {
            contentType = "text/css; charset=utf-8";
        } else if (fileName.endsWith("js")) {
            contentType = "application/javascript; charset=utf-8";
        } else {
            contentType = "image/gif";
        }

        // body
        String header = String.format("HTTP/1.1 200 very OK\r\nContent-Type: %s;\r\n\r\n", contentType);
        byte[] body = new byte[1];
        try (InputStream is = Utility.fileStream(filePath)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] part1 = header.getBytes(StandardCharsets.UTF_8);
        byte[] response = new byte[part1.length + body.length];
        System.arraycopy(part1, 0, response, 0, part1.length);
        System.arraycopy(body, 0, response, part1.length, body.length);

        // 也可以用 ByteArrayOutputStream
        // ByteArrayOutputStream response2 = new ByteArrayOutputStream();
        // response2.write(header.getBytes());
        // response2.write(body);
        return response;
    }

    public static byte[] route404(Request request) {
        String body = "<html><body><h1>404</h1><br><img src='/static?file=doge2.gif'></body></html>";
        String response = "HTTP/1.1 404 NOT OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes();
    }

    public static byte[] redirect(String path) {
        String response = String.format("HTTP/1.1 302 very OK\r\nContent-Type: text/html;\r\nLocation: %s\r\n\r\n", path);
        return response.getBytes(StandardCharsets.UTF_8);
    }
}
