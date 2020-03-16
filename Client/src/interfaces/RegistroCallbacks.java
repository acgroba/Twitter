/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author abraham
 */
public interface RegistroCallbacks extends Remote{
    public void registroCallback(ClienteCallback callback) throws RemoteException;
    public void desregistroCallback(ClienteCallback callback) throws RemoteException;
    public List<ClienteCallback> getCallbacks() throws RemoteException;
}
