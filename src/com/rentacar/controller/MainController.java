/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.controller;

import com.rentacar.model.ClienteModel;
import com.rentacar.model.EmpleadoModel;
import com.rentacar.model.TemporadaModel;
import com.rentacar.view.FrmNuevoVehiculoView;
import com.rentacar.view.FrmVehiculoView;
import com.rentacar.model.VehiculoModel;
import com.rentacar.pojo.Cliente;
import com.rentacar.pojo.Empleado;
import com.rentacar.pojo.Temporada;
import com.rentacar.pojo.Vehiculo;
import com.rentacar.view.FrmClienteView;
import com.rentacar.view.FrmEmpleadoView;
import com.rentacar.view.FrmNuevaTemporadaView;
import com.rentacar.view.FrmNuevoClienteView;
import com.rentacar.view.FrmNuevoEmpleadoView;
import com.rentacar.view.FrmRentacarView;
import com.rentacar.view.FrmTemporadaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author jonat
 */
public class MainController implements ActionListener{
    private FrmRentacarView frmRentaCar;
    
    //Vehiculos
    Vehiculo veh = new Vehiculo();
    VehiculoModel VehiculoModel = new VehiculoModel();
    FrmVehiculoView frmMainVehiculos = new FrmVehiculoView(frmRentaCar, false);
    FrmNuevoVehiculoView frmNewVehiculos = new FrmNuevoVehiculoView(frmRentaCar, true);
    VehiculoController vehController = new VehiculoController(veh, VehiculoModel, frmMainVehiculos,frmNewVehiculos);
    
    //Clientes
    Cliente cli = new Cliente();
    ClienteModel cliModel = new ClienteModel();
    FrmClienteView frmMainClientes = new FrmClienteView(frmRentaCar, false);
    FrmNuevoClienteView frmNewClientes = new FrmNuevoClienteView(frmRentaCar, true);
    ClienteController cliController = new ClienteController(cli, cliModel, frmMainClientes, frmNewClientes);
    
   
    //Empleados
    
    Empleado emp = new Empleado();
    EmpleadoModel empModel = new EmpleadoModel();
    FrmEmpleadoView frmMainEmpleados = new FrmEmpleadoView(frmRentaCar, false);
    FrmNuevoEmpleadoView frmNewEmpleados = new FrmNuevoEmpleadoView(frmRentaCar, true);
    EmpleadoController empController = new EmpleadoController(emp, empModel, frmMainEmpleados, frmNewEmpleados);
    
    //Temporadas
    Temporada temp = new Temporada();
    TemporadaModel tempModel = new TemporadaModel();
    FrmTemporadaView frmMainTemporadas = new FrmTemporadaView(frmRentaCar, false);
    FrmNuevaTemporadaView frmNewTemporadas = new FrmNuevaTemporadaView(frmRentaCar, true);
    TemporadaController tempController = new TemporadaController(temp, tempModel, frmMainTemporadas, frmNewTemporadas);
    
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
     // Gestión de Empleados
       if (e.getSource()== frmRentaCar.btnEmpleados) {
           frmMainEmpleados.setVisible(true);
      }
    
    //Gestion de temporadas
       if (e.getSource()== frmRentaCar.btnTemporadas) {
          frmMainTemporadas.setVisible(true);
        }

    }
}