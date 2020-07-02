package myMVC;

import myMVC.Route.*;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

class MyServlet implements Runnable {
    Request request;
    Socket connection;

    public MyServlet(Socket connection, Request request) {
        this.connection = connection;
        this.request = request;
    }

    private static byte[] responseForPath(Request request) {
        String path = request.path;

        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.putAll(Route.routeMap());
        map.putAll(RouteUser.routeMap());
        map.putAll(RouteTodo.routeMap());
        map.putAll(RouteAjaxTodo.routeMap());
        map.putAll(RouteWeibo.routeMap());
        map.putAll(RouteComment.routeMap());

        byte[] response = map.getOrDefault(path, Route::route404).apply(request);

        return response;
    }

    @Override
    public void run() {
        byte[] response = responseForPath(this.request);
        try {
            SocketOperator.socketSendAll(this.connection, response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Server {
    static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        // 监听请求
        // 获取请求数据
        // 发送响应数据
        // 我们的服务器使用 9000 端口
        // 不使用 80 的原因是 1024 以下的端口都要管理员权限才能使用

        /*
        * 1. 接受请求
        * 2. 处理请求
        * 3. 返回响应
        * */
        int port = 9000;
        Utility.log("服务器启动, 访问 http://localhost:%s", port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                // accept 方法会一直停留在这里等待连接
                try {
                    Socket socket = serverSocket.accept();
                    // 客户端连接上来了
                    Utility.log("\nclient 连接成功");
                    // 读取客户端请求数据
                    String request = SocketOperator.socketReadAll(socket);
                    byte[] response;

                    // 处理请求
                    if (request.length() > 0) {
                        // 输出请求的数据
                        Utility.log("请求:\n%s", request);
                        // 解析 request 得到 path
                        Request r = new Request(request);
                        // 根据 path 来判断要返回什么数据
//                        response = responseForPath(r);
                        // 多线程
                        MyServlet servlet = new MyServlet(socket, r);
                        Thread t = new Thread(servlet);
                        pool.execute(t);
                    } else {
                        response = new byte[1];
                        Utility.log("接受到了一个空请求");
                        SocketOperator.socketSendAll(socket, response);
                    }

                } finally {

                }
            }
        } catch (IOException ex) {
            System.out.println("exception: " + ex.getMessage());
        }
    }



}
