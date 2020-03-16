/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author abraham
 */



import interfaces.EstadoInterface;
import interfaces.MensajeInterface;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

/**
 *
 * @author abraham
 */
public class MensajeImpl extends UnicastRemoteObject
                         implements MensajeInterface, Serializable{
   public String remitente;
   public String destinatario;
   public  String mensaje;
   public  Date fecha;
   
   public MensajeImpl(String remitente, String destinatario, String mensaje, Date fecha) throws RemoteException {
       super();
       this.remitente=remitente;
       this.destinatario=destinatario;
       this.mensaje=mensaje;
       this.fecha=fecha;
   }

    public String getRemitente() throws RemoteException {
        return remitente;
    }

   

    public String getDestinatario() throws RemoteException {
        return destinatario;
    }
     public String getMensaje() throws RemoteException {
        return mensaje;
    }

    public Date getFecha() throws RemoteException {
        return fecha;
    }
 
   
    
}
