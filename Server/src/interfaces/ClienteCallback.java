/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author abraham
 */
public interface ClienteCallback extends Remote{
      public void actualizarEstados() throws RemoteException;
       public String getTarget() throws RemoteException;
       
        public Usuario getUser() throws RemoteException;
}
