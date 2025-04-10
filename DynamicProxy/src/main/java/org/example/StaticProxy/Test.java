package org.example.StaticProxy;
public class Test {

    public static void main(String[] args) {
        Persion persion = new Persion();
        XiaoMing xiaoMing = new XiaoMing(persion);
        xiaoMing.Say("xiaoming");
    }
}
