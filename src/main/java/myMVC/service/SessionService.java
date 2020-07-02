package myMVC.service;

import myMVC.models.Session;
import myMVC.models.ModelFactory;
import java.util.ArrayList;

public class SessionService {
    public static void add(Integer userId, String sessionId) {

        Session m = new Session();
        m.userId = userId;
        m.sessionId = sessionId;

        ArrayList<Session> all = load();
        all.add(m);
        save(all);
    }

    public static void save(ArrayList<Session> SessionList) {
        String className = Session.class.getSimpleName();

        ModelFactory.save(className, SessionList, (model) -> {
            Integer userId = model.userId;
            String sessionId = model.sessionId;
            ArrayList<String> line = new ArrayList<>();
            line.add(userId.toString());
            line.add(sessionId);
            return line;
        });
    }

    public static ArrayList<Session> load() {
        String className = Session.class.getSimpleName();

        ArrayList<Session> rs = ModelFactory.load(className, 2, (modelData) -> {
            Session m = new Session();
            m.userId = Integer.valueOf(modelData.get(0));
            m.sessionId = modelData.get(1);
            return m;
        });

        return rs;
    }

    public static Integer findBySessionId(String sessionId) {
        ArrayList<Session> all = load();

        for (Session s:all) {
            if (s.sessionId.equals(sessionId)) {
                return s.userId;
            }
        }
        return null;
    }

    public static Session findById(Integer userId) {
        ArrayList<Session> all = load();

        Session s = ModelFactory.findBy(all, (m) -> {
            return m.userId.equals(userId);
        });
        return s;
    }

    public static void delete(Integer userId) {
        ArrayList<Session> all = load();

        for (int i = 0; i < all.size(); i++) {
            Session e = all.get(i);
            if (e.userId.equals(userId)) {
                all.remove(e);
            }
        }
        save(all);
    }

}
