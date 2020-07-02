package myMVC;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;

public class Utility {
    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static InputStream fileStream(String path) throws FileNotFoundException {
        String resource = String.format("%s.class", Utility.class.getSimpleName());
//        Utility.log("resource %s", resource);
//        Utility.log("resource path %s", Utility.class.getResource(""));
        var res = Utility.class.getResource(resource);
        if (res != null && res.toString().startsWith("jar:")) {
            // 打包后, templates 放在 jar 包的根目录下, 要加 / 才能取到
            // 不加 / 就是从 类的当前包目录下取
            path = String.format("/%s", path);
            InputStream is = Utility.class.getResourceAsStream(path);
            if (is == null) {
                throw new FileNotFoundException(String.format("在 jar 里面找不到 %s", path));
            } else {
                return is;
            }
        } else {
            path = String.format("build/resources/main/%s", path);
            return new FileInputStream(path);
        }
    }

    public static String html(String fileName) {
        String dir = "templates";
        String path = String.format("%s/%s", dir, fileName);

        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        byte[] body = new byte[1];
        try (InputStream is = fileStream(path)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String r = new String(body, StandardCharsets.UTF_8);
        return r;
    }

    public static void save(String fileName, String data) {
        try (FileOutputStream os = new FileOutputStream(fileName)) {
            os.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            String s = String.format(
                    "Save <%s> error <%s>",
                    fileName,
                    e
            );
            throw new RuntimeException(s);
        }
    }

    public static String load(String fileName) {
        byte[] data = new byte[1];
        try (FileInputStream is = new FileInputStream(fileName)) {
            data = is.readAllBytes();
        } catch (IOException e) {
            String s = String.format(
                    "Load <%s> error <%s>",
                    fileName,
                    e
            );
            throw new RuntimeException(s);
        }
        String r = new String(data, StandardCharsets.UTF_8);
        return r;
    }

    public static String responseWithHeader(Integer code, HashMap<String, String> headers, String body) {
        String line = String.format("HTTP/1.1 %s", code);
        StringBuilder sb = new StringBuilder();
        sb.append(line);
        for (String k :headers.keySet()) {
            String v = headers.get(k);
            sb.append("\r\n");
            String s = String.format("%s: %s;", k, v);
            sb.append(s);
        }
        sb.append("\r\n\r\n");
        sb.append(body);
        return sb.toString();
    }

    public static MysqlDataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("12345");
        dataSource.setServerName("127.0.0.1");
        dataSource.setDatabaseName("myMVC");

        // 用来设置时区和数据库连接的编码
        try {
            dataSource.setCharacterEncoding("UTF-8");
            dataSource.setServerTimezone("UTC");

            Utility.log("url: %s", dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
    }
}
