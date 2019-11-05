package cn.sunline;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: cn.sunline.Person
 * @packageName: PACKAGE_NAME
 * @description: 测试
 * @date: 2019-11-01 14:02
 **/
@CustomInterface(isDelete = true)
public class Person {
    private String name;
    private Integer age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
