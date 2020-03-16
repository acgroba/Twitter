/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author abraham
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 *
 * @author abraham
 */
public interface MensajeInterface extends Remote {
    public String getRemitente() throws RemoteException;
     public String getDestinatario() throws RemoteException;
     public String getMensaje() throws RemoteException ;
     public Date getFecha() throws RemoteException;
    
    
}
