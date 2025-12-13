/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.controller;

import com.rentacar.model.TemporadaModel;
import com.rentacar.pojo.Temporada;
import com.rentacar.view.DlgNuevaTemporadaView;
import com.rentacar.view.DlgTemporadaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author josea
 */

public class TemporadaController implements ActionListener, KeyListener, WindowListener {

    private Temporada temporada;
    private TemporadaModel tempModel;

    private DlgTemporadaView frmA;           // vista principal
    private DlgNuevaTemporadaView frmB;      // vista nuevo / editar
    private int operacion;                   // 1 = insertar, 2 = actualizar

    DefaultTableModel modelT;

    public TemporadaController(Temporada t,
                               TemporadaModel model,
                               DlgTemporadaView frmMain,
                               DlgNuevaTemporadaView frmNew) {

        this.temporada = t;
        this.tempModel = model;

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

        // botones del formulario
        this.frmB.btnGuardar.addActionListener(this);
        this.frmB.btnLimpiar.addActionListener(this);
        this.frmB.btnCancelar.addActionListener(this);
    }

    public void iniciarVistaNuevo(String titulo) {
        frmB.setTitle(titulo);
        frmB.setLocationRelativeTo(null);
    }

    public void iniciarVistaMain() {
        frmA.setTitle("Gestión de Temporadas");
        frmA.setLocationRelativeTo(null);
    }

    //Manejo de los botones 
    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        // ===== NUEVA TEMPORADA =====
        if (src == frmA.btnNuevo) {
            this.operacion = 1;
            limpiarVistaNuevo();
            frmB.txtIdTemporada.setEnabled(true);
            iniciarVistaNuevo("Registro de Temporadas");
            frmB.setVisible(true);
        }

        //Editar Temporada
        else if (src == frmA.btnEditar) {

            if (frmA.tblTemporadas.getSelectedRowCount() == 1) {
                int fila = frmA.tblTemporadas.getSelectedRow();

                // columnas: 0=idTemporada, 1=nombre, 2=porcDesc
                frmB.txtIdTemporada.setText(
                    frmA.tblTemporadas.getValueAt(fila, 0).toString()
                );
                frmB.txtIdTemporada.setEnabled(false);

                frmB.txtNombreTemporada.setText(
                    frmA.tblTemporadas.getValueAt(fila, 1).toString()
                );
                frmB.txtPorcDesc.setText(
                    frmA.tblTemporadas.getValueAt(fila, 2).toString()
                );

                this.operacion = 2;
                iniciarVistaNuevo("Actualizar Temporada");
                frmB.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro");
            }
        }

        // ===== ELIMINAR TEMPORADA =====
        else if (src == frmA.btnEliminar) {

            if (frmA.tblTemporadas.getSelectedRowCount() == 1) {
                int fila = frmA.tblTemporadas.getSelectedRow();
                int idTemp = Integer.parseInt(
                    frmA.tblTemporadas.getValueAt(fila, 0).toString()
                );

                int resp = JOptionPane.showConfirmDialog(
                    frmA,
                    "¿Desea eliminar la temporada con ID " + idTemp + "?"
                );

                if (resp == JOptionPane.YES_OPTION) {
                    if (tempModel.eliminarTemporada(idTemp)) {
                        JOptionPane.showMessageDialog(frmA, "Temporada eliminada");
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

        // ===== GUARDAR (NUEVA O EDITADA) =====
        else if (src == frmB.btnGuardar) {

            // construir objeto Temporada desde la vista
            temporada.setIdTemporada(
                Integer.parseInt(frmB.txtIdTemporada.getText())
            );
            temporada.setNombre(frmB.txtNombreTemporada.getText());
            temporada.setPorcDesc(
                Integer.parseInt(frmB.txtPorcDesc.getText())
            );

            if (operacion == 1) { // insertar

                if (tempModel.insertarTemporada(temporada)) {
                    JOptionPane.showMessageDialog(frmB, "Temporada registrada");
                    limpiarVistaNuevo();
                } else {
                    JOptionPane.showMessageDialog(frmB, "Error al guardar");
                }

            } else { // actualizar

                if (tempModel.modificarTemporada(temporada)) {
                    JOptionPane.showMessageDialog(frmB, "Temporada modificada");
                    limpiarVistaNuevo();
                    frmB.dispose();
                } else {
                    JOptionPane.showMessageDialog(frmB, "Error al editar");
                }
            }
        }

        // ===== LIMPIAR / CANCELAR =====
        else if (src == frmB.btnLimpiar) {
            limpiarVistaNuevo();

        } else if (src == frmB.btnCancelar) {
            frmB.dispose();
        }
    }

    // ============================================================
    // BÚSQUEDA EN TIEMPO REAL
    // ============================================================
    @Override
    public void keyReleased(KeyEvent ke) {

        modelT = tempModel.filtrarTemporadas(frmA.txtBuscar.getText());
        frmA.tblTemporadas.setModel(modelT);
        frmA.txtRegs.setText(String.valueOf(modelT.getRowCount()));
    }

    public void limpiarVistaNuevo() {
        frmB.txtIdTemporada.setText(null);
        frmB.txtNombreTemporada.setText(null);
        frmB.txtPorcDesc.setText(null);
        frmB.txtIdTemporada.setEnabled(true);
        frmB.txtIdTemporada.requestFocus();
    }

    // ============================================================
    // REFRESCAR TABLA AL ACTIVAR VENTANA
    // ============================================================
    @Override
    public void windowActivated(WindowEvent we) {

        modelT = tempModel.mostrarTemporadas();
        frmA.tblTemporadas.setModel(modelT);
        frmA.txtRegs.setText(String.valueOf(modelT.getRowCount()));
    }

    // Métodos vacíos obligatorios
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
