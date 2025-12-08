package com.rentacar.model;

import static com.rentacar.model.DataBase.conectar;
import com.rentacar.pojo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClienteModel {

    Connection bd;

    // INSERTAR
    public boolean insertarCliente(Cliente cli) {

        bd = conectar();
        String sql = "INSERT INTO cliente (cedula, nombre, fechNac, direccion, telefono, email) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {
            pst.setInt(1, cli.getCedula());
            pst.setString(2, cli.getNombre());
            pst.setDate(3, cli.getFechNac());
            pst.setString(4, cli.getDireccion());
            pst.setString(5, cli.getTelefono());
            pst.setString(6, cli.getEmail());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        } finally {
            DataBase.cerrarConexion();
        }
    }

    // ELIMINAR
    public boolean eliminarCliente(int cedula) {

        bd = conectar();
        String sql = "DELETE FROM cliente WHERE cedula = ?";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {
            pst.setInt(1, cedula);

            int filasBorradas = pst.executeUpdate();
            return filasBorradas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + cedula + " -> " + e.getMessage());
            return false;
        } finally {
            DataBase.cerrarConexion();
        }
    }

    // MOSTRAR TODOS
    public DefaultTableModel mostrarClientes() {
        bd = conectar();
        String[] titulos = {"Cédula", "Nombre", "Fecha Nac.", "Dirección", "Teléfono", "Email"};

        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String sql = "SELECT * FROM cliente";

        try {
            ResultSet rs = DataBase.ejecutarConsulta(sql);
            if (rs != null) {
                while (rs.next()) {
                    String[] registro = new String[6];
                    registro[0] = rs.getString("cedula");
                    registro[1] = rs.getString("nombre");
                    registro[2] = rs.getString("fechNac");
                    registro[3] = rs.getString("direccion");
                    registro[4] = rs.getString("telefono");
                    registro[5] = rs.getString("email");

                    modelo.addRow(registro);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar clientes: " + e.getMessage());
        } finally {
            DataBase.cerrarConexion();
        }

        return modelo;
    }

    // FILTRAR (por cédula o nombre)
    public DefaultTableModel filtrarClientes(String txtBusca) {
        bd = conectar();
        String[] titulos = {"Cédula", "Nombre", "Fecha Nac.", "Dirección", "Teléfono", "Email"};

        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String texto = txtBusca.trim();
        String sql = "SELECT * FROM cliente "
                + "WHERE cedula LIKE '%" + texto + "%' "
                + "OR nombre LIKE '%" + texto + "%'";

        try {
            ResultSet rs = DataBase.ejecutarConsulta(sql);
            if (rs != null) {
                while (rs.next()) {
                    String[] registro = new String[6];
                    registro[0] = rs.getString("cedula");
                    registro[1] = rs.getString("nombre");
                    registro[2] = rs.getString("fechNac");
                    registro[3] = rs.getString("direccion");
                    registro[4] = rs.getString("telefono");
                    registro[5] = rs.getString("email");

                    modelo.addRow(registro);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al filtrar clientes: " + e.getMessage());
        } finally {
            DataBase.cerrarConexion();
        }

        return modelo;
    }

    // MODIFICAR
    public boolean modificarCliente(Cliente cli) {

        bd = conectar();
        String sql = "UPDATE cliente SET "
                + "nombre = ?, "
                + "fechNac = ?, "
                + "direccion = ?, "
                + "telefono = ?, "
                + "email = ? "
                + "WHERE cedula = ?";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {
            pst.setString(1, cli.getNombre());
            pst.setDate(2, cli.getFechNac());
            pst.setString(3, cli.getDireccion());
            pst.setString(4, cli.getTelefono());
            pst.setString(5, cli.getEmail());
            pst.setInt(6, cli.getCedula());

            int filasEditadas = pst.executeUpdate();
            return filasEditadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + cli.getCedula() + " -> " + e.getMessage());
            return false;
        } finally {
            DataBase.cerrarConexion();
        }
    }
}
