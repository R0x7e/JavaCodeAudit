package org.example;

import java.io.IOException;

public class HelloWorld {
    static{
        try {
            Runtime.getRuntime().exec("calc");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sayHello(){
        System.out.println("Hello World");
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
