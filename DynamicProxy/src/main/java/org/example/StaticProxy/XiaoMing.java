package org.example.StaticProxy;

import org.example.Hello;

public class XiaoMing implements Hello {
    private Persion persion;


    public XiaoMing(Persion persion){
        this.persion=persion;

    }
    @Override
    public void Say(String name) {
        System.out.println("Say执行前的操作");
        persion.Say(name);
        System.out.println("Say执行后的操作");
    }
}
