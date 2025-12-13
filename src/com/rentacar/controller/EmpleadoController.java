package com.rentacar.controller;

import com.rentacar.model.EmpleadoModel;
import com.rentacar.pojo.Empleado;
import com.rentacar.view.DlgEmpleadoView;
import com.rentacar.view.DlgNuevoEmpleadoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmpleadoController implements ActionListener, KeyListener, WindowListener {

    private Empleado empleado;
    private EmpleadoModel empModel;

    private DlgEmpleadoView frmA;          // vista principal
    private DlgNuevoEmpleadoView frmB;     // vista nuevo/editar
    private int operacion;                 // 1 = insertar, 2 = actualizar

    DefaultTableModel modelT;

    public EmpleadoController(Empleado emp,
                              EmpleadoModel model,
                              DlgEmpleadoView frmMain,
                              DlgNuevoEmpleadoView frmNew) {

        this.empleado = emp;
        this.empModel = model;

        this.frmA = frmMain;
        this.frmB = frmNew;

        // listeners de ventana
        this.frmA.addWindowListener(this);
        this.frmB.addWindowListener(this);

        // botones de la vista principal
        this.frmA.btnNuevo.addActionListener(this);
        this.frmA.btnEditar.addActionListener(this);
        this.frmA.btnEliminar.addActionListener(this);
        this.frmA.txtBuscar.addKeyListener(this);

        // botones del formulario nuevo/editar
        this.frmB.btnGuardar.addActionListener(this);
        this.frmB.btnLimpiar.addActionListener(this);
        this.frmB.btnCancelar.addActionListener(this);
    }

    public void iniciarVistaNuevo(String titulo) {
        frmB.setTitle(titulo);
        frmB.setLocationRelativeTo(null);
    }

    public void iniciarVistaMain() {
        frmA.setTitle("Gestión de Empleados");
        frmA.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // ===== NUEVO EMPLEADO =====
        if (e.getSource() == frmA.btnNuevo) {
            this.operacion = 1;
            limpiarVistaNuevo();
            frmB.txtCedula.setEnabled(true);  // PK habilitada para nuevo
            iniciarVistaNuevo("Registro de Empleados");
            frmB.setVisible(true);
        }

        // ===== EDITAR EMPLEADO =====
        if (e.getSource() == frmA.btnEditar) {

            if (frmA.tblEmpleados.getSelectedRowCount() == 1) {
                int fila = frmA.tblEmpleados.getSelectedRow();

                // columnas esperadas: 0=cedula,1=nombre,2=fechIng,3=direccion,
                // 4=telefono,5=email,6=nickName,7=pass
                frmB.txtCedula.setText(
                    frmA.tblEmpleados.getValueAt(fila, 0).toString()
                );
                frmB.txtCedula.setEnabled(false); // la PK no se cambia

                frmB.txtNombre.setText(
                    frmA.tblEmpleados.getValueAt(fila, 1).toString()
                );
                frmB.txtFechaIng.setText(
                    frmA.tblEmpleados.getValueAt(fila, 2).toString()
                );
                frmB.txtDireccion.setText(
                    frmA.tblEmpleados.getValueAt(fila, 3).toString()
                );
                frmB.txtTelefono.setText(
                    frmA.tblEmpleados.getValueAt(fila, 4).toString()
                );
                frmB.txtEmailEmple.setText(
                    frmA.tblEmpleados.getValueAt(fila, 5) == null
                        ? "" : frmA.tblEmpleados.getValueAt(fila, 5).toString()
                );
                frmB.txtNick.setText(
                    frmA.tblEmpleados.getValueAt(fila, 6).toString()
                );
                frmB.txtPass.setText(
                    frmA.tblEmpleados.getValueAt(fila, 7).toString()
                );

                this.operacion = 2;
                iniciarVistaNuevo("Actualizar Empleado");
                frmB.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
            }
        }

        // ===== ELIMINAR EMPLEADO =====
        if (e.getSource() == frmA.btnEliminar) {

            if (frmA.tblEmpleados.getSelectedRowCount() == 1) {
                int fila = frmA.tblEmpleados.getSelectedRow();
                int cedula = Integer.parseInt(
                    frmA.tblEmpleados.getValueAt(fila, 0).toString()
                );

                int resp = JOptionPane.showConfirmDialog(
                    frmA,
                    "¿Desea eliminar el empleado con cédula " + cedula + "?"
                );

                if (resp == JOptionPane.YES_OPTION) {
                    if (empModel.eliminarEmpleado(cedula)) {
                        JOptionPane.showMessageDialog(frmA, "Empleado eliminado");
                    } else {
                        JOptionPane.showMessageDialog(
                            frmA,
                            "Error al eliminar el registro"
                        );
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
            }
        }

        // ===== GUARDAR (NUEVO / EDITAR) =====
        if (e.getSource() == frmB.btnGuardar) {

            // formar el objeto Empleado desde la vista
            empleado.setCedula(
                Integer.parseInt(frmB.txtCedula.getText())
            );
            empleado.setNombre(frmB.txtNombre.getText());

            // formato esperado: yyyy-MM-dd
            empleado.setFechIng(
                Date.valueOf(frmB.txtFechaIng.getText())
            );

            empleado.setDireccion(frmB.txtDireccion.getText());
            empleado.setTelefono(frmB.txtTelefono.getText());
            empleado.setEmail(
                frmB.txtEmailEmple.getText().isEmpty()
                    ? null : frmB.txtEmailEmple.getText()
            );
            empleado.setNickName(frmB.txtNick.getText());
            empleado.setPass(frmB.txtPass.getText());

            if (operacion == 1) { // insertar
                if (empModel.insertarEmpleado(empleado)) {
                    JOptionPane.showMessageDialog(frmB, "Empleado registrado");
                    limpiarVistaNuevo();
                } else {
                    JOptionPane.showMessageDialog(frmB, "Error al guardar");
                }

            } else { // actualizar
                if (empModel.modificarEmpleado(empleado)) {
                    JOptionPane.showMessageDialog(frmB, "Empleado modificado");
                    limpiarVistaNuevo();
                    frmB.dispose();
                } else {
                    JOptionPane.showMessageDialog(frmB, "Error al editar");
                }
            }
        }

        // ===== LIMPIAR / CANCELAR FORMULARIO =====
        if (e.getSource() == frmB.btnLimpiar) {
            limpiarVistaNuevo();
        }

        if (e.getSource() == frmB.btnCancelar) {
            frmB.dispose();
        }
    }

    // ===== BÚSQUEDA EN TIEMPO REAL =====
    @Override
    public void keyReleased(KeyEvent ke) {
        modelT = empModel.filtrarEmpleados(frmA.txtBuscar.getText());
        frmA.tblEmpleados.setModel(modelT);
        frmA.txtRegs.setText(String.valueOf(modelT.getRowCount()));
    }

    public void limpiarVistaNuevo() {
        frmB.txtCedula.setText(null);
        frmB.txtNombre.setText(null);
        frmB.txtFechaIng.setText(null);
        frmB.txtDireccion.setText(null);
        frmB.txtTelefono.setText(null);
        frmB.txtEmailEmple.setText(null);
        frmB.txtNick.setText(null);
        frmB.txtPass.setText(null);
        frmB.txtCedula.setEnabled(true);
        frmB.txtCedula.requestFocus();
    }

    // ===== REFRESCAR TABLA AL ACTIVAR VENTANA =====
    @Override
    public void windowActivated(WindowEvent we) {
        modelT = empModel.mostrarEmpleados();
        frmA.tblEmpleados.setModel(modelT);
        frmA.txtRegs.setText(String.valueOf(modelT.getRowCount()));
    }

    
    @Override public void keyTyped(KeyEvent e)  {
        
    
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
