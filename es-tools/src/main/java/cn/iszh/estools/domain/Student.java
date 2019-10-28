package cn.iszh.estools.domain;

/**
 * @version: V1.0
 * @author: Mr.Zhang
 * @className: Student
 * @packageName: cn.iszh.estools.domain
 * @description: 学生信息
 * @date: 2019-09-12 14:08
 **/
public class Student {
    private String name;
    private String sex;
    private Integer age;
    private String date;

    public Student() {
    }

    public Student(String name, String sex, Integer age, String date) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
