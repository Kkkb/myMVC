package myMVC.Route;

import myMVC.freemarkerTemplate;
import myMVC.Request;
import myMVC.Utility;
import myMVC.models.User;
import myMVC.models.UserRole;
import myMVC.service.SessionService;
import myMVC.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

public class RouteUser {

    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/login", RouteUser::login);
        map.put("/register", RouteUser::register);
        map.put("/logout", RouteUser::logout);
        map.put("/profile", RouteUser::profile);
        map.put("/admin/users", RouteUser::users);
        map.put("/admin/users/update", RouteUser::update);

        return map;
    }

    public static byte[] login(Request request) {
        HashMap<String, String> headers = new HashMap<>();

        HashMap<String, String> data;

        if (request.method.equals("GET")) {
            data = request.query;
        } else if (request.method.equals("POST")) {
            data = request.form;
        } else {
            String e = String.format("unknow method: <%s>", request.method);
            throw new RuntimeException(e);
        }

        User curUser = UserService.guest();
        String loginRseult = "";
        if (data != null) {
            String username = data.get("username");
            String password = data.get("password");
            if (UserService.validLogin(username, password)) {
                loginRseult = "登录成功";
                curUser = UserService.findByUsername(username);

                String sessionId = UUID.randomUUID().toString();
                SessionService.add(curUser.id, sessionId);

                headers.put("Set-cookie", String.format("session=%s", sessionId));

            } else {
                curUser = UserService.guest();
                loginRseult = "登录失败";
            }
        }

        HashMap<String, Object> d = new HashMap<>();
        d.put("curUser", curUser);
        d.put("loginRseult", loginRseult);
        String body = freemarkerTemplate.render(d, "login.ftl");

        headers.put("Content-Type", "text/html");
        String response = Utility.responseWithHeader(200, headers, body);
        return response.getBytes();
    }

    public static byte[] register(Request request) {

        HashMap<String, String> data;

        if (request.method.equals("GET")) {
            data = request.query;
        } else if (request.method.equals("POST")) {
            data = request.form;
        } else {
            String e = String.format("unknow method: <%s>", request.method);
            throw new RuntimeException(e);
        }

        String registerResult = "";
        if (data != null) {
            String username = data.get("username");
            String password = data.get("password");
            if (username.equals("") || password.equals("")) {
                registerResult = "注册失败";
            } else {
                UserService.add(data);
                registerResult = "注册成功";
            }
        }

        HashMap<String, Object> d = new HashMap<>();
        d.put("registerResult", registerResult);
        String body = freemarkerTemplate.render(d, "register.ftl");
        String response = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes();
    }

    public static byte[] logout(Request request) {
        Utility.log("logout");
        User u = UserService.currentUser(request);
        SessionService.delete(u.id);
        return Route.redirect("/");
    }

    public static byte[] profile(Request request) {
        HashMap<String, String> query = request.query;
        // ?u=kun
        if (query == null) {
            query = new HashMap<>();
        }

        String username = query.get("u");
        User curUser = UserService.findByUsername(username);

        HashMap<String, Object> d = new HashMap<>();
        d.put("curUser", curUser);
        String body = freemarkerTemplate.render(d, "profile.ftl");

        String response = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes();
    }

    public static byte[] users(Request request) {

        User current = UserService.currentUser(request);
        if (current.role.equals(UserRole.admin)) {
            ArrayList<User> userList = UserService.load();
            HashMap<String, Object> d = new HashMap<>();
            d.put("userList", userList);
            String body = freemarkerTemplate.render(d, "users.ftl");

            String response = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n" + body;
            return response.getBytes();
        } else {
            return Route.redirect("/login");
        }
    }

    public static byte[] update(Request request) {
        HashMap<String, String> form;
        form = request.form;

        String password = form.get("password");
        Integer id = Integer.valueOf(form.get("id"));
        UserService.updatePassword(id, password);

        return Route.redirect("/admin/users");
    }
}
