/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import interfaces.Usuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author abraham
 */
public class AL implements ActionListener {
 LoginDialog loginDlg;
 public boolean permited=false;
 Usuario user;
 JFrame frame;
 
 public AL(JFrame frame){
     this.frame=frame;
 }
                    public void actionPerformed(ActionEvent e) {
                        loginDlg = new LoginDialog(frame);
                        loginDlg.setVisible(true);
                        
                        permited=loginDlg.isSucceeded();
                       
                        user=loginDlg.getUser();
                       
                       
                    }
                    public boolean getPermited(){
                        return permited;
                    }
                    public Usuario getUser(){
                        return user;
                    }
                    
}