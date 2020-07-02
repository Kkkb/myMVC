package myMVC.service;

import myMVC.Digest;
import myMVC.Request;
import myMVC.models.ModelFactory;
import myMVC.models.User;
import myMVC.models.UserRole;

import java.util.ArrayList;
import java.util.HashMap;

public class UserService {
    public static void add(HashMap<String, String> data) {

        String username = data.get("username");

        String password = saltedPassword(data.get("password"));

        User m = new User();
        m.username = username;
        m.password = password;
        m.note = "note";

        ArrayList<User> all = load();

        if (all.isEmpty()) {
            m.id = 1;
        } else {
            m.id = all.get(all.size() - 1).id + 1;
        }
        m.role = UserRole.normal;
        all.add(m);
        save(all);
    }

    public static void save(ArrayList<User> list) {
        String className = User.class.getSimpleName();

        ModelFactory.save(className, list, (model) -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.id.toString());
            lines.add(model.username);
            lines.add(model.password);
            lines.add(model.note);
            lines.add(model.role.toString());
            return lines;
        });
    }

    public static ArrayList<User> load() {
        String className = User.class.getSimpleName();

        ArrayList<User> rs = ModelFactory.load(className, 5, (modelData) -> {
            Integer id = Integer.valueOf(modelData.get(0));
            String username = modelData.get(1);
            String password = modelData.get(2);
            String note = modelData.get(3);
            String role = modelData.get(4);
            User m = new User();
            m.id = id;
            m.username = username;
            m.password = password;
            m.note = note;
            m.role = UserRole.valueOf(role);
            return m;
        });

        return rs;
    }

    public static User guest() {
        User g = new User();
        g.id = -1;
        g.username = "游客";
        g.note = "这家伙很懒, 什么都没有留下";
        g.password = "";
        g.role = UserRole.guest;
        return g;
    }

    public static boolean validLogin(String username, String password) {
        ArrayList<User> all = load();

        String saltedPassword = saltedPassword(password);
        for (User u:all) {
            if (u.username.equals(username) && u.password.equals(saltedPassword)) {
                return true;
            }
        }
        return false;
    }

    public static User findByUsername(String username) {
        ArrayList<User> all = load();

        User u = ModelFactory.findBy(all, (m) -> {
            return m.username.equals(username);
        });

        if (u == null) {
            return guest();
        } else {
            return u;
        }
    }

    public static User findByUsernamePassword(String username, String password) {
        ArrayList<User> all = load();

        for (User u: all) {
            if (u.username.equals(username) && u.password.equals(password)) {
                return u;
            }
        }

        return null;
    }

    public static User findById(Integer userId) {
        ArrayList<User> all = load();

        User u = ModelFactory.findBy(all, (m) -> {
            return m.id.equals(userId);
        });

        if (u == null) {
            return guest();
        } else {
            return u;
        }
    }

    public static User currentUser(Request request) {
        if (request.cookies.containsKey("session")) {
            String sessionId = request.cookies.get("session");
            Integer userId = SessionService.findBySessionId(sessionId);
            User u = findById(userId);
            return u;
        } else {
            return UserService.guest();
        }
    }

    public static String saltedPassword(String password) {
        String salt = "g$a#k#k@i";
        String pd = password + salt;
        String hex = Digest.md5(pd);
        return hex;
    }

    public static String usersListShowString() {
        ArrayList<User> all = load();

        StringBuilder sb = new StringBuilder();

        for (User m:all) {
            Integer id = m.id;
            String username = m.username;
            String note = m.note;
            String role = m.role.toString();
            String s = String.format("%s: %s, (%s), %s", id, username, note, role);
            sb.append(s);
            sb.append("<br>");
        }
        return sb.toString();
    }

    public static void updatePassword(Integer id, String password) {
        ArrayList<User> userList = load();
        for (User u: userList) {
            if (u.id.equals(id)) {
                String saltedPassword = saltedPassword(password);
                u.password = saltedPassword;
            }
        }

        save(userList);
    }
}

