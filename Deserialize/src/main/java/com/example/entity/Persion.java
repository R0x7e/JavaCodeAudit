package com.example.entity;

import java.io.IOException;
import java.io.Serializable;

public class Persion implements Serializable {
    private String name;
    private int age;

    public Persion() {
    }

    @Override
    public String toString() {
        return "Persion{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Persion(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //重定义了readObject方
    private void readObject(java.io.ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Runtime.getRuntime().exec("calc.exe");
    }
}
