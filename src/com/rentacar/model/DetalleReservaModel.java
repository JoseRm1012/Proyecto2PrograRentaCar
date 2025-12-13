/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.model;
import static com.rentacar.model.DataBase.conectar;
import com.rentacar.pojo.DetalleReserva;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author QXC
 */
public class DetalleReservaModel {
     public boolean insertarDetalle(DetalleReserva d) {

        String sql = "CALL sp_insertar_detalle_reserva(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {

            pst.setInt(1, d.getNumReserva());
            pst.setString(2, d.getVehiculo());
            pst.setTimestamp(3, d.getFechaInicio());
            pst.setInt(4, d.getCantDias());
            pst.setDouble(5, d.getDescuento());
            pst.setDouble(6, d.getSubTotal());
            pst.setDouble(7, d.getPrecioPorDia());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error insertarDetalle: " + e.getMessage());
            return false;

        } finally {
            DataBase.cerrarConexion();
        }
    }
}
