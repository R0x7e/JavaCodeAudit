package org.example;

public class Main {
    public static void main(String[] args) {
        ClassLoader classLoader = String.class.getClassLoader();
        System.out.println(classLoader);
        User user1 = new User();
        System.out.println(user1.getClass().getClassLoader());
        User user2 = new User();

        System.out.println(user1.getClass().equals(user2.getClass()));


    }

}