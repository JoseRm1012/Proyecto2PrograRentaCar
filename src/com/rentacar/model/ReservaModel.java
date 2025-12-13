/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.rentacar.model.DataBase;
import static com.rentacar.model.DataBase.conectar;
import com.rentacar.pojo.Reserva;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReservaModel {
    
    Connection bd;

    // Insertar nueva reserva
    public boolean insertarReserva(Reserva reserva) {
        bd = conectar();
        String sql = "CALL sp_insertar_reserva(?, ?, ?, ?, ?, ?)";  // Cambia el nombre del procedimiento si es necesario
        
        try (PreparedStatement pst = conectar().prepareStatement(sql)) {
            pst.setInt(1, reserva.getNumReserva());
            pst.setDate(2, (Date) reserva.getFecha());
            pst.setInt(3, reserva.getEmpleado());
            pst.setInt(4, reserva.getCliente());
            pst.setInt(5, reserva.getTemporada());
            pst.setDouble(6, reserva.getTotal());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar reserva: " + e.getMessage());
            return false;
        } finally {
            DataBase.cerrarConexion();
        }
    }

    // Mostrar todas las reservas
    public DefaultTableModel mostrarReservas() {
        bd = conectar();
        String[] titulos = {"N° Reserva", "Fecha", "Empleado", "Cliente", "Temporada", "Total"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String sql = "CALL sp_listar_reservas()";  // Cambia el nombre del procedimiento si es necesario

        try {
            ResultSet rs = DataBase.ejecutarConsulta(sql);
            while (rs.next()) {
                String[] registro = new String[6];
                registro[0] = rs.getString("numReserva");
                registro[1] = rs.getString("fecha");
                registro[2] = rs.getString("empleado");
                registro[3] = rs.getString("cliente");
                registro[4] = rs.getString("temporada");
                registro[5] = rs.getString("total");

                modelo.addRow(registro);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar reservas: " + e.getMessage());
        } finally {
            DataBase.cerrarConexion();
        }
        
        return modelo;
    }

    // Eliminar reserva
    public boolean eliminarReserva(int numReserva) {
        bd = conectar();
        String sql = "CALL sp_eliminar_reserva(?)";

        try (PreparedStatement pst = conectar().prepareStatement(sql)) {
            pst.setInt(1, numReserva);

            int filasBorradas = pst.executeUpdate();
            return filasBorradas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar reserva: " + numReserva + " -> " + e.getMessage());
            return false;
        } finally {
            DataBase.cerrarConexion();
        }
    }
}
