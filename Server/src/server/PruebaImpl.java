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


 import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import common.*;

public class PruebaImpl extends UnicastRemoteObject
                         implements PruebaInterface, Serializable {
    


    protected PruebaImpl() throws RemoteException {
        super();
    }

   
    public String test() throws RemoteException {
        return "Funciona!";
    }
}