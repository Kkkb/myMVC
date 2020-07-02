package myMVC;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;

public class JDBCDemo {
    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }



    public static void addBySQL(String content) {
        MysqlDataSource ds = Utility.getDataSource();
        String sql = String.format("insert into `mymvc`.`Todo` (content) values ('%s')", content);

        try {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void selectBySQL() {
        MysqlDataSource ds = Utility.getDataSource();
        String sql = String.format("select * from `mymvc`.`Todo`");

        try {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                log("id: %s", rs.getInt("id"));
                log("content: %s", rs.getString("content"));
            }

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void selectByIdSQL(String id) {
        MysqlDataSource ds = Utility.getDataSource();
        String sql = String.format("select * from `mymvc`.`Todo` where id = %s", id);

        try {
            Connection connection = ds.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                log("id: %s", rs.getInt("id"));
                log("content: %s", rs.getString("content"));
            }

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static void selectByIdSQLSafe(String id) {
        MysqlDataSource ds = Utility.getDataSource();
        String sql = "select * from `mymvc`.`Todo` where id = ?";

        try {
            Connection connection = ds.getConnection();
            // 防止 SQL 注入, 采用预编译语句, 把 SQL 解析为语法树
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                log("id: %s", rs.getInt("id"));
                log("content: %s", rs.getString("content"));
            }

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        addBySQL("todo2");

//        selectBySQL();
//        selectByIdSQL("1 or true");
//        selectByIdSQLSafe("1 or true");
    }
}
