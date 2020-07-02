package myMVC.Route;

import myMVC.Request;
import myMVC.Utility;
import myMVC.freemarkerTemplate;
import myMVC.models.Comment;
import myMVC.models.User;
import myMVC.service.CommentService;
import myMVC.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RouteComment {

    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/comment", RouteComment::index);
        map.put("/comment/add", RouteComment::add);
        map.put("/comment/delete", RouteComment::delete);
        map.put("/comment/edit", RouteComment::edit);
        map.put("/comment/update", RouteComment::update);
        map.put("/comment/completed", RouteComment::completed);
        map.put("/comment/addview", RouteComment::addView);
        return map;
    }

    public static byte[] index(Request request) {
        Utility.log("comment index");
        ArrayList<Comment> commentList = CommentService.load();
        User curUser = UserService.currentUser(request);

        Utility.log("commentList: %s", commentList);
        HashMap<String, Object> d = new HashMap<>();
        d.put("commentList", commentList);
        d.put("curUser", curUser);
        String body = freemarkerTemplate.render(d, "comment_index.ftl");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        String response = Utility.responseWithHeader(200, headers, body);;
        return response.getBytes(StandardCharsets.UTF_8);
    }


    public static byte[] delete(Request request) {
        HashMap<String, String> data;
        data = request.query;
        Integer id = Integer.valueOf(data.get("id"));

        CommentService.delete(id);
        return Route.redirect("/comment");
    }

    public static byte[] addView(Request request) {
        HashMap<String, String> data;
        data = request.query;
        Integer weiboId = Integer.valueOf(data.get("weiboId"));
        User curUser = UserService.currentUser(request);

        HashMap<String, Object> d = new HashMap<>();
        d.put("weiboId", weiboId);
        d.put("curUser", curUser);
        String body = freemarkerTemplate.render(d, "comment_add_view.ftl");
        String response = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes(StandardCharsets.UTF_8);
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
        Utility.log("add: %s", data);

        User currentUser = UserService.currentUser(request);
        Integer userId = currentUser.getId();
        if (data != null) {
            if (data.get("content").length() > 0) {
                CommentService.add(data, userId);
            }
        }

        return Route.redirect("/weibo");
    }

    public static byte[] edit(Request request) {
        HashMap<String, String> data;
        data = request.query;
        Integer id = Integer.valueOf(data.get("id"));

        Comment t = CommentService.findById(id);
        String oldContent = t.content;
        Utility.log("oldContent: %s t: %s" , oldContent, t);
        String body = Utility.html("comment_edit.html");
        body = body.replace("{id}", id.toString());
        body = body.replace("{oldContent}", oldContent);
        String response = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] update(Request request) {
        HashMap<String, String> form;
        form = request.form;

        String content = form.get("content");
        Integer id = Integer.valueOf(form.get("id"));
//        commentService.updateContent(id, content);
        CommentService.updateContent(id, content);
        return Route.redirect("/comment");
    }

    public static byte[] completed(Request request) {
        HashMap<String, String> data;
        data = request.query;

        Integer id = Integer.valueOf(data.get("id"));
//        commentService.completed(id);

        return Route.redirect("/comment");
    }
}
