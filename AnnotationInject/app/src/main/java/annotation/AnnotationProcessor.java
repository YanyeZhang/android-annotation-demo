package annotation;

import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zhangyanye on 2016/8/14.
 * Description:
 */
public class AnnotationProcessor {

    public static void register(Context context) {
        Class<? extends Context> clazz = context.getClass();
        setConventView(context, clazz);
        findViewById(context,clazz);

    }


    private static void setConventView(Context context, Class clazz) {
        ContentView conventView = (ContentView) clazz.getAnnotation(ContentView.class);
        if (conventView == null) {
            return;
        }
        //方式一
        try {
            Method method = clazz.getMethod("setContentView", int.class);
            method.setAccessible(true);
            method.invoke(context, conventView.id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*方式二
        if (context instanceof Activity) {
            ((Activity) context).setContentView(conventView.id());
        }*/
    }

    private static void findViewById( Context context,Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            InjectVIew injectView = field.getAnnotation(InjectVIew.class);
            if (injectView == null) {
                return;
            }
            int viewId = injectView.id();
            if (viewId != -1) {
                try {
                    //方式一
                    Method method = clazz.getMethod("findViewById",
                            int.class);
                    Object view = method.invoke(context, viewId);
                /*方式二
                if (context instanceof Activity) {
                        Object view = ((Activity) context).findViewById(viewId);
                }*/
                    field.setAccessible(true);
                    field.set(context, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
