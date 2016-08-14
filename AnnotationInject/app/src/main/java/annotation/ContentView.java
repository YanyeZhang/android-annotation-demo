package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by zhangyanye on 2016/8/15.
 * Description:
 */
@Target(ElementType.TYPE)
public @interface ContentView {
    int id() default -1;
}
