package myMVC.Route;

import myMVC.Request;
import myMVC.Utility;
import myMVC.freemarkerTemplate;
import myMVC.models.Weibo;
import myMVC.models.User;
import myMVC.service.WeiboService;
import myMVC.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RouteWeibo {

    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/weibo", RouteWeibo::index);
        map.put("/weibo/add", RouteWeibo::add);
        map.put("/weibo/delete", RouteWeibo::delete);
        map.put("/weibo/edit", RouteWeibo::edit);
        map.put("/weibo/update", RouteWeibo::update);
        map.put("/weibo/completed", RouteWeibo::completed);

        return map;
    }

    public static byte[] index(Request request) {
        Utility.log("weibo index");
        ArrayList<Weibo> weiboList = WeiboService.load();
        User curUser = UserService.currentUser(request);

        HashMap<String, Object> d = new HashMap<>();
        d.put("weiboList", weiboList);
        d.put("curUser", curUser);
        String body = freemarkerTemplate.render(d, "weibo_index.ftl");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        String response = Utility.responseWithHeader(200, headers, body);;
        return response.getBytes(StandardCharsets.UTF_8);
    }


    public static byte[] delete(Request request) {
        HashMap<String, String> data;
        data = request.query;
        Integer id = Integer.valueOf(data.get("id"));

        WeiboService.delete(id);
        return Route.redirect("/weibo");
    }

    public static byte[] add(Request request) {
        HashMap<String, String> data;



        if (request.method.equals("GET")) {
            data = request.query;
        } else if (request.method.equals("POST")) {
            data = request.form;
        } else {
            String e = String.format("unknow method: <%s>", request.method);
            throw new RuntimeException(e);
        }

        User currentUser = UserService.currentUser(request);
        Integer userId = currentUser.getId();
        if (data != null) {
            if (data.get("content").length() > 0) {
                WeiboService.add(data, userId);
            }
        }

        return Route.redirect("/weibo");
    }

    public static byte[] edit(Request request) {
        User curUser = UserService.currentUser(request);

        HashMap<String, String> data;
        data = request.query;
        Integer id = Integer.valueOf(data.get("id"));

        Weibo t = WeiboService.findById(id);
        String oldContent = t.content;

        HashMap<String, Object> d = new HashMap<>();
        d.put("id", id);
        d.put("oldContent", oldContent);
        d.put("curUser", curUser);
        String body = freemarkerTemplate.render(d, "weibo_edit.ftl");

        String response = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] update(Request request) {
        HashMap<String, String> form;
        form = request.form;

        String content = form.get("content");
        Integer id = Integer.valueOf(form.get("id"));
        WeiboService.updateContent(id, content);
        return Route.redirect("/weibo");
    }

    public static byte[] completed(Request request) {
        HashMap<String, String> data;
        data = request.query;

        Integer id = Integer.valueOf(data.get("id"));

        return Route.redirect("/weibo");
    }
}
