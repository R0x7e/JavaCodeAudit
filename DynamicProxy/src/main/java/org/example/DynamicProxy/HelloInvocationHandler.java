package org.example.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloInvocationHandler implements InvocationHandler {
    private Object target;
    public HelloInvocationHandler(Object target){
        this.target=target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行前");
        Object invoke = method.invoke(target, args);
        System.out.println("执行后");
        return invoke;
    }
}
