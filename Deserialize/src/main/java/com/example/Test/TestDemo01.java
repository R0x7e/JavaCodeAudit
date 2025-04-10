package com.example.Test;

import com.example.entity.Persion;
import org.junit.Test;

import java.io.*;

public class TestDemo01 {
    @Test
    public void TestSerialize() throws IOException {
        Persion persion = new Persion("pwjcw", 1);
        // 创建文件输出流，用于将对象序列化后的字节流写入文件
        FileOutputStream f = new FileOutputStream("persion.ser");
        // 创建对象输出流，用于将对象序列化后的数据写入文件
        ObjectOutputStream outputStream = new ObjectOutputStream(f);
        // 将 Person 对象进行序列化并写入文件
        outputStream.writeObject(persion);
        // 关闭对象输出流
        outputStream.close();
        // 关闭文件输出流
        f.close();
    }
    @Test
    public void TestUnSerialize() throws IOException, ClassNotFoundException {
        //从文件中读取二进制字节流
        FileInputStream fileInputStream=new FileInputStream("persion.ser");
        //创建ObjectInputStream对象，并且传入FileInputStream读取到的二进制字节流
        ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
        //调用readObject方法，并且转换为Persion对象
        Persion persion= (Persion) objectInputStream.readObject();
        //打印反序列化得到的persion对象
        System.out.println(persion);
    }
}
