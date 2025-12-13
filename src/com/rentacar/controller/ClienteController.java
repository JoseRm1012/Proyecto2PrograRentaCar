package com.rentacar.controller;

import com.rentacar.model.ClienteModel;
import com.rentacar.pojo.Cliente;
import com.rentacar.view.DlgClienteView;
import com.rentacar.view.DlgNuevoClienteView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClienteController implements ActionListener, KeyListener, WindowListener {

    private Cliente cliente;
    private ClienteModel cliModel;

    private DlgClienteView frmA;          // vista principal
    private DlgNuevoClienteView frmB;     // vista nuevo/editar
    private int operacion;                // 1 = insertar, 2 = actualizar

    DefaultTableModel modelT;

    public ClienteController(Cliente cli, ClienteModel model,
                             DlgClienteView frmMain, DlgNuevoClienteView frmNew) {

        this.cliente = cli;
        this.cliModel = model;

        this.frmA = frmMain;
        this.frmB = frmNew;

        this.frmA.addWindowListener(this);
        this.frmB.addWindowListener(this);

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
        frmA.setTitle("Gestión de Clientes");
        frmA.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Botones de DlgClienteView (principal)
        if (e.getSource() == frmA.btnNuevo) {   // Insertar
            this.operacion = 1;
            limpiarVistaNuevo();
            frmB.txtCedula.setEnabled(true);
            iniciarVistaNuevo("Registro de Clientes");
            frmB.setVisible(true);
        }

        if (e.getSource() == frmA.btnEditar) {  // Modificar

            if (frmA.tblClientes.getSelectedRowCount() == 1) {
                int fila = frmA.tblClientes.getSelectedRow();

                frmB.txtCedula.setText(frmA.tblClientes.getValueAt(fila, 0).toString());
                frmB.txtCedula.setEnabled(false); // PK no se cambia
                frmB.txtNombre.setText(frmA.tblClientes.getValueAt(fila, 1).toString());
                frmB.txtFechaNac.setText(frmA.tblClientes.getValueAt(fila, 2).toString());
                frmB.txtDireccion.setText(frmA.tblClientes.getValueAt(fila, 3).toString());
                frmB.txtTelefono.setText(frmA.tblClientes.getValueAt(fila, 4).toString());
                frmB.txtEmail.setText(frmA.tblClientes.getValueAt(fila, 5) == null
                        ? "" : frmA.tblClientes.getValueAt(fila, 5).toString());

                this.operacion = 2;
                iniciarVistaNuevo("Actualizar Cliente");
                frmB.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
            }
        }

        if (e.getSource() == frmA.btnEliminar) { // Eliminar

            if (frmA.tblClientes.getSelectedRowCount() == 1) {
                int fila = frmA.tblClientes.getSelectedRow();
                int cedula = Integer.parseInt(frmA.tblClientes.getValueAt(fila, 0).toString());

                int resp = JOptionPane.showConfirmDialog(
                        frmA,
                        "¿Desea eliminar el cliente con cédula " + cedula + "?"
                );

                if (resp == JOptionPane.YES_OPTION) {
                    if (cliModel.eliminarCliente(cedula)) {
                        JOptionPane.showMessageDialog(frmA, "Cliente eliminado");
                    } else {
                        JOptionPane.showMessageDialog(frmA, "Error al eliminar el registro");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
            }
        }

        // Botones de DlgNuevoClienteView
        if (e.getSource() == frmB.btnGuardar) {

            cliente.setCedula(Integer.parseInt(frmB.txtCedula.getText()));
            cliente.setNombre(frmB.txtNombre.getText());

            // txtFechaNac en formato yyyy-MM-dd
            cliente.setFechNac(Date.valueOf(frmB.txtFechaNac.getText()));

            cliente.setDireccion(frmB.txtDireccion.getText());
            cliente.setTelefono(frmB.txtTelefono.getText());
            cliente.setEmail(frmB.txtEmail.getText().isEmpty()
                    ? null : frmB.txtEmail.getText());

            if (operacion == 1) { // insertar
                if (cliModel.insertarCliente(cliente)) {
                    JOptionPane.showMessageDialog(frmB, "Cliente registrado");
                    limpiarVistaNuevo();
                } else {
                    JOptionPane.showMessageDialog(frmB, "Error al guardar");
                }
            } else {              // actualizar
                if (cliModel.modificarCliente(cliente)) {
                    JOptionPane.showMessageDialog(frmB, "Cliente modificado");
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
        modelT = cliModel.filtrarClientes(frmA.txtBuscar.getText());
        frmA.tblClientes.setModel(modelT);
        frmA.txtRegs.setText(String.valueOf(modelT.getRowCount()));
    }

    public void limpiarVistaNuevo() {
        frmB.txtCedula.setText(null);
        frmB.txtNombre.setText(null);
        frmB.txtFechaNac.setText(null);
        frmB.txtDireccion.setText(null);
        frmB.txtTelefono.setText(null);
        frmB.txtEmail.setText(null);
        frmB.txtCedula.requestFocus();
    }

    @Override
    public void windowActivated(WindowEvent we) {
        modelT = cliModel.mostrarClientes();
        frmA.tblClientes.setModel(modelT);
        frmA.txtRegs.setText(String.valueOf(modelT.getRowCount()));
    }

    @Override public void keyTyped(KeyEvent e) {
    
    }
    @Override public void keyPressed(KeyEvent e) { 
    
    }
    @Override public void windowOpened(WindowEvent e) { 
    }
    
    @Override public void windowClosing(WindowEvent e) {
    
    }
    
    @Override public void windowClosed(WindowEvent e) { 
    
    }
    @Override public void windowIconified(WindowEvent e) { 
    
    }
    @Override public void windowDeiconified(WindowEvent e) { 
    
    }
    @Override public void windowDeactivated(WindowEvent e) {
    
    }
}
