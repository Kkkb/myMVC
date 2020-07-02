package myMVC.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import myMVC.Utility;
import myMVC.models.Todo;
import myMVC.models.ModelFactory;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TooManyListenersException;

public class TodoService {

//    public static Todo add(HashMap<String, String> data) {
//        Todo m = new Todo();
//        m.content = data.get("content").replace("\n", " ");
//        m.completed = false;
//
//        // 本函数被调用, 意味着有数据添加, 这时可以拿到 unix 时间 给 createdTime 和 updatedTime 赋值
//        Long unixTime = System.currentTimeMillis() / 1000L;
//        m.createdTime = unixTime;
//        m.updatedTime = unixTime;
//
//        ArrayList<Todo> all = load();
//
//        if (all.isEmpty()) {
//            m.id = 1;
//        } else {
//            m.id = all.get(all.size() - 1).id + 1;
//        }
//
//        all.add(m);
//        save(all);
//
//        return m;
//    }

    public static String insertSQL(String dataBase, String tableName, String column, String value) {
//        MysqlDataSource ds = Utility.getDataSource();
        String sql = String.format("insert into %s.%s (%s) values (%s)", dataBase, tableName, column, value);
        return sql;
    }

    public static Todo addBySQL(String content) {
        Todo m = new Todo();
        m.setContent(content);

        MysqlDataSource ds = Utility.getDataSource();
//        String sql = "insert into `mymvc`.`todo` (content) values (?)";
        String dataBase = "mymvc";
        String tableName = "todo";
        String column = "content";
        String value = "?";
        String sql = insertSQL(dataBase, tableName, column, value);

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, content);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            rs.first();
            Integer id = rs.getInt("GENERATED_KEY");
            m.setId(id);

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return m;
    }

//    public static void save(ArrayList<Todo> TodoList) {
//        String className = Todo.class.getSimpleName();
//
//        ModelFactory.save(className, TodoList, (model) -> {
//            String id = model.id.toString();
//            String content = model.content;
//            String completed = model.completed.toString();
//            ArrayList<String> line = new ArrayList<>();
//            line.add(id);
//            line.add(content);
//            line.add(completed);
//
//            // 将 createdTime 和 updatedTime 保存到文件
//            line.add(model.createdTime.toString());
//            line.add(model.updatedTime.toString());
//
//            return line;
//        });
//    }

//    public static ArrayList<Todo> load() {
//        String className = Todo.class.getSimpleName();
//
//        ArrayList<Todo> rs = ModelFactory.load(className, 5, (modelData) -> {
//            Todo m = new Todo();
//            m.id = Integer.valueOf(modelData.get(0));
//            m.content = modelData.get(1);
//            m.completed = Boolean.valueOf(modelData.get(2));
//
//            // 拿到文件中 createdTime 和 updatedTime 的值
//            String createdTime = modelData.get(3);
//            String updatedTime = modelData.get(4);
//
//            // 将读到的数据赋到 m 对象上
//            m.createdTime = Long.valueOf(createdTime);
//            m.updatedTime = Long.valueOf(updatedTime);
//
//            return m;
//        });
//
//        return rs;
//    }

//    public static Todo delete(Integer id) {
//        ArrayList<Todo> all = TodoService.load();
//
//        Todo t = new Todo();
//        for (int i = 0; i < all.size(); i++) {
//            t = all.get(i);
//            if (t.id.equals(id)) {
//                all.remove(t);
//                break;
//            }
//        }
//
//        TodoService.save(all);
//        return t;
//    }

//    public static Todo findById(Integer id) {
//        ArrayList<Todo> all = load();
//
//        for (Todo t: all) {
//            if (t.id.equals(id)) {
//                return t;
//            }
//        }
//        return null;
//    }

//    public static void completed(Integer id) {
//        ArrayList<Todo> all = load();
//
//        for (Todo t: all) {
//            if (t.id.equals(id)) {
//                t.completed = true;
//            }
//        }
//
//        save(all);
//    }

    public static Todo findByIdSQL(Integer id) {
        Todo m = new Todo();
        m.setId(id);

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "select * from `mymvc`.`todo` where id = (?)";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            rs.first();
            m.setContent(rs.getString("content"));

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return m;
    }

//    public static Todo updateContent(Integer todoId, String content) {
//        ArrayList<Todo> all = load();
//
//        Todo e = new Todo();
//        for (int i = 0; i < all.size(); i++) {
//            e = all.get(i);
//            if (e.id.equals(todoId)) {
//                e.content = content;
//
//                // 本函数被调用, 意味着数据有更新, updatedTime 也相应更新
//                Long unixTime = System.currentTimeMillis() / 1000L;
//                e.updatedTime = unixTime;
//                break;
//            }
//        }
//
//        save(all);
//        return e;
//    }

    public static Todo updateBySQL(Integer todoId, String content) {
        Todo m = new Todo();
        m.setId(todoId);
        m.setContent(content);

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "update `mymvc`.`todo` set content = (?) where id = (?)";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, content);
            statement.setInt(2, todoId);
            statement.executeUpdate();

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return m;
    }

    public static void deleteBySQL(Integer id) {
        MysqlDataSource ds = Utility.getDataSource();
        String sql = "delete from `mymvc`.`todo` where id = (?)";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeUpdate();

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Todo> allBySQL() {
        ArrayList<Todo> ms = new ArrayList<>();

        MysqlDataSource ds = Utility.getDataSource();
        String sql = "select * from `mymvc`.`todo`";

        try {
            Connection connection = ds.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Todo m = new Todo();
                m.setId(rs.getInt("id"));
                m.setContent(rs.getString("content"));
                ms.add(m);
            }

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return ms;
    }

    public static String formattedTime(Long unixTime) {
        // 先创建 unix 时间
        Date date = new Date(unixTime * 1000);

        // 将 unix 时间 转成相应格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = dateFormat.format(date);

        return dateString;
    }
}
