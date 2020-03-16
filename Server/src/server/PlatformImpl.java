/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import interfaces.ClienteCallback;
import interfaces.EstadoInterface;
import interfaces.MensajeInterface;
import interfaces.PlatformInterface;
import interfaces.RegistroCallbacks;
import interfaces.Usuario;
 import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PlatformImpl extends UnicastRemoteObject
                         implements PlatformInterface, Serializable {
    
    private static final String db = "jdbc:sqlite:db/database.db";
    RegistroCallbacks rc;
  
    protected PlatformImpl(RegistroCallbacks rc) throws RemoteException {
        super();
        this.rc=rc;
    }



   
   public Usuario login(String name, String password) throws RemoteException{
      String[] data= getData(name,password);
      
      if(data[0]==null){
          return null;
      }
      else{
           Usuario toret=null;
           System.out.println(data[0]);
           System.out.println(data[1]);
           System.out.println(data[2]);
          
          List<String> seguidores=getFollowers(name);
          List<Usuario> seguidos=getFollowed(name);
          List<EstadoInterface> estados=getEstados(name);
         
          System.out.println(seguidores);
          System.out.println(seguidos);
          
              try{
           toret= new UsuarioImpl(data[0],data[1],data[2],seguidores,seguidos,estados,this);
          }catch(RemoteException e){
              System.out.println(e.getMessage());
          }
          return toret;
          
      }
    }
 
    public boolean register(String name, String password) throws RemoteException{
       
       
        if(exists(name)){
            return false;
        }
        else {
           insertUser(name,password);
            return true;
        }
    }
    
    private Connection connect() {
        // SQLite connection 
     
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(db);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    
      public String[]  getData(String user, String password) throws RemoteException{
        String [] toret=new String[5];
        String sql = "SELECT user, password, biografia FROM USUARIOS WHERE (user='"+user+"' AND password='"+password+"')";
          
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
              toret[0]=rs.getString("user"); 
              toret[1]=rs.getString("password");
              toret[2]=rs.getString("biografia");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
         
         
        return toret;
    }
       public String  getBio(String user) throws RemoteException{
        String toret="";
        String sql = "SELECT  biografia FROM USUARIOS WHERE user='"+user+"'";
          
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
              toret=rs.getString("biografia"); 
            
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
         
         
        return toret;
    }
      
       public Usuario  buscarUsuario(String user) throws RemoteException{
        Usuario toret=null;
        String usuario="";
        String biografia="";
        String sql = "SELECT user, biografia  FROM USUARIOS  WHERE user='"+user+"'";
          
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            
            while (rs.next()) {
                
              usuario=rs.getString("user"); 
              biografia=rs.getString("biografia");
            
            }
          List<String> seguidores =getFollowers(user);
          List<Usuario> seguidos =getFollowed(user);
          List<EstadoInterface> estados =getEstados(user);
          toret=new UsuarioImpl(usuario,"",biografia, seguidores, seguidos, estados,this);
          
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
         if(usuario.equals("")){
             return null;
         }
         else{
         
        return toret;}
    }
       
       public boolean exists (String user) throws RemoteException{
        boolean toret=false;
        String sql = "SELECT user, password, biografia FROM USUARIOS WHERE user='"+user+"'";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
              toret=true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return toret;
    }
      public void  insertUser(String user, String password) throws RemoteException{
    
        
       
        String sql = "INSERT INTO USUARIOS(user,password,biografia) VALUES (?,?,?)";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            pstmt.setString(2, password);
            pstmt.setString(3, "");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
      
      
      
       
      public List<String>  getFollowers(String user) throws RemoteException{
        List<String> toret=new ArrayList<String>();
        
       
        String sql = "SELECT seguidor FROM SEGUIMIENTOS WHERE seguido='"+user+"'";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
              toret.add(rs.getString("seguidor")); 
                          }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return toret;
    }
         public List<MensajeInterface>  getMensajes(String user) throws RemoteException{
        List<MensajeInterface> toret=new ArrayList<MensajeInterface>();
        
       
        String sql = "SELECT remitente,mensaje, fecha FROM MENSAJES WHERE destinatario='"+user+"'";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
             String remitente=rs.getString("remitente");
             String mensaje=rs.getString("mensaje"); 
             Date fecha=new Date(rs.getTimestamp("fecha").getTime());
             toret.add(new MensajeImpl(remitente,user,mensaje,fecha));
                          }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return toret;
    }
         
      public List<EstadoInterface>  getEstados(String user) throws RemoteException{
        List<EstadoInterface> toret=new ArrayList<EstadoInterface>();
        
       
        String sql = "SELECT estado, user, fecha FROM ESTADOS WHERE user='"+user+"'";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
             String usuario=rs.getString("user");
             String estado=rs.getString("estado"); 
             Date fecha=new Date(rs.getTimestamp("fecha").getTime());
             toret.add(new EstadoImpl(usuario,estado,fecha));
                          }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return toret;
    }
      
    public List<Usuario>  getFollowed(String user) throws RemoteException{
        
        List<Usuario> toret=new ArrayList<Usuario>();
        
       
        String sql = "SELECT seguido FROM SEGUIMIENTOS WHERE seguidor='"+user+"'";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
              toret.add(new UsuarioImpl(rs.getString("seguido"),"","",null,null, this.getEstados(rs.getString("seguido")),this)); 
                          }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return toret;
    }
    
    public void   follow(String seguidor, String seguido) throws RemoteException{
        
       
        String sql = "INSERT INTO SEGUIMIENTOS(seguidor,seguido) VALUES (?,?)";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, seguidor);
            pstmt.setString(2, seguido);
            
            pstmt.executeUpdate();
             Iterator <ClienteCallback> cc=this.rc.getCallbacks().iterator();
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
   public void   sendMessage(MensajeInterface mensaje) throws RemoteException{
        
       
        String sql = "INSERT INTO MENSAJES (remitente,destinatario,mensaje,fecha) VALUES (?,?,?,?)";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mensaje.getRemitente());
            pstmt.setString(2, mensaje.getDestinatario());
            pstmt.setString(3, mensaje.getMensaje());                
            pstmt.setDate(4, new java.sql.Date(mensaje.getFecha().getTime()));
            
            pstmt.executeUpdate();
            /*Iterator <ClienteCallback> cc=this.rc.getCallbacks().iterator();
            while (cc.hasNext()){
                ClienteCallback clcb=cc.next();
                if(clcb.getTarget().equals(user)){
                clcb.actualizarEstados();
                }
            }*/
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
      public void   addEstado(String user, EstadoInterface estado) throws RemoteException{
        
       
        String sql = "INSERT INTO ESTADOS (user,estado,fecha) VALUES (?,?,?)";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, estado.getUser());
            pstmt.setString(2, estado.getEstado());
             pstmt.setDate(3, new java.sql.Date(estado.getFecha().getTime()));
            
            pstmt.executeUpdate();
            Iterator <ClienteCallback> cc=this.rc.getCallbacks().iterator();
            while (cc.hasNext()){
                ClienteCallback clcb=cc.next();
                if(clcb.getTarget().equals(user)){
                clcb.actualizarEstados();
                }
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
      public void   deleteEstado(String user, EstadoInterface estado) throws RemoteException{
        
       
        String sql = "DELETE FROM ESTADOS WHERE (user = ? AND estado=?) ";
          System.out.println(sql);
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, estado.getUser());
            pstmt.setString(2, estado.getEstado());
   
            
            pstmt.executeUpdate();
            Iterator <ClienteCallback> cc=this.rc.getCallbacks().iterator();
            while (cc.hasNext()){
                ClienteCallback clcb=cc.next();
                  if(clcb.getTarget().equals(user)){
                clcb.actualizarEstados();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
      
    public void   unfollow(String seguidor, String seguido) throws RemoteException{
        
      String sql = "DELETE FROM SEGUIMIENTOS WHERE (seguidor = ? AND seguido=?) ";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, seguidor);
             pstmt.setString(2, seguido);
            // execute the delete statement
            pstmt.executeUpdate();
          
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateBio(String user,String biografia) throws RemoteException {
        String sql = "UPDATE USUARIOS SET biografia = ?  "
                
                + "WHERE user = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, biografia);
            pstmt.setString(2, user);
            
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void updatePassword(String user,String password)  throws RemoteException {
        String sql = "UPDATE USUARIOS SET password = ?  "
                
                + "WHERE user = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding param
            pstmt.setString(1, password);
            pstmt.setString(2, user);
            
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 
    
    
    public String selectAll() throws RemoteException{
        String toret="";
        String sql = "SELECT * FROM USUARIOS";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            toret+="USUARIO\tCONTRASEÑA\tBIOGRAFIA\n";
            while (rs.next()) {
               toret+=rs.getString("user") +  "\t" + 
                                   rs.getString("password") + "\t" +
                                  rs.getString("biografia")+ "\n";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
            toret+="----------------------------------------------\n";
         sql = "SELECT * FROM SEGUIMIENTOS";
         try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            toret+="SEGUIDOR\tSEGUIDO\n";
            while (rs.next()) {
               toret+=rs.getString("seguidor") +  "\t" + 
                                   rs.getString("seguido")+ "\n";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
         toret+="----------------------------------------------\n";
         sql = "SELECT * FROM ESTADOS";
         try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            toret+="USUARIO\tESTADO\n";
            while (rs.next()) {
               toret+=rs.getString("user") +  "\t" + 
                                   rs.getString("estado")+ "\n";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
         return toret;
    }

    
    
    
}