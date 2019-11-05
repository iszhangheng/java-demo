package cn.iszh;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: MethodSelector
 * @packageName: cn.iszh
 * @description: 方法选择
 * @date: 2019-11-04 21:43
 **/
public class MethodSelector implements InvocationHandler {
    private Object proxied;

    public MethodSelector(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("interesting")) {
            System.out.println("执行方法Interesting（）");
        }
        return method.invoke(proxied, args);
    }
}
