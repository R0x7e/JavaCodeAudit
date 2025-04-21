package org.example;

import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

public class TestJNDI {
    public static void main(String[] args) throws NamingException {
        InitialContext initialContext = new InitialContext();
        NamingEnumeration<NameClassPair> list = initialContext.list("rmi://166.108.225.228:1099/yeryms");
        System.out.println(list);
    }
}
