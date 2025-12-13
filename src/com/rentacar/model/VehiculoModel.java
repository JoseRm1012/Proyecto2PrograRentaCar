/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.model;

import static com.rentacar.model.DataBase.conectar;
import com.rentacar.pojo.Vehiculo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jonat
 */
public class VehiculoModel {

    Connection bd;

    public boolean insertarVehiculo(Vehiculo veh) {

        bd = conectar();
       String sql = "CALL sp_insertar_vehiculo(?, ?, ?, ?, ?, ?)";


        try (PreparedStatement pst = conectar().prepareStatement(sql)) {
            pst.setString(1, veh.getPlaca());
            pst.setString(2, veh.getMarca());
            pst.setString(3, veh.getVin());
          
            pst.setString(4, veh.getModelo());
            pst.setString(5, veh.getTipo());
            pst.setDouble(6, veh.getPrecio());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar vehiculo: ");
            return false;
        } finally {
            // cerrar la conexion
            DataBase.cerrarConexion();
        }
    }

    
    public boolean eliminarVehiculo(String placa) {
        
       bd = conectar();
    String sql = "CALL sp_eliminar_vehiculo(?)";

    try (PreparedStatement pst = conectar().prepareStatement(sql)) {
        pst.setString(1, placa);

        int filasBorradas = pst.executeUpdate();
        return filasBorradas > 0;

    } catch (SQLException e) {
        System.err.println("Error al eliminar vehiculo: " + placa + " -> " + e.getMessage());
        return false;

    } finally {
        DataBase.cerrarConexion();
    }
    }

     public DefaultTableModel mostrarVehiculos() {
         bd = conectar();
    String[] titulos = {"Placa", "Marca", "VIN", "Modelo", "Tipo", "Precio"};
    DefaultTableModel modelo = new DefaultTableModel(null, titulos);

    String sql = "CALL sp_listar_vehiculos()";

    try {
        PreparedStatement pst = conectar().prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            String[] registro = new String[6];
            registro[0] = rs.getString("placa");
            registro[1] = rs.getString("marca");
            registro[2] = rs.getString("vin");
            registro[3] = rs.getString("modelo");
            registro[4] = rs.getString("tipo");
            registro[5] = rs.getString("precio");

            modelo.addRow(registro);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al mostrar vehículos: " + e.getMessage());
    } finally {
        DataBase.cerrarConexion();
    }

    return modelo;
    }
    
    public DefaultTableModel filtrarVehiculos(String txtBusca) {
        bd = conectar();
        String[] titulos = {"Placa", "Marca", "VIN", "Modelo", "Tipo", "Precio"};
        
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

         String sql = "CALL sp_filtrar_vehiculos(?)";

         try {
        PreparedStatement pst = conectar().prepareStatement(sql);
        pst.setString(1, txtBusca);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            String[] registro = new String[6];
            registro[0] = rs.getString("placa");
            registro[1] = rs.getString("marca");
            registro[2] = rs.getString("vin");
            registro[3] = rs.getString("modelo");
            registro[4] = rs.getString("tipo");
            registro[5] = rs.getString("precio");

            modelo.addRow(registro);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al filtrar: " + e.getMessage());
    } finally {
        DataBase.cerrarConexion();
    }

    return modelo;
    }

    public boolean modificarVehiculo(Vehiculo veh) {

         bd = conectar();
    String sql = "CALL sp_modificar_vehiculo(?, ?, ?, ?, ?, ?)";

    try (PreparedStatement pst = conectar().prepareStatement(sql)) {

        // El ORDEN debe coincidir con el SP:
        // (pPlaca, pMarca, pVIN, pModelo, pTipo, pPrecio)
        pst.setString(1, veh.getPlaca());
        pst.setString(2, veh.getMarca());
        pst.setString(3, veh.getVin());
        pst.setString(4, veh.getModelo());
        pst.setString(5, veh.getTipo());
        pst.setDouble(6, veh.getPrecio());

        int filasEditadas = pst.executeUpdate();
        return filasEditadas > 0;

    } catch (SQLException e) {
        System.err.println("Error al actualizar vehículo: " 
                + veh.getPlaca() + " -> " + e.getMessage());
        return false;

    } finally {
        DataBase.cerrarConexion();
    }
    }
}
