package org.example.DynamicProxy;

import org.example.Hello;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        Hello hello=new Hello() {
            @Override
            public void Say(String name) {
                System.out.println("Say Hello");
            }
        };
        HelloInvocationHandler helloInvocationHandler =new HelloInvocationHandler(hello);
        //创建代理对象
        Hello HelloProxy = (Hello) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{Hello.class}, helloInvocationHandler);
        HelloProxy.Say("whoami");
    }
}
