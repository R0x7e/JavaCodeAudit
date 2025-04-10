package org.example.StaticProxy;

import org.example.Hello;

public class Persion implements Hello {
    @Override
    public void Say(String name) {
        System.out.println(name+" Say Hello!");
    }
}
