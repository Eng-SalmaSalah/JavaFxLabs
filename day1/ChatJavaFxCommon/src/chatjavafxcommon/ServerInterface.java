/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatjavafxcommon;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author salma
 */
public interface ServerInterface extends Remote {
    public void broadcastMessage (Message message,clientInterface client) throws RemoteException;
    public void registerClient (clientInterface newClient) throws RemoteException;
    public void unregisterClient (clientInterface existingClient) throws RemoteException;
}
