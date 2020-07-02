package myMVC.service;

import myMVC.models.Comment;
import myMVC.models.ModelFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CommentService {
        public static Comment add(HashMap<String, String> data, Integer userId) {
        Comment m = new Comment();
        m.content = data.get("content").replace("\n", " ");
        m.userId = userId;
        m.weiboId = Integer.valueOf(data.get("weiboId"));
//        m.completed = false;

        // 本函数被调用, 意味着有数据添加, 这时可以拿到 unix 时间 给 createdTime 和 updatedTime 赋值
//        Long unixTime = System.currentTimeMillis() / 1000L;
//        m.createdTime = unixTime;
//        m.updatedTime = unixTime;

        ArrayList<Comment> all = load();

        if (all.isEmpty()) {
            m.id = 1;
        } else {
            m.id = all.get(all.size() - 1).id + 1;
        }

        all.add(m);
        save(all);

        return m;
    }


    public static void save(ArrayList<Comment> commentList) {
        String className = Comment.class.getSimpleName();

        ModelFactory.save(className, commentList, (model) -> {
            String id = model.id.toString();
            String content = model.content;
            String userId = model.userId.toString();
            String weiboId = model.weiboId.toString();
//            String completed = model.completed.toString();
            ArrayList<String> line = new ArrayList<>();
            line.add(id);
            line.add(content);
            line.add(userId);
            line.add(weiboId);
//            line.add(completed);

//            // 将 createdTime 和 updatedTime 保存到文件
//            line.add(model.createdTime.toString());
//            line.add(model.updatedTime.toString());

            return line;
        });
    }

    public static ArrayList<Comment> load() {
        String className = Comment.class.getSimpleName();

        ArrayList<Comment> rs = ModelFactory.load(className, 4, (modelData) -> {
            Comment m = new Comment();
            m.id = Integer.valueOf(modelData.get(0));
            m.content = modelData.get(1);
            m.userId = Integer.valueOf(modelData.get(2));
            m.weiboId = Integer.valueOf(modelData.get(3));
            m.user = UserService.findById(m.userId);
//            m.completed = Boolean.valueOf(modelData.get(2));

//            // 拿到文件中 createdTime 和 updatedTime 的值
//            String createdTime = modelData.get(3);
//            String updatedTime = modelData.get(4);

//            // 将读到的数据赋到 m 对象上
//            m.createdTime = Long.valueOf(createdTime);
//            m.updatedTime = Long.valueOf(updatedTime);

            return m;
        });

        return rs;
    }

    public static Comment delete(Integer id) {
        ArrayList<Comment> all = CommentService.load();

        Comment t = new Comment();
        for (int i = 0; i < all.size(); i++) {
            t = all.get(i);
            if (t.id.equals(id)) {
                all.remove(t);
                break;
            }
        }

        CommentService.save(all);
        return t;
    }

    public static Comment findById(Integer id) {
        ArrayList<Comment> all = load();

        for (Comment t: all) {
            if (t.id.equals(id)) {
                return t;
            }
        }
        return null;
    }

    public static ArrayList<Comment> findByWeiboId(Integer id) {
        ArrayList<Comment> all = load();
        ArrayList<Comment> r = new ArrayList<>();

        for (Comment t: all) {
            if (t.weiboId.equals(id)) {
                r.add(t);
            }
        }

        return r;
    }

//    public static void completed(Integer id) {
//        ArrayList<comment> all = load();
//
//        for (comment t: all) {
//            if (t.id.equals(id)) {
//                t.completed = true;
//            }
//        }
//
//        save(all);
//    }


    public static Comment updateContent(Integer commentId, String content) {
        ArrayList<Comment> all = load();

        Comment e = new Comment();
        for (int i = 0; i < all.size(); i++) {
            e = all.get(i);
            if (e.id.equals(commentId)) {
                e.content = content;

//                // 本函数被调用, 意味着数据有更新, updatedTime 也相应更新
//                Long unixTime = System.currentTimeMillis() / 1000L;
//                e.updatedTime = unixTime;
//                break;
            }
        }

        save(all);
        return e;
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
