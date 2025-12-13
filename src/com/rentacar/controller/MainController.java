/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.controller;



import com.rentacar.controller.ReservaController;
import com.rentacar.model.ClienteModel;
import com.rentacar.model.EmpleadoModel;
import com.rentacar.model.ReservaModel;
import com.rentacar.model.TemporadaModel;
import com.rentacar.view.DlgNuevoVehiculoView;
import com.rentacar.view.DlgVehiculoView;
import com.rentacar.model.VehiculoModel;
import com.rentacar.pojo.Cliente;
import com.rentacar.pojo.Empleado;
import com.rentacar.pojo.Reserva;
import com.rentacar.pojo.Temporada;
import com.rentacar.pojo.Vehiculo;
import com.rentacar.view.DlgClienteView;
import com.rentacar.view.DlgEmpleadoView;
import com.rentacar.view.DlgNuevaReservaView;
import com.rentacar.view.DlgNuevaTemporadaView;
import com.rentacar.view.DlgNuevoClienteView;
import com.rentacar.view.DlgNuevoEmpleadoView;
import com.rentacar.view.DlgReservasView;
import com.rentacar.view.FrmRentacarView;
import com.rentacar.view.DlgTemporadaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author jonat
 */
public class MainController implements ActionListener{
    private FrmRentacarView frmRentaCar;
    
    // ===== RESERVAS =====
private Reserva res;
private ReservaModel resModel;

private DlgReservasView dlgReservasView;       // LISTA
private DlgNuevaReservaView dlgNuevaReserva;   // NUEVA

private ReservaController resController;
    
    //Vehiculos
    Vehiculo veh = new Vehiculo();
    VehiculoModel VehiculoModel = new VehiculoModel();
    DlgVehiculoView frmMainVehiculos = new DlgVehiculoView(frmRentaCar, false);
    DlgNuevoVehiculoView frmNewVehiculos = new DlgNuevoVehiculoView(frmRentaCar, true);
    VehiculoController vehController = new VehiculoController(veh, VehiculoModel, frmMainVehiculos,frmNewVehiculos);
    
    //Clientes
    Cliente cli = new Cliente();
    ClienteModel cliModel = new ClienteModel();
    DlgClienteView frmMainClientes = new DlgClienteView(frmRentaCar, false);
    DlgNuevoClienteView frmNewClientes = new DlgNuevoClienteView(frmRentaCar, true);
    ClienteController cliController = new ClienteController(cli, cliModel, frmMainClientes, frmNewClientes);
    
   
    //Empleados
    
    Empleado emp = new Empleado();
    EmpleadoModel empModel = new EmpleadoModel();
    DlgEmpleadoView frmMainEmpleados = new DlgEmpleadoView(frmRentaCar, false);
    DlgNuevoEmpleadoView frmNewEmpleados = new DlgNuevoEmpleadoView(frmRentaCar, true);
    EmpleadoController empController = new EmpleadoController(emp, empModel, frmMainEmpleados, frmNewEmpleados);
    
    //Temporadas
    Temporada temp = new Temporada();
    TemporadaModel tempModel = new TemporadaModel();
    DlgTemporadaView frmMainTemporadas = new DlgTemporadaView(frmRentaCar, false);
    DlgNuevaTemporadaView frmNewTemporadas = new DlgNuevaTemporadaView(frmRentaCar, true);
    TemporadaController tempController = new TemporadaController(temp, tempModel, frmMainTemporadas, frmNewTemporadas);
    
    
    
    
    
    // Reservas
    
  



    public MainController(FrmRentacarView frmRentaCar){
        this.frmRentaCar = frmRentaCar;
        this.frmRentaCar.btnClientes.addActionListener(this);
        this.frmRentaCar.btnReservas.addActionListener(this);
        this.frmRentaCar.btnEmpleados.addActionListener(this);
        this.frmRentaCar.btnVehiculos.addActionListener(this);
        this.frmRentaCar.btnTemporadas.addActionListener(this);
        // ===== RESERVAS: inicializar aquí (NO como campos) =====
    res = new Reserva();
    resModel = new ReservaModel();

    dlgReservasView = new DlgReservasView(this.frmRentaCar, false);     // LISTA
    dlgNuevaReserva = new DlgNuevaReservaView(this.frmRentaCar, true);  // NUEVA

    // Controller que manejará la lista y abrirá la nueva
    resController = new ReservaController(res, resModel, dlgReservasView, dlgNuevaReserva);
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
       // Dentro de MainController.java
if (e.getSource() == frmRentaCar.btnReservas) {
    dlgReservasView.setLocationRelativeTo(frmRentaCar);
    dlgReservasView.setVisible(true);   // ABRE LISTA
}


}
        


    }
    

