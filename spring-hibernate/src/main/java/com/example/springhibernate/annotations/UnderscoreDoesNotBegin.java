package com.example.springhibernate.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UnderscoreDoesNotBeginValidator.class})
public @interface UnderscoreDoesNotBegin {
    // 简易码
    String easyCode() default "ERROR";

    // 默认错误信息
    String message() default "不能以下划线开头";

    // 分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};

    //指定多个时使用
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        UnderscoreDoesNotBegin[] value();
    }


}
