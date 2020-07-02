package myMVC.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import myMVC.Utility;
import myMVC.models.ModelFactory;
import myMVC.models.Weibo;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class WeiboService {
        public static Weibo add(HashMap<String, String> data, Integer userId) {
        Weibo m = new Weibo();
        m.content = data.get("content").replace("\n", " ");
        m.userId = userId;
//        m.completed = false;

        // 本函数被调用, 意味着有数据添加, 这时可以拿到 unix 时间 给 createdTime 和 updatedTime 赋值
//        Long unixTime = System.currentTimeMillis() / 1000L;
//        m.createdTime = unixTime;
//        m.updatedTime = unixTime;

        ArrayList<Weibo> all = load();

        if (all.isEmpty()) {
            m.id = 1;
        } else {
            m.id = all.get(all.size() - 1).id + 1;
        }

        all.add(m);
        save(all);

        return m;
    }


    public static void save(ArrayList<Weibo> WeiboList) {
        String className = Weibo.class.getSimpleName();

        ModelFactory.save(className, WeiboList, (model) -> {
            String id = model.id.toString();
            String content = model.content;
            String userId = model.userId.toString();
//            String completed = model.completed.toString();
            ArrayList<String> line = new ArrayList<>();
            line.add(id);
            line.add(content);
            line.add(userId);
//            line.add(completed);

//            // 将 createdTime 和 updatedTime 保存到文件
//            line.add(model.createdTime.toString());
//            line.add(model.updatedTime.toString());

            return line;
        });
    }

    public static ArrayList<Weibo> load() {
        String className = Weibo.class.getSimpleName();

        ArrayList<Weibo> rs = ModelFactory.load(className, 3, (modelData) -> {
            Weibo m = new Weibo();
            m.id = Integer.valueOf(modelData.get(0));
            m.content = modelData.get(1);
            m.userId = Integer.valueOf(modelData.get(2));
            m.user = UserService.findById(m.userId);
            m.comments = CommentService.findByWeiboId(m.id);
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

    public static Weibo delete(Integer id) {
        ArrayList<Weibo> all = WeiboService.load();

        Weibo t = new Weibo();
        for (int i = 0; i < all.size(); i++) {
            t = all.get(i);
            if (t.id.equals(id)) {
                all.remove(t);
                break;
            }
        }

        WeiboService.save(all);
        return t;
    }

    public static Weibo findById(Integer id) {
        ArrayList<Weibo> all = load();

        for (Weibo t: all) {
            if (t.id.equals(id)) {
                return t;
            }
        }
        return null;
    }

//    public static void completed(Integer id) {
//        ArrayList<Weibo> all = load();
//
//        for (Weibo t: all) {
//            if (t.id.equals(id)) {
//                t.completed = true;
//            }
//        }
//
//        save(all);
//    }


    public static Weibo updateContent(Integer WeiboId, String content) {
        ArrayList<Weibo> all = load();

        Weibo e = new Weibo();
        for (int i = 0; i < all.size(); i++) {
            e = all.get(i);
            if (e.id.equals(WeiboId)) {
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
