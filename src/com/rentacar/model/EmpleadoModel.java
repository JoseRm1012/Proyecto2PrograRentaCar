/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.model;

import static com.rentacar.model.DataBase.conectar;
import com.rentacar.pojo.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmpleadoModel {

    Connection bd;

    
    //Insertar Empleado 
    
    public boolean insertarEmpleado(Empleado emp) {

        bd = conectar();
        String sql = "CALL sp_insertar_empleado(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {

            pst.setInt(1, emp.getCedula());
            pst.setString(2, emp.getNombre());
            pst.setDate(3, emp.getFechIng());
            pst.setString(4, emp.getDireccion());
            pst.setString(5, emp.getTelefono());
            pst.setString(6, emp.getEmail());
            pst.setString(7, emp.getNickName());
            pst.setString(8, emp.getPass());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar empleado: " + e.getMessage());
            return false;

        } finally {
            DataBase.cerrarConexion();
        }
    }

   
    // Eliminar
   
    public boolean eliminarEmpleado(int cedula) {

        bd = conectar();
        String sql = "CALL sp_eliminar_empleado(?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {

            pst.setInt(1, cedula);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(
                "Error al eliminar empleado: "
                + cedula + " -> " + e.getMessage()
            );
            return false;

        } finally {
            DataBase.cerrarConexion();
        }
    }

   //Mostar empleados
    
    public DefaultTableModel mostrarEmpleados() {

        bd = conectar();
        String[] titulos = {
            "Cédula", "Nombre", "Fecha Ingreso",
            "Dirección", "Teléfono", "Email",
            "NickName", "Password"
        };

        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String sql = "CALL sp_listar_empleados()";

        try {
            PreparedStatement pst = conectar().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String[] registro = new String[8];

                registro[0] = rs.getString("cedula");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("fechIng");
                registro[3] = rs.getString("direccion");
                registro[4] = rs.getString("telefono");
                registro[5] = rs.getString("email");
                registro[6] = rs.getString("nickName");
                registro[7] = rs.getString("pass");

                modelo.addRow(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error al mostrar empleados: " + e.getMessage()
            );

        } finally {
            DataBase.cerrarConexion();
        }

        return modelo;
    }

   //Filtrar empleado
    public DefaultTableModel filtrarEmpleados(String txtBusca) {

        bd = conectar();
        String[] titulos = {
            "Cédula", "Nombre", "Fecha Ingreso",
            "Dirección", "Teléfono", "Email",
            "NickName", "Password"
        };

        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String sql = "CALL sp_filtrar_empleados(?)";

        try {
            PreparedStatement pst = conectar().prepareStatement(sql);
            pst.setString(1, txtBusca.trim());

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String[] registro = new String[8];

                registro[0] = rs.getString("cedula");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("fechIng");
                registro[3] = rs.getString("direccion");
                registro[4] = rs.getString("telefono");
                registro[5] = rs.getString("email");
                registro[6] = rs.getString("nickName");
                registro[7] = rs.getString("pass");

                modelo.addRow(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error al filtrar empleados: " + e.getMessage()
            );

        } finally {
            DataBase.cerrarConexion();
        }

        return modelo;
    }

   //Modificar empleado
    public boolean modificarEmpleado(Empleado emp) {

        bd = conectar();
        String sql = "CALL sp_modificar_empleado(?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {

            pst.setInt(1, emp.getCedula());
            pst.setString(2, emp.getNombre());
            pst.setDate(3, emp.getFechIng());
            pst.setString(4, emp.getDireccion());
            pst.setString(5, emp.getTelefono());
            pst.setString(6, emp.getEmail());
            pst.setString(7, emp.getNickName());
            pst.setString(8, emp.getPass());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(
                "Error al actualizar empleado: "
                + emp.getCedula() + " -> " + e.getMessage()
            );
            return false;

        } finally {
            DataBase.cerrarConexion();
        }
    }
}
