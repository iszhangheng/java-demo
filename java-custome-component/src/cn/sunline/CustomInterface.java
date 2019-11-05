package cn.sunline;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 应用于类，接口，枚举
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomInterface {
    String value() default "test";

    boolean isDelete();
}
