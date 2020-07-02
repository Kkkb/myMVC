package myMVC.Route;

import myMVC.freemarkerTemplate;
import myMVC.Request;
import myMVC.Utility;
import myMVC.models.Todo;
import myMVC.models.User;
import myMVC.service.TodoService;
import myMVC.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RouteTodo {

    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/todo", RouteTodo::index);
        map.put("/todo/add", RouteTodo::add);
        map.put("/todo/delete", RouteTodo::delete);
        map.put("/todo/edit", RouteTodo::edit);
        map.put("/todo/update", RouteTodo::update);
        map.put("/todo/completed", RouteTodo::completed);

        return map;
    }

    public static byte[] index(Request request) {
        ArrayList<Todo> todoList = TodoService.allBySQL();
        User curUser = UserService.currentUser(request);

        HashMap<String, Object> d = new HashMap<>();
        d.put("todoList", todoList);
        d.put("curUser", curUser);
        String body = freemarkerTemplate.render(d, "todo_index.ftl");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        String response = Utility.responseWithHeader(200, headers, body);;
        return response.getBytes(StandardCharsets.UTF_8);
    }


    public static byte[] delete(Request request) {
        HashMap<String, String> data;
        data = request.query;
        Integer id = Integer.valueOf(data.get("id"));

        TodoService.deleteBySQL(id);
        return Route.redirect("/todo");
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

        if (data != null) {
            if (data.get("content").length() > 0) {
//                TodoService.add(data);
                String content = data.get("content");
                TodoService.addBySQL(content);
            }
        }

        return Route.redirect("/todo");
    }

    public static byte[] edit(Request request) {
        HashMap<String, String> data;
        data = request.query;
        Integer id = Integer.valueOf(data.get("id"));

        Todo t = TodoService.findByIdSQL(id);
        String oldContent = t.content;
        Utility.log("oldContent: %s t: %s" , oldContent, t);
        String body = Utility.html("todo_edit.html");
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
//        TodoService.updateContent(id, content);
        TodoService.updateBySQL(id, content);
        return Route.redirect("/todo");
    }

    public static byte[] completed(Request request) {
        HashMap<String, String> data;
        data = request.query;

        Integer id = Integer.valueOf(data.get("id"));
//        TodoService.completed(id);

        return Route.redirect("/todo");
    }
}
