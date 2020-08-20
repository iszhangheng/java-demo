package com.example.springhibernate.util;

import com.example.springhibernate.annotations.UnderscoreDoesNotBegin;
import com.example.springhibernate.entity.Foo;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.TooManyListenersException;

public class ValidationTools {
    private static final Logger log = LoggerFactory.getLogger(ValidationTools.class);
    private static Validator VALIDATOR;

    static {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        VALIDATOR = vf.getValidator();
    }


    public static <T> void validate(T t) {
        Set<ConstraintViolation<T>> set = VALIDATOR.validate(t);
        if (Objects.nonNull(set)) {
            for (ConstraintViolation<T> violation : set) {
                System.out.println("EasyCode" + parseViolation(violation));
            }
        }
    }

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.setName("_HelloWorld");
        ValidationTools.validate(foo);
    }

    private static <T> String parseViolation(ConstraintViolation<T> violation) {
        Field field;// 检测不通过字段
        Method messageMethod;// 异常信息方法
        Method easyCodeMethod;// 异常简易Code方法
        // 获取验证对象的实体类
        Class<T> rootBeanClass = violation.getRootBeanClass();
        try {
            // 获取验证不通过的属性字段
            field = rootBeanClass.getDeclaredField(String.valueOf(violation.getPropertyPath()));
            // 获取字段上的注解列表
            Annotation[] annotations = field.getAnnotations();
            for (Annotation item : annotations) {
                try {
                    messageMethod = item.getClass().getMethod("message");
                    easyCodeMethod = item.getClass().getMethod("easyCode");
                } catch (NoSuchMethodException e) {
                    log.info("参数校验获取注解函数message()与easyCode()失败，判定非自定义注解，跳过。。");
                    continue;
                }
                if (violation.getMessage().equals(messageMethod.invoke(item))) {
                    return String.valueOf(easyCodeMethod.invoke(item));
                }
            }
        } catch (NoSuchFieldException e) {
            log.error("参数验证获取不通过字段失败", e);
        } catch (InvocationTargetException e) {
            log.error("获取标签内容方法执行失败", e);
        } catch (IllegalAccessException e) {
            log.error("标签执行方法权限不足", e);
        }
        return null;
    }
}
