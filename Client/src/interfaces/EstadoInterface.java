/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 *
 * @author abraham
 */
public interface EstadoInterface extends Remote{
     public String getEstado() throws RemoteException;
     public void setEstado(String estado) throws RemoteException;
     public String getUser() throws RemoteException ;
     public Date getFecha() throws RemoteException ;
  
     
    
}
