package org.Rce;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;

public class TestRce {
    @Test
    public void Test1() throws IOException {
        String cmd ="netstat -ano";
        Process exec = Runtime.getRuntime().exec(cmd);
        // 读取命令执行的结果并打印输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream(), Charset.forName("GBK")));
        String line;
        System.out.println("命令执行结果：");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        // 关闭输入流
        reader.close();
    }

    @Test
    public void Test2() throws IOException {
        String [] cmds={"ipconfig","/all"};
        Process exec = Runtime.getRuntime().exec(cmds);
        // 读取命令执行的结果并打印输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(exec.getInputStream(), Charset.forName("GBK")));
        String line;
        System.out.println("命令执行结果：");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        // 关闭输入流
        reader.close();
    }
    @Test
    public void Test3() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("ipconfig","/all");
        Process start = processBuilder.start();
        InputStream inputStream = start.getInputStream();
        BufferedReader gbk = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("GBK")));
        String res;
        while ((res = gbk.readLine())!=null){
            System.out.println(res);
        }
    }
    @Test
    public void Test4() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Class<?> aClass = Class.forName("java.lang.ProcessImpl");
        Method start = aClass.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        start.setAccessible(true);
        InputStream invoke = (InputStream) start.invoke(null, new String[]{"calc"}, null, null, null, false);

    }
    @Test
    public void Test5() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Class<?> aClass = Class.forName("java.lang.ProcessImpl");
        Method start = aClass.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        start.setAccessible(true);
        Process process = (Process) start.invoke(null, new String[]{"ipconfig", "/all"}, null, null, null, false);

        // 获取进程的输入流
        InputStream inputStream = process.getInputStream();

        // 读取输入流中的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,Charset.forName("GBK")));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // 关闭输入流
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 等待进程执行完毕
        try {
            int exitCode = process.waitFor();
            System.out.println("Command executed with exit code: " + exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //反射调用Runtime
    @Test
    public void Test6() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> aClass = Class.forName("java.lang.Runtime");
        Method exec = aClass.getDeclaredMethod("exec", String.class);
        Object getRuntime = aClass.getDeclaredMethod("getRuntime").invoke(aClass);
        Object invoke = exec.invoke(getRuntime,"calc");

    }
    //隐藏Runtime关键字
    @Test
    public void Test7() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String str="calc";
        // 定义"java.lang.Runtime"字符串变量
        String rt = new String(new byte[]{106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 82, 117, 110, 116, 105, 109, 101});
        // 反射java.lang.Runtime类获取Class对象
        Class<?> c = Class.forName(rt);
        // 反射获取Runtime类的getRuntime方法
        Method m1 = c.getMethod(new String(new byte[]{103, 101, 116, 82, 117, 110, 116, 105, 109, 101}));
        // 反射获取Runtime类的exec方法
        Method m2 = c.getMethod(new String(new byte[]{101, 120, 101, 99}), String.class);
        // 反射调用Runtime.getRuntime().exec(xxx)方法
        Object obj2 = m2.invoke(m1.invoke(null, new Object[]{}), new Object[]{str});
    }

    //java代码审计exec命令执行的相关利用前面拼了一段然后调用 lang.runtime.exec("fuck" + a) 这里可以利用吗
    @Test
    public void Test8() throws IOException {
        String a = " & calc"; // 攻击者控制a的值
        Runtime.getRuntime().exec("fuck" + a);
    }

}
