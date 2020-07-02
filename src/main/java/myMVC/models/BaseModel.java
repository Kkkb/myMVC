package myMVC.models;

import java.lang.reflect.Field;

public class BaseModel {
    @Override
    public String toString() {
        /* (User:
        id: %s
        username: %s
        )
        */
        Field[] fields = this.getClass().getFields();
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(this.getClass().getSimpleName() + ":\n");
        for (Field f:fields) {
            try {
                Object v = f.get(this);
                String s = String.format("%s: %s\n", f.getName(), v);
                sb.append(s);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
