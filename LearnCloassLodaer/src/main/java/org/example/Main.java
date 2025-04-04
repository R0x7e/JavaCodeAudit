package org.example;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        TestClassLoader r0x7e = new TestClassLoader("R0x7e", "HelloWorld.enc");
        Class<?> aClass = r0x7e.loadClass("org.example.HelloWorld");
        HelloWorld helloWorld = (HelloWorld) aClass.newInstance();
        helloWorld.sayHello();
//        System.out.println(aClass.getClassLoader());

    }

}