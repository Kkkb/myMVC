package myMVC.Route;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import myMVC.Request;
import myMVC.Utility;
import myMVC.models.Todo;
import myMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RouteAjaxTodo {

    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/ajax/todo", RouteAjaxTodo::index);
        map.put("/ajax/todo/add", RouteAjaxTodo::add);
        map.put("/ajax/todo/delete", RouteAjaxTodo::delete);
        map.put("/ajax/todo/all", RouteAjaxTodo::all);
        map.put("/ajax/todo/update", RouteAjaxTodo::update);

        return map;
    }

    public static byte[] index(Request request) {
        String body = Utility.html("ajax_todo.ftl");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        String response = Utility.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] add(Request request) {
        String jsonString = request.body;
        JSONObject jsonForm = JSON.parseObject(jsonString);
        String content = jsonForm.getString("content");

        Todo todo = TodoService.addBySQL(content);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/json");

        String todoString = JSON.toJSONString(todo);
        String body = todoString;

        String response = Utility.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] delete(Request request) {
        String jsonString = request.body;
        JSONObject jsonForm = JSON.parseObject(jsonString);
        String idStr = jsonForm.getString("id");
        Integer id = Integer.valueOf(idStr);

        TodoService.deleteBySQL(id);
        Todo todo = TodoService.findByIdSQL(id);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/json");

        String todoString = JSON.toJSONString(todo);
        String body = todoString;

        String response = Utility.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] update(Request request) {
        String jsonString = request.body;
        Utility.log("jString: %s", jsonString);
        JSONObject jsonForm = JSON.parseObject(jsonString);
        String idStr = jsonForm.getString("id");
        Integer id = Integer.valueOf(idStr);
        String content = jsonForm.getString("content");

        Todo todo = TodoService.updateBySQL(id, content);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/json");

        String todoString = JSON.toJSONString(todo);
        String body = todoString;

        String response = Utility.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }


    public static byte[] all(Request request) {
        ArrayList<Todo> todos = TodoService.allBySQL();
        String todoString = JSON.toJSONString(todos);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/json");
        String body = todoString;
        String response = Utility.responseWithHeader(200, headers, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

}
