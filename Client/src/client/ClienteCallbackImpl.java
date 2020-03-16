/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.ClienteCallback;
import interfaces.EstadoInterface;
import interfaces.Usuario;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author abraham
 */
public class ClienteCallbackImpl extends UnicastRemoteObject implements ClienteCallback {
    Usuario user;
    JList <String> estados;
    String target;
    
    public ClienteCallbackImpl(JList <String> estados, Usuario user, String target) throws RemoteException {
        super();
      
        this.estados=estados;
        this.user=user;
        this.target=target;
    }

    public String getTarget() throws RemoteException{
        return this.target;
    }
     public Usuario getUser() throws RemoteException{
        return this.user;
    }

    public void actualizarEstados() throws RemoteException{
      
estados.setModel(new javax.swing.AbstractListModel<String>() {
    public ArrayList<EstadoInterface> getEstados(){
        ArrayList<EstadoInterface> estados = new ArrayList<EstadoInterface>();
        try{

            Iterator <Usuario> seguidos= user.getSeguidos().iterator();
            while(seguidos.hasNext()){
                Usuario seguido=seguidos.next();
                Iterator<EstadoInterface> estadosSeguido=seguido.getEstados().iterator();
                while(estadosSeguido.hasNext()){
                    EstadoInterface es=estadosSeguido.next();
                    estados.add(es);
                }
            }
            Iterator<EstadoInterface> estadosPropios=user.getEstados().iterator();
            while(estadosPropios.hasNext()){
                EstadoInterface es=estadosPropios.next();
                estados.add(es);
            }
            Collections.sort(estados, new Comparator<EstadoInterface>() {
                public int compare(EstadoInterface o1, EstadoInterface o2) {
                    try{
                        return o1.getFecha().compareTo(o2.getFecha());
                    }catch(Exception e){
                        return -1;
                    }

                }
            });
        } catch(Exception e){}
        return estados;
    }
    public int getSize() {
        int toret=0;
        try {
            toret= this.getEstados().size();
        } catch( Exception e)
        {}
        return toret;
    }
    public String getElementAt(int i) {
        String toret="";

        EstadoInterface  estado=this.getEstados().get(this.getSize()-i-1);
        DateFormat hourFormat = new SimpleDateFormat("dd/MM HH:mm");
        try{
            toret=estado.getUser()+"("+hourFormat.format(estado.getFecha())+"):"+estado.getEstado();
        }catch(Exception e){}
        return toret;
    }
});
    }

}