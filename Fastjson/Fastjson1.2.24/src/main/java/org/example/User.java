package org.example;

import java.io.IOException;

public class User {
    private  String name;
    private   int age;
    

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        System.out.println("调用了getName方法");
        return name;
    }

    public void setName(String name) {
        System.out.println("调用了setName方法");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
