/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import interfaces.EstadoInterface;
import interfaces.MensajeInterface;
import interfaces.PlatformInterface;
import interfaces.Usuario;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import server.PlatformImpl;
/**
 *
 * @author abraham
 */
public class UsuarioImpl  extends UnicastRemoteObject implements Usuario  {

    private static final String db = "jdbc:sqlite:db/database.db";
    String user;
    String password;
    String biografia;
    List<Usuario> seguidos;
    List<String> seguidores;
    List<EstadoInterface> estados;
    PlatformInterface p;
 
       
   

    public UsuarioImpl  (String user, String password, String biografia, List<String> seguidores, List<Usuario> seguidos,List<EstadoInterface> estados, PlatformInterface p) throws RemoteException{
         super();
        this.user=user;
        this.password=password;
        this.biografia=biografia;
        this.seguidos=seguidos;
        this.seguidores=seguidores;
        this.estados=estados;
        this.p=p;
    }
    public String getUser() throws RemoteException {
        return user;
    }

   

    public String getPassword()throws RemoteException {
        return password;
    }
    
    public void setPassword(String password) throws RemoteException{
       try{
  
    p.updatePassword(this.user,password);
       } catch(Exception e){
       }
           this.password = password;
          
    }

    public void setBiografia(String bio) throws RemoteException{
         try{
    
    p.updateBio(this.user,bio);
       } catch(Exception e){
       }
        this.biografia = bio;
    }
    
     public String getBiografia() throws RemoteException {
      String toret="";
         try{
    
    toret =p.getBio(this.user);
           
       } catch(Exception e){
       }
       return toret;
    }

    

    public List<Usuario> getSeguidos() throws RemoteException {
     return this.seguidos;
    }
        public List<String> getSeguidores() throws RemoteException{
            List<String> seg =new ArrayList <String>();
             try{
   
    seg =p.getFollowers(this.user);
       } catch(Exception e){
       }
        return seg;
    }
        public List<MensajeInterface> getMensajes() throws RemoteException {
          List <MensajeInterface> toret= new ArrayList<MensajeInterface>();
        try{

    toret =p.getMensajes(this.user);
       } catch(Exception e){
       }
        return toret;
    }
        
         public List<EstadoInterface> getEstados() throws RemoteException {
          List <EstadoInterface> toret= new ArrayList<EstadoInterface>();
        try{

    toret =p.getEstados(this.user);
       } catch(Exception e){
       }
        return toret;
    }
         
          public void sendMensaje( String destinatario, String mensaje, Date fecha)throws RemoteException {
         try{
              MensajeInterface message=new MensajeImpl(this.user, destinatario, mensaje , fecha);
   
    p.sendMessage(message);
  
       } catch(Exception e){
       }
        
    }
         
         public void addEstado(String usuario, String est, Date fecha)throws RemoteException {
         try{
             EstadoImpl estado=new EstadoImpl(usuario, est,fecha);
   
    p.addEstado(this.user,estado);
    this.estados.add(estado);
       } catch(Exception e){
       }
        
    }
         
        public void deleteEstado(String usuario, String est, int index)throws RemoteException {
         try{
       
              EstadoImpl estado=new EstadoImpl(usuario, est,null);
   
    p.deleteEstado(this.user,estado);
    this.estados.remove(index);
       } catch(Exception e){
       }
        
    }


    public void dejarSeguir(String user) throws RemoteException {
         try{
  
    p.unfollow(this.user,user);
    this.seguidos=p.getFollowed(this.user);
       } catch(Exception e){
       }
    }
    
     public void seguir(String user) throws RemoteException {
       try{
           
  
    p.follow(this.user,user);
     this.seguidos=p.getFollowed(this.user);
       } catch(Exception e){
       }
    }
     
     public Usuario buscarUsuario(String user)throws RemoteException {
         Usuario toret=null;
          try{
      
        toret=p.buscarUsuario(user);
             
       } catch(Exception e){
       }
          return toret;
     }



  
    
    
}
