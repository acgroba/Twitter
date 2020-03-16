/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import interfaces.Usuario;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author abraham
 */
public class MenuInicial {
    Usuario user;
    boolean permited;
    AL al;
    public MenuInicial(JFrame frame){
   
            final JButton btnLogin = new JButton("Iniciar sesión");
            final JButton btnReg = new JButton("Registro");
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 100);
            frame.setLayout(new FlowLayout());
            frame.getContentPane().add(btnLogin);
            frame.getContentPane().add(btnReg);
            frame.setVisible(true);

         

            al = new AL(frame);
            btnLogin.addActionListener(al);
            
            
            
            btnReg.addActionListener(
                    new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    RegDialog regDlg = new RegDialog(frame);
                    regDlg.setVisible(true);
                    // if logon successfully

                }
            });
    
              }
    public boolean getPermited(){
        permited=al.getPermited();
                        return permited;
                    }
                    public Usuario getUser(){
                        user=al.getUser();
                        return user;
                    }
}
