package org.Reflex.entity;

public class User {
    class Userinternal{
        public void PrintC(){
            System.out.println("执行了内部类的PrintC方法");
        }
    }
    static {
//        System.out.println("aaa");
    }

    public User() {
        System.out.println("调用了无参构造方法");
    }

    public User(String user_name, String user_phone) {
        this.user_name = user_name;
        this.user_phone = user_phone;
        System.out.println(user_name+" "+user_phone);
    }

    public String user_name;
    private String user_phone;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    private void PrintAll(){
        System.out.println("调用了私有化方法");
    }
    public String Print(String connect){
        return connect;
    }
    public void PrintA(){
        System.out.println("调用了PrintA方法");
    }

    public static void PringB(){
        System.out.println("调用了static PrintB方法");
    }
}