package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RceImpl extends UnicastRemoteObject implements Rce{
    protected RceImpl() throws RemoteException {
    }

    @Override
    public String RunRce(String cmd) throws RemoteException {
        try {
            Process cmd1 = Runtime.getRuntime().exec(cmd);
            // 读取命令执行的结果并打印输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(cmd1.getInputStream(), Charset.forName("GBK")));
            StringBuffer output=new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            // 关闭输入流
            reader.close();
            return output.toString();
        } catch (IOException e) {
            return e.getMessage();
        }

    }
}
