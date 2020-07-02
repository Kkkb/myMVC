package myMVC.service;

import myMVC.models.Message;
import myMVC.models.ModelFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageService {
    public static void add(HashMap<String, String> data) {

        Message m = new Message();
        m.author = data.get("author");
        m.message = data.get("message").replace("\n", " ");
        m = checkWord(m);

        ArrayList<Message> all = load();
        all.add(m);
        save(all);
    }

    public static void save(ArrayList<Message> messageList) {
        String className = Message.class.getSimpleName();

        ModelFactory.save(className, messageList, (model) -> {
            String author = model.author;
            String message = model.message;
            ArrayList<String> line = new ArrayList<>();
            line.add(author);
            line.add(message);
            return line;
        });
    }

    public static ArrayList<Message> load() {
        String className = Message.class.getSimpleName();

        ArrayList<Message> rs = ModelFactory.load(className, 2, (modelData) -> {
            Message m = new Message();
            m.author = modelData.get(0);
            m.message = modelData.get(1);
            return m;
        });

        return rs;
    }

    public static String messageListShowString() {
        ArrayList<Message> all = load();

        StringBuilder sb = new StringBuilder();

        for (Message m:all) {
            String author = m.author;
            String message = m.message;
            String s = String.format("%s: %s", author, message);
            sb.append(s);
            sb.append("<br>");
        }
        return sb.toString();
    }

    public static Message checkWord(Message m) {
        ArrayList<String> hexiWord = new ArrayList<>();
        hexiWord.add("tmd");
        hexiWord.add("fuck");

        for (String w:hexiWord) {
            String message = m.message;
            if (message.equals(w)) {
                m.message = "*";
            }
        }

        return m;
    }

}
