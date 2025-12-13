/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.controller;

import com.rentacar.view.DlgNuevoVehiculoView;
import com.rentacar.view.DlgVehiculoView;
import com.rentacar.model.VehiculoModel;
import com.rentacar.pojo.Vehiculo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jonat
 */
public class VehiculoController implements ActionListener, KeyListener, WindowListener {

    private Vehiculo vehiculo;
    private VehiculoModel vehModel;

    private DlgVehiculoView frmA;
    private DlgNuevoVehiculoView frmB;
    private int operacion;

    DefaultTableModel modelT;

    public VehiculoController(Vehiculo veh, VehiculoModel model, DlgVehiculoView frmMain,
            DlgNuevoVehiculoView frmNew) {
        this.vehiculo = veh;
        this.vehModel = model;

        this.frmA = frmMain;
        this.frmB = frmNew;

        this.frmA.addWindowListener(this); // Vista principal de Vehículo
        this.frmB.addWindowListener(this); // Vista Nuevo-Modificar Vehículo

        this.frmA.btnNuevo.addActionListener(this);
        this.frmA.btnEditar.addActionListener(this);
        this.frmA.btnEliminar.addActionListener(this);
        this.frmA.txtBuscar.addKeyListener(this);

        this.frmB.btnGuardar.addActionListener(this);
        this.frmB.btnLimpiar.addActionListener(this);
        this.frmB.btnCancelar.addActionListener(this);
    }

    public void iniciarVistaNuevo(String titulo) {
        frmB.setTitle(titulo);
        frmB.setLocationRelativeTo(null);
    }

    public void iniciarVistaMain() {
        frmB.setTitle("Gestión de Vehículos");
        frmB.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        
        if (e.getSource() == frmA.btnNuevo) {  //Insertar un registro nuevo
            this.operacion = 1;
            iniciarVistaNuevo("Registro de Vehículos");
            frmB.txtPlaca.setEnabled(true);
            frmB.setVisible(true);
        }

        if (e.getSource() == frmA.btnEditar) { // Modificar un vehiculo

    if (frmA.tblVehiculos.getSelectedRowCount() == 1) {
        int fila = frmA.tblVehiculos.getSelectedRow();

        
        frmB.txtPlaca.setText(frmA.tblVehiculos.getValueAt(fila, 0).toString());
        frmB.txtPlaca.setEnabled(false);

        frmB.txtMarca.setText(frmA.tblVehiculos.getValueAt(fila, 1).toString());
        frmB.txtVIN.setText(frmA.tblVehiculos.getValueAt(fila, 2).toString());
        frmB.txtModelo.setText(frmA.tblVehiculos.getValueAt(fila, 3).toString());
        frmB.cmbTipo.setSelectedItem(frmA.tblVehiculos.getValueAt(fila, 4).toString());
        frmB.txtPrecio.setText(frmA.tblVehiculos.getValueAt(fila, 5).toString());

        this.operacion = 2;
        iniciarVistaNuevo("Actualizar Vehiculo");
        frmB.setVisible(true);

    } else {
        JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
    }
}

        if (e.getSource() == frmA.btnEliminar) { //Eliminar un vehiculo

            if (frmA.tblVehiculos.getSelectedRowCount() == 1) {
                int fila = frmA.tblVehiculos.getSelectedRow();
                vehiculo.setPlaca(frmA.tblVehiculos.getValueAt(fila, 0).toString());
                int resp = JOptionPane.showConfirmDialog(frmA, "Desea eliminar el vehiculo " + vehiculo.getPlaca());

                if (resp == 0) {
                    if (vehModel.eliminarVehiculo(vehiculo.getPlaca())) {
                        JOptionPane.showMessageDialog(frmA, "Vehiculo Eliminado");
                    } else {
                        JOptionPane.showMessageDialog(frmA, "Error al eliminar el registro");
                    }
                }

            } else {
                JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
            }

        }

        //Botones de FrmNuevoPacienteView
        if (e.getSource() == frmB.btnGuardar) {

            vehiculo.setPlaca(frmB.txtPlaca.getText());
            vehiculo.setMarca(frmB.txtMarca.getText());
            vehiculo.setVin(frmB.txtVIN.getText());
           
            vehiculo.setModelo(frmB.txtModelo.getText());
            vehiculo.setTipo(frmB.cmbTipo.getSelectedItem().toString());
            vehiculo.setPrecio(Double.valueOf(frmB.txtPrecio.getText()));
            if (operacion == 1) {//Si se quiere insertar un nuevo registro

                if (vehModel.insertarVehiculo(vehiculo)) {
                    JOptionPane.showMessageDialog(frmB, "Vehículo Registrado");
                    limpiarVistaNuevo();
                } else {
                    JOptionPane.showMessageDialog(frmB, "Error al guardar");
                }
            } else {//Si se quiere actualizar un registro existente
                if (vehModel.modificarVehiculo(vehiculo)) {
                    JOptionPane.showMessageDialog(frmB, "Vehículo Modificado");
                    limpiarVistaNuevo();
                    frmB.dispose();
                } else {
                    JOptionPane.showMessageDialog(frmB, "Error al editar");
                }

            }

        }

        if (e.getSource() == frmB.btnLimpiar) {
            limpiarVistaNuevo();
        }
        if (e.getSource() == frmB.btnCancelar) {
            frmB.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

        modelT = vehModel.filtrarVehiculos(frmA.txtBuscar.getText());
        frmA.tblVehiculos.setModel(modelT);
        frmA.txtRegs.setText(String.valueOf(modelT.getRowCount()));
    }

    public void limpiarVistaNuevo() {
        frmB.txtPlaca.setText(null);
        frmB.txtMarca.setText(null);
        frmB.txtVIN.setText(null);
        frmB.txtModelo.setText(null);
        frmB.cmbTipo.setSelectedIndex(0);
        frmB.txtPrecio.setText(null);
        frmB.txtPlaca.requestFocus();
        frmB.txtPlaca.setEnabled(true);
    }

    @Override
    public void windowActivated(WindowEvent we) {

        modelT = vehModel.mostrarVehiculos();
        frmA.tblVehiculos.setModel(modelT);
        frmA.txtRegs.setText(String.valueOf(modelT.getRowCount()));

    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }

}
