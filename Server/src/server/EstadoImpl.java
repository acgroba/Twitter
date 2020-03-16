/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import interfaces.EstadoInterface;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

/**
 *
 * @author abraham
 */
public class EstadoImpl extends UnicastRemoteObject
                         implements EstadoInterface, Serializable{
   public String user;
   public  String estado;
   public  Date fecha;
   
   protected EstadoImpl(String user, String estado, Date fecha) throws RemoteException {
        super();
        this.user=user;
        this.estado=estado;
        this.fecha=fecha;
    }

    public String getEstado() throws RemoteException {
        return estado;
    }

    public void setEstado(String estado) throws RemoteException {
        this.estado = estado;
    }

    public String getUser() throws RemoteException {
        return user;
    }

    public Date getFecha() throws RemoteException {
        return fecha;
    }
 
   
    
}
