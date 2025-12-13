/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.rentacar.main;

import com.rentacar.controller.MainController;
import com.rentacar.view.FrmRentacarView;

/**
 *
 * @author jonat
 */
public class PrivateClinic {

   static FrmRentacarView frmClinic = new FrmRentacarView();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MainController mainControl = new MainController(frmClinic);
    }
    
}
