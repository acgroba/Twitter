/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author abraham
 */
public interface Usuario extends Remote {

    public String getUser() throws RemoteException;

    public String getPassword() throws RemoteException;

    public void setPassword(String password) throws RemoteException;

    public void setBiografia(String biografia) throws RemoteException;

    public String getBiografia() throws RemoteException;

    public List<Usuario> getSeguidos() throws RemoteException;

    public List<String> getSeguidores() throws RemoteException;

    public void seguir(String user) throws RemoteException;

    public void dejarSeguir(String user) throws RemoteException;

    public Usuario buscarUsuario(String user) throws RemoteException;

   public void addEstado(String usuario, String est, Date fecha)throws RemoteException;
public void deleteEstado(String usuario, String est, int index)throws RemoteException;

    public List<EstadoInterface> getEstados() throws RemoteException;
    public List<MensajeInterface> getMensajes() throws RemoteException;
    public void sendMensaje( String destinatario, String mensaje, Date fecha)throws RemoteException ;

}
