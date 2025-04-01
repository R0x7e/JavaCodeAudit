package org.example;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiService {
    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
        RceImpl rce = new RceImpl();
        // 创建本地RMI注册表，指定端口号为1099
        Registry registry = LocateRegistry.createRegistry(1099);
        // 将RceImpl对象绑定到RMI注册表中，命名为"RceImpl"
        registry.bind("RceImpl",rce);
    }
}
