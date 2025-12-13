package com.rentacar.controller;

import com.rentacar.model.DetalleReservaModel;
import com.rentacar.model.ReservaModel;
import com.rentacar.pojo.DetalleReserva;
import com.rentacar.pojo.Reserva;
import com.rentacar.view.DlgNuevaReservaView;
import com.rentacar.view.DlgReservasView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReservaController implements ActionListener {

    // VISTAS
    private final DlgReservasView dlgLista;        // LISTA
    private final DlgNuevaReservaView dlgNueva;    // NUEVA

    // MODELOS / POJOS
    private final Reserva reserva;
    private final ReservaModel resModel;
    private final DetalleReservaModel detModel;

    private DefaultTableModel modeloDetalle;

    // IDs seleccionados
    private int idClienteSeleccionado = 0;
    private int idEmpleadoSeleccionado = 0;
    private int idTemporadaSeleccionada = 0;

    public ReservaController(Reserva reserva,
                             ReservaModel resModel,
                             DlgReservasView dlgLista,
                             DlgNuevaReservaView dlgNueva) {

        this.reserva = reserva;
        this.resModel = resModel;
        this.detModel = new DetalleReservaModel();

        this.dlgLista = dlgLista;
        this.dlgNueva = dlgNueva;

        // LISTA
        this.dlgLista.btnNuevo.addActionListener(this);

        // NUEVA
        this.dlgNueva.btnAgregarAuto.addActionListener(this);
        this.dlgNueva.btnEliminarAuto.addActionListener(this);
        this.dlgNueva.btnReservar.addActionListener(this);
        this.dlgNueva.btnBuscarCliente.addActionListener(this);
        this.dlgNueva.btnBuscarEmpleado.addActionListener(this);

        iniciarTablaDetalle();
        limpiarFormulario();
    }

    private void iniciarTablaDetalle() {
        String[] cols = {"Vehiculo", "FechaInicio", "CantDias", "PrecioDia", "Desc", "SubTotal"};
        modeloDetalle = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dlgNueva.tblDetalleReserva.setModel(modeloDetalle);
    }

    private void limpiarFormulario() {
        dlgNueva.txtNumReservas.setText("");
        dlgNueva.btnCalendarioFecha.setDate(null);
        dlgNueva.txtCliente.setText("");
        dlgNueva.txtEmpleado.setText("");
        dlgNueva.txtTemporada.setText("");

        dlgNueva.txtSubtotal.setText("0");
        dlgNueva.txtDescuento.setText("0");
        dlgNueva.txtTotal.setText("0");

        idClienteSeleccionado = 0;
        idEmpleadoSeleccionado = 0;
        idTemporadaSeleccionada = 0;

        modeloDetalle.setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // ===== LISTA =====
        if (e.getSource() == dlgLista.btnNuevo) {
            limpiarFormulario();
            dlgNueva.setLocationRelativeTo(dlgLista);
            dlgNueva.setVisible(true);
            return;
        }

        // ===== NUEVA =====
        if (e.getSource() == dlgNueva.btnAgregarAuto) {
            agregarAutoPorDialogo();
            recalcularTotales();
            return;
        }

        if (e.getSource() == dlgNueva.btnEliminarAuto) {
            eliminarAutoSeleccionado();
            recalcularTotales();
            return;
        }

        if (e.getSource() == dlgNueva.btnBuscarCliente) {
            String ced = JOptionPane.showInputDialog(dlgNueva, "Cédula del cliente:");
            if (ced != null && !ced.trim().isEmpty()) {
                idClienteSeleccionado = Integer.parseInt(ced.trim());
                dlgNueva.txtCliente.setText("Cliente " + idClienteSeleccionado);
            }
            return;
        }

        if (e.getSource() == dlgNueva.btnBuscarEmpleado) {
            String ced = JOptionPane.showInputDialog(dlgNueva, "Cédula del empleado:");
            if (ced != null && !ced.trim().isEmpty()) {
                idEmpleadoSeleccionado = Integer.parseInt(ced.trim());
                dlgNueva.txtEmpleado.setText("Empleado " + idEmpleadoSeleccionado);
            }
            return;
        }

        if (e.getSource() == dlgNueva.btnReservar) {
            reservar();
        }
    }

    private void agregarAutoPorDialogo() {
        String placa = JOptionPane.showInputDialog(dlgNueva, "Placa del vehículo:");
        if (placa == null || placa.trim().isEmpty()) return;

        String diasStr = JOptionPane.showInputDialog(dlgNueva, "Cantidad de días:");
        if (diasStr == null || diasStr.trim().isEmpty()) return;

        String precioStr = JOptionPane.showInputDialog(dlgNueva, "Precio por día:");
        if (precioStr == null || precioStr.trim().isEmpty()) return;

        String descStr = JOptionPane.showInputDialog(dlgNueva, "Descuento del detalle (0 si no aplica):");
        if (descStr == null || descStr.trim().isEmpty()) descStr = "0";

        String fechaInicioStr = JOptionPane.showInputDialog(
                dlgNueva,
                "Fecha inicio (yyyy-MM-dd HH:mm:ss):",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        if (fechaInicioStr == null || fechaInicioStr.trim().isEmpty()) return;

        int cantDias = Integer.parseInt(diasStr.trim());
        double precioDia = Double.parseDouble(precioStr.trim());
        double descuento = Double.parseDouble(descStr.trim());

        double subTotal = (cantDias * precioDia) - descuento;

        modeloDetalle.addRow(new Object[]{
            placa.trim(),
            fechaInicioStr.trim(),
            cantDias,
            precioDia,
            descuento,
            subTotal
        });
    }

    private void eliminarAutoSeleccionado() {
        int fila = dlgNueva.tblDetalleReserva.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(dlgNueva, "Seleccione un auto de la tabla para eliminar.");
            return;
        }
        modeloDetalle.removeRow(fila);
    }

    private void recalcularTotales() {
        double subtotal = 0;
        double desc = 0;

        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            desc += Double.parseDouble(modeloDetalle.getValueAt(i, 4).toString());
            subtotal += Double.parseDouble(modeloDetalle.getValueAt(i, 5).toString());
        }

        dlgNueva.txtSubtotal.setText(String.valueOf(subtotal));
        dlgNueva.txtDescuento.setText(String.valueOf(desc));
        dlgNueva.txtTotal.setText(String.valueOf(subtotal));
    }

    private void reservar() {

        // Validaciones mínimas
        if (dlgNueva.txtNumReservas.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(dlgNueva, "Ingrese el N° de Reserva.");
            return;
        }

        if (idClienteSeleccionado == 0 || idEmpleadoSeleccionado == 0) {
            JOptionPane.showMessageDialog(dlgNueva, "Seleccione Cliente y Empleado.");
            return;
        }

        if (modeloDetalle.getRowCount() == 0) {
            JOptionPane.showMessageDialog(dlgNueva, "Agregue al menos 1 vehículo al detalle.");
            return;
        }

        int numReserva = Integer.parseInt(dlgNueva.txtNumReservas.getText().trim());

        // fecha: si está vacía, usa la actual
// Fecha (JDateChooser): si no selecciona, usa la actual
Timestamp fechaReserva;

java.util.Date fechaSeleccionada = dlgNueva.btnCalendarioFecha.getDate(); // JDateChooser

if (fechaSeleccionada == null) {
    fechaReserva = Timestamp.valueOf(LocalDateTime.now());
} else {
    fechaReserva = new Timestamp(fechaSeleccionada.getTime());
}




        double desc = Double.parseDouble(dlgNueva.txtDescuento.getText().trim());
        double total = Double.parseDouble(dlgNueva.txtTotal.getText().trim());

        // Si todavía no seleccionas temporada con lupa, queda en 0 o lo tomas por input
        if (idTemporadaSeleccionada == 0 && !dlgNueva.txtTemporada.getText().trim().isEmpty()) {
            // si el txtTemporada es un ID, puedes parsearlo:
            try {
                idTemporadaSeleccionada = Integer.parseInt(dlgNueva.txtTemporada.getText().trim());
            } catch (Exception ex) {
                // si es nombre, lo dejas 0 por ahora
            }
        }

        // 1) Insertar RESERVA (cabecera)
        reserva.setNumReservas(numReserva);
        reserva.setFecha(fechaReserva);
        reserva.setEmpleado(idEmpleadoSeleccionado);
        reserva.setCliente(idClienteSeleccionado);
        reserva.setTemporada(idTemporadaSeleccionada);
        reserva.setDescuento(desc);
        reserva.setTotal(total);

        if (!resModel.insertarReserva(reserva)) {
            JOptionPane.showMessageDialog(dlgNueva, "Error al guardar la reserva (cabecera).");
            return;
        }

        // 2) Insertar DETALLES
        boolean okDetalles = true;

        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {

            String placa = modeloDetalle.getValueAt(i, 0).toString();
            String fechaInicioStr = modeloDetalle.getValueAt(i, 1).toString();
            int cantDias = Integer.parseInt(modeloDetalle.getValueAt(i, 2).toString());
            double precioDia = Double.parseDouble(modeloDetalle.getValueAt(i, 3).toString());
            double descuentoDet = Double.parseDouble(modeloDetalle.getValueAt(i, 4).toString());
            double subTotalDet = Double.parseDouble(modeloDetalle.getValueAt(i, 5).toString());

            Timestamp fechaInicio;
            try {
                LocalDateTime ldt = LocalDateTime.parse(
                        fechaInicioStr,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                );
                fechaInicio = Timestamp.valueOf(ldt);
            } catch (Exception ex) {
                // fallback
                fechaInicio = Timestamp.valueOf(LocalDateTime.now());
            }

            DetalleReserva det = new DetalleReserva();
            det.setNumReserva(numReserva);
            det.setVehiculo(placa);
            det.setFechaInicio(fechaInicio);
            det.setCantDias(cantDias);
            det.setPrecioPorDia(precioDia);
            det.setDescuento(descuentoDet);
            det.setSubTotal(subTotalDet);

            if (!detModel.insertarDetalle(det)) {
                okDetalles = false;
                break;
            }
        }

        if (okDetalles) {
            JOptionPane.showMessageDialog(dlgNueva, "Reserva registrada correctamente.");
            limpiarFormulario();
            dlgNueva.dispose();
        } else {
            JOptionPane.showMessageDialog(dlgNueva, "Reserva guardada, pero hubo error en los detalles.");
        }
    }
}