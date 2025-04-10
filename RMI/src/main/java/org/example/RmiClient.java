package org.example;

import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient {
    @Test
    public void TestCliente() throws MalformedURLException, NotBoundException, RemoteException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        Rce rce = (Rce) registry.lookup("RceImpl");
        String whoami = rce.RunRce("whoami");
        System.out.println(whoami);

    }
}
