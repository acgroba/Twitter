/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 *
 */
import interfaces.PlatformInterface;
import interfaces.RegistroCallbacks;
import interfaces.Usuario;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import ui.AL;
import ui.LoginDialog;
import ui.MenuInicial;
import ui.MenuPrincipal;
import ui.RegDialog;

public class Client {

    /**
     * @param args the command line arguments
     */
    private static final String REMOTE = "localhost";
    static RegistroCallbacks registroCallbacks;
    
    

    public static void main(String[] args) {

        System.setProperty("java.rmi.server.useCodebaseOnly", "false"); //permite carga automatica de clases
        System.setProperty("java.security.policy", "seguridad.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Registry registro = LocateRegistry.getRegistry(REMOTE, Registry.REGISTRY_PORT);
            PlatformInterface platform = (PlatformInterface) registro.lookup("Platform");
            registroCallbacks = (RegistroCallbacks)registro.lookup("RegistroCallbacks");
while(true){
             final JFrame frame = new JFrame("Menu principal");
           MenuInicial menu0=new MenuInicial(frame);
           
            
           boolean autenticated=false;
            while (!autenticated) {
                Thread.sleep(100);
                autenticated = menu0.getPermited();
            }
           Usuario user=(Usuario)menu0.getUser();
           
        
            frame.dispose();
           

            
                MenuPrincipal dialog = new MenuPrincipal(new javax.swing.JFrame(), true,user, registroCallbacks);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
                
                while (dialog.closed==false){
                    Thread.sleep(1);
                }
}

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
