package cn.sunline;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: AnnotationTest
 * @packageName: PACKAGE_NAME
 * @description: 利用反射解析注解
 * @date: 2019-11-01 14:04
 **/
public class AnnotationTest {
    public static void main(String[] args) {
        Person person = new Person();
        Class<? extends Person> clazz = person.getClass();
        // 判断对象上是否存在指定注解
        if (clazz.isAnnotationPresent(CustomInterface.class)) {
            // 获取该对象上注解
            CustomInterface customInterface = clazz.getAnnotation(CustomInterface.class);
            System.out.println("person.name:" + customInterface.value() + ".person.isDelete:" + customInterface.isDelete());
        } else {
            System.out.println("Persion类上没有配置该注解");
        }
    }
}
