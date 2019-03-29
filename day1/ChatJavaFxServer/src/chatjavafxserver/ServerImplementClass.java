/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatjavafxserver;

import chatjavafxcommon.Message;
import chatjavafxcommon.ServerInterface;
import chatjavafxcommon.clientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author salma
 */
public class ServerImplementClass extends UnicastRemoteObject implements ServerInterface{
    
    ArrayList<clientInterface> clientsList=new ArrayList<clientInterface>();
    
    public ServerImplementClass() throws RemoteException {
    }
    

    @Override
    public void broadcastMessage(Message message,clientInterface client) throws RemoteException {
        for(int i=0;i<clientsList.size();i++)
        {
            if(clientsList.get(i).equals(client))
                clientsList.get(i).recieve(message,true);
            else
                clientsList.get(i).recieve(message,false);
        }
    }

    @Override
    public void registerClient(clientInterface client) throws RemoteException {
        clientsList.add(client);
    }

    @Override
    public void unregisterClient(clientInterface existingClient) throws RemoteException {
        for(int i=0;i<clientsList.size();i++)
        {
           
                clientsList.remove(existingClient);
            
        }
    }
    
}