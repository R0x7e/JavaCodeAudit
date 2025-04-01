package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * 定义接口类
 */
public interface Rce extends Remote {
    public String RunRce(String cmd) throws RemoteException;
}
