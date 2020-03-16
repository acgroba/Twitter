/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PlatformInterface extends Remote {

    public Usuario login(String name, String password) throws RemoteException;

    public boolean register(String name, String password) throws RemoteException;

    public String[] getData(String user, String password) throws RemoteException;

    public boolean exists(String user) throws RemoteException;

    public void insertUser(String user, String password) throws RemoteException;

    public List<String> getFollowers(String user) throws RemoteException;

    public List<Usuario> getFollowed(String user) throws RemoteException;

    public void follow(String seguidor, String seguido) throws RemoteException;

    public void unfollow(String seguidor, String seguido) throws RemoteException;

    public void updateBio(String user, String biografia) throws RemoteException;

    public void updatePassword(String user, String password) throws RemoteException;

    public String selectAll() throws RemoteException;

    public Usuario buscarUsuario(String user) throws RemoteException;

    public List<EstadoInterface> getEstados(String user) throws RemoteException;

    public void addEstado(String user, EstadoInterface estado) throws RemoteException;

    public void deleteEstado(String user, EstadoInterface estado) throws RemoteException;
    public String  getBio(String user) throws RemoteException;
    public void   sendMessage(MensajeInterface mensaje) throws RemoteException;
    public List<MensajeInterface>  getMensajes(String user) throws RemoteException;

 
     
}
