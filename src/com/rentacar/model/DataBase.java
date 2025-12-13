/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;     


public class DataBase {
    
    private static final String URL = "jdbc:mysql://localhost:3306/Rentacar-db";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private static Connection conn = null;

    // Método para establecer la conexión
    public static Connection conectar() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USUARIO, CLAVE);
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conn;
    }

    // Método para ejecutar consultas de tipo SELECT
    public static ResultSet ejecutarConsulta(String sql) {
        try {
            Connection con = conectar();
            Statement stmt = con.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar consulta: " + e.getMessage());
            return null;
        }
    }

    // Método para ejecutar sentencias de actualización (INSERT, UPDATE, DELETE)
    public static int ejecutarActualizacion(String sql) {
        try {
            Connection con = conectar();
            Statement stmt = con.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar actualización: " + e.getMessage());
            return 0;
        }
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

}