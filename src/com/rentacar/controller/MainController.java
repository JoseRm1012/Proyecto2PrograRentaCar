/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.controller;

import com.rentacar.model.ClienteModel;
import com.rentacar.view.FrmNuevoVehiculoView;
import com.rentacar.view.FrmVehiculoView;
import com.rentacar.model.VehiculoModel;
import com.rentacar.pojo.Cliente;
import com.rentacar.pojo.Vehiculo;
import com.rentacar.view.FrmClienteView;
import com.rentacar.view.FrmNuevoClienteView;
import com.rentacar.view.FrmRentacarView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author jonat
 */
public class MainController implements ActionListener{
    private FrmRentacarView frmRentaCar;
    
    Vehiculo veh = new Vehiculo();
    VehiculoModel VehiculoModel = new VehiculoModel();
    FrmVehiculoView frmMainVehiculos = new FrmVehiculoView(frmRentaCar, false);
    FrmNuevoVehiculoView frmNewVehiculos = new FrmNuevoVehiculoView(frmRentaCar, true);
    VehiculoController vehController = new VehiculoController(veh, VehiculoModel, frmMainVehiculos,frmNewVehiculos);
    
    Cliente cli = new Cliente();
    ClienteModel cliModel = new ClienteModel();
    FrmClienteView frmMainClientes = new FrmClienteView(frmRentaCar, false);
    FrmNuevoClienteView frmNewClientes = new FrmNuevoClienteView(frmRentaCar, true);

    ClienteController cliController = new ClienteController(cli, cliModel, frmMainClientes, frmNewClientes);
    
   
    
    public MainController(FrmRentacarView frmRentaCar){
        this.frmRentaCar = frmRentaCar;
        this.frmRentaCar.btnClientes.addActionListener(this);
        this.frmRentaCar.btnReservas.addActionListener(this);
        this.frmRentaCar.btnEmpleados.addActionListener(this);
        this.frmRentaCar.btnVehiculos.addActionListener(this);
        this.frmRentaCar.btnTemporadas.addActionListener(this);
        iniciarVistaRentaCar("Renta Car - Autos Deluxe");
    }
    
    private void iniciarVistaRentaCar(String titulo) {
        frmRentaCar.setTitle(titulo);
        frmRentaCar.setLocationRelativeTo(null);
        frmRentaCar.setVisible(true);
    }
     
    @Override
    public void actionPerformed(ActionEvent e) {

        //Botones de FrmClinicaView
        if (e.getSource() == frmRentaCar.btnClientes) {  //Abre la gestión de clientes
           // frmMainVehiculos.setVisible(true);
        }else if (e.getSource() == frmRentaCar.btnVehiculos) {  //Abre la gestión de vehiculos
            frmMainVehiculos.setVisible(true);
            
        }
        
        if (e.getSource() == frmRentaCar.btnClientes) {
    frmMainClientes.setVisible(true);
    }
    }
}