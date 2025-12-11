/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.model;

import static com.rentacar.model.DataBase.conectar;
import com.rentacar.pojo.Temporada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author josea
 */
public class TemporadaModel {
    Connection bd;

    // ============================================================
    // INSERTAR TEMPORADA
    // ============================================================
    public boolean insertarTemporada(Temporada t) {

        bd = conectar();
        String sql = "CALL sp_insertar_temporada(?, ?, ?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {

            pst.setInt(1, t.getIdTemporada());
            pst.setString(2, t.getNombre());
            pst.setInt(3, t.getPorcDesc());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar temporada: " + e.getMessage());
            return false;

        } finally {
            DataBase.cerrarConexion();
        }
    }

    // ============================================================
    // ELIMINAR TEMPORADA
    // ============================================================
    public boolean eliminarTemporada(int idTemporada) {

        bd = conectar();
        String sql = "CALL sp_eliminar_temporada(?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {

            pst.setInt(1, idTemporada);

            int filas = pst.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar temporada: "
                    + idTemporada + " -> " + e.getMessage());
            return false;

        } finally {
            DataBase.cerrarConexion();
        }
    }

    // ============================================================
    // MOSTRAR TODAS LAS TEMPORADAS
    // ============================================================
    public DefaultTableModel mostrarTemporadas() {

        bd = conectar();

        String[] titulos = {"ID", "Nombre", "Descuento (%)"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String sql = "CALL sp_listar_temporadas()";

        try {
            PreparedStatement pst = conectar().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String[] registro = new String[3];
                registro[0] = rs.getString("idTemporada");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("porcDesc");

                modelo.addRow(registro);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al mostrar temporadas: " + e.getMessage());

        } finally {
            DataBase.cerrarConexion();
        }

        return modelo;
    }

    // ============================================================
    // FILTRAR TEMPORADAS
    // ============================================================
    public DefaultTableModel filtrarTemporadas(String txtBusca) {

        bd = conectar();

        String[] titulos = {"ID", "Nombre", "Descuento (%)"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String sql = "CALL sp_filtrar_temporadas(?)";

        try {

            PreparedStatement pst = conectar().prepareStatement(sql);
            pst.setString(1, txtBusca.trim());

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String[] registro = new String[3];
                registro[0] = rs.getString("idTemporada");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("porcDesc");

                modelo.addRow(registro);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null,
                    "Error al filtrar temporadas: " + e.getMessage());

        } finally {
            DataBase.cerrarConexion();
        }

        return modelo;
    }

    // ============================================================
    // MODIFICAR TEMPORADA
    // ============================================================
    public boolean modificarTemporada(Temporada t) {

        bd = conectar();
        String sql = "CALL sp_modificar_temporada(?, ?, ?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {

            pst.setInt(1, t.getIdTemporada());
            pst.setString(2, t.getNombre());
            pst.setInt(3, t.getPorcDesc());

            int filas = pst.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {

            System.err.println("Error al modificar temporada: "
                    + t.getIdTemporada() + " -> " + e.getMessage());
            return false;

        } finally {
            DataBase.cerrarConexion();
        }
    }
    
}
