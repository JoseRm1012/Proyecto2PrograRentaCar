/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.rentacar.pojo.Reserva;
import com.rentacar.model.ReservaModel;
import com.rentacar.view.DlgReservasView;
import com.rentacar.view.DlgReservasView;
import com.rentacar.view.DlgNuevaReservaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ReservaController implements ActionListener {

    private ReservaModel reserva;
    private ReservaModel reservaModel;
    
    private DlgReservasView frmA;
    private DlgNuevaReservaView frmB;
    private int operacion; // 1 = insertar, 2 = actualizar

    public ReservaController(ReservaModel reserva, ReservaModel reservaModel, DlgReservasView frmA, DlgNuevaReservaView frmB) {
        this.reserva = reserva;
        this.reservaModel = reservaModel;
        this.frmA = frmA;
        this.frmB = frmB;

        // Acción de los botones
        this.frmA.btnNuevo.addActionListener(this);
        this.frmA.btnEliminar.addActionListener(this);
        this.frmB.btnGuardar.addActionListener(this);
        this.frmB.btnCancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Botón para crear una nueva reserva
        if (e.getSource() == frmA.btnNuevo) {
            this.operacion = 1;
            frmB.setVisible(true);
        }

        // Botón para eliminar una reserva
        if (e.getSource() == frmA.btnEliminar) {
            if (frmA.tblReservas.getSelectedRowCount() == 1) {
                int fila = frmA.tblReservas.getSelectedRow();
                int numReserva = Integer.parseInt(frmA.tblReservas.getValueAt(fila, 0).toString());

                int resp = JOptionPane.showConfirmDialog(frmA, "¿Desea eliminar la reserva N° " + numReserva + "?");
                if (resp == 0) {
                    if (reservaModel.eliminarReserva(numReserva)) {
                        JOptionPane.showMessageDialog(frmA, "Reserva eliminada.");
                    } else {
                        JOptionPane.showMessageDialog(frmA, "Error al eliminar reserva.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frmA, "Debe seleccionar un registro.");
            }
        }

        // Botón para guardar una nueva reserva
        if (e.getSource() == frmB.btnGuardar) {
            reserva.setNumReserva(Integer.parseInt(frmB.txtNumReserva.getText()));
            reserva.setFecha(frmB.dateFecha.getDate());
            reserva.setEmpleado(Integer.parseInt(frmB.txtEmpleado.getText()));
            reserva.setCliente(Integer.parseInt(frmB.txtCliente.getText()));
            reserva.setTemporada(Integer.parseInt(frmB.txtTemporada.getText()));
            reserva.setTotal(Double.parseDouble(frmB.txtTotal.getText()));

            if (operacion == 1) { // Insertar
                if (reservaModel.insertarReserva(reserva)) {
                    JOptionPane.showMessageDialog(frmB, "Reserva registrada.");
                    limpiarVistaNuevo();
                } else {
                    JOptionPane.showMessageDialog(frmB, "Error al guardar.");
                }
            }
        }

        // Botón para cancelar
        if (e.getSource() == frmB.btnCancelar) {
            frmB.dispose();
        }
    }

    // Limpiar los campos de la vista
    private void limpiarVistaNuevo() {
        frmB.txtNumReserva.setText("");
        frmB.txtEmpleado.setText("");
        frmB.txtCliente.setText("");
        frmB.txtTemporada.setText("");
        frmB.txtTotal.setText("");
        frmB.setVisible(false);
    }
}