
package server;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import interfaces.RegistroCallbacks;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {
  private static final String db = "jdbc:sqlite:db/database.db";
  private static final String REMOTE="localhost";
  
   
    public static void main(String[] args) {
       
        
       
        System.setProperty("java.rmi.server.hostname", REMOTE);
        System.setProperty("java.security.policy", "seguridad.policy");
        System.setProperty("java.rmi.server.codebase", "http://" + REMOTE + "/server/");
        

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try{
            Registry registro = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            RegistroCallbacksImpl rc= new RegistroCallbacksImpl();
            PlatformImpl p= new PlatformImpl(rc);
         
            
            registro.rebind("Platform", p);
            registro.rebind("RegistroCallbacks", rc);
        } catch (RemoteException e){
            e.printStackTrace();
        }
        
        File af = new File("db/database.db");
        
        if (!af.exists()){
            createDB();

         }
        
    }
    
    
    
     public static void createDB(){
                    
       
        String sql1="CREATE TABLE  USUARIOS (\n" +
"    user varchar(15) NOT NULL,\n" +
"    password varchar(15) NOT NULL,\n" +
"    biografia varchar(200),\n" +
"    PRIMARY KEY(user)\n" +
")";
        String sql2="CREATE TABLE  SEGUIMIENTOS (\n" +
"    seguidor varchar(15) NOT NULL,\n" +
"    seguido varchar(15) NOT NULL,\n" +
"     FOREIGN KEY (seguidor) REFERENCES USUARIOS(user),\n" +
" FOREIGN KEY (seguido) REFERENCES USUARIOS(user),\n" +
"    CONSTRAINT PK_SEGUIMIENTOS  PRIMARY KEY (seguidor,seguido)\n" +
")";
        

String sql3="CREATE TABLE ESTADOS(\n" +


"    user varchar(15) NOT NULL,\n" +
"	estado VARCHAR(140) NOT NULL,\n" +
"	fecha datetime NOT NULL,\n" +
" FOREIGN KEY (user) REFERENCES USUARIOS(user)\n" +

")";

String sql4="CREATE TABLE MENSAJES(\n" +


"    remitente varchar(15) NOT NULL,\n" +
"	destinatario VARCHAR(15) NOT NULL,\n" +
"	fecha datetime NOT NULL,\n" +
     "	mensaje VARCHAR(300) NOT NULL\n" +


")";

     
        
        try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql3);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
         try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
}