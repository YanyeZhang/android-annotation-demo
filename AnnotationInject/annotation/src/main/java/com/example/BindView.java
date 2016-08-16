package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangyanye on 2016/8/16.
 * Description:
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface BindView {
    int id() default -1;
}
