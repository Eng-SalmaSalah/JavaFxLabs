/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatjavafxserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author salma
 */
public class ChatJavaFxServer {

    public ChatJavaFxServer() {
        try {
            ServerImplementClass chatService = new ServerImplementClass();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("chattingService", chatService);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatJavaFxServer();
    }

}
