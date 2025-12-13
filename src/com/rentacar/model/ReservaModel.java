/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.model;

import static com.rentacar.model.DataBase.conectar;
import com.rentacar.pojo.Reserva;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author QXC
 */
public class ReservaModel {
    public boolean insertarReserva(Reserva r) {

        String sql = "CALL sp_insertar_reserva(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {

            pst.setInt(1, r.getNumReservas());
            pst.setTimestamp(2, r.getFecha());
            pst.setInt(3, r.getEmpleado());
            pst.setInt(4, r.getCliente());
            pst.setInt(5, r.getTemporada());
            pst.setDouble(6, r.getDescuento());
            pst.setDouble(7, r.getTotal());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error insertarReserva: " + e.getMessage());
            return false;

        } finally {
            DataBase.cerrarConexion();
        }
    }

    public boolean eliminarReserva(int numReservas) {

        String sql = "CALL sp_eliminar_reserva(?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {

            pst.setInt(1, numReservas);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error eliminarReserva: " + e.getMessage());
            return false;

        } finally {
            DataBase.cerrarConexion();
        }
    }

    public DefaultTableModel listarReservas() {

        String[] titulos = {"N°", "Fecha", "Empleado", "Cliente", "Temporada", "Descuento", "Total"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String sql = "CALL sp_listar_reservas()";

        try (PreparedStatement pst = conectar().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt("numReservas");
                fila[1] = rs.getString("fecha");
                fila[2] = rs.getInt("empleado");
                fila[3] = rs.getInt("cliente");
                fila[4] = rs.getInt("temporada");
                fila[5] = rs.getDouble("descuento");
                fila[6] = rs.getDouble("total");
                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            System.err.println("Error listarReservas: " + e.getMessage());

        } finally {
            DataBase.cerrarConexion();
        }

        return modelo;
    }
}
