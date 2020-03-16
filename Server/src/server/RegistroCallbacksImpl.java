/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.ClienteCallback;
import interfaces.RegistroCallbacks;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abraham
 */
public class RegistroCallbacksImpl  extends UnicastRemoteObject implements RegistroCallbacks{
     List <ClienteCallback> callbacks; 
	public RegistroCallbacksImpl() throws RemoteException {
		super();
                this.callbacks=new ArrayList <ClienteCallback>();
	}
        public void registroCallback(ClienteCallback callback) throws RemoteException {
		this.callbacks.add(callback);
	}
         public void desregistroCallback(ClienteCallback callback) throws RemoteException {
		this.callbacks.remove(callback);
	}
        public List<ClienteCallback> getCallbacks() throws RemoteException{
            return callbacks;
        }
}
