package cn.iszh;

import java.lang.reflect.Proxy;

public class Application {
    public static void main(String[] args) {
        SomeMethods someMethods = (SomeMethods) Proxy.newProxyInstance(SomeMethods.class.getClassLoader(),
                new Class[]{SomeMethods.class},
                new MethodSelector(new Implementation())
        );
        someMethods.interesting("666666");
    }
}
