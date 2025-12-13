/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.pojo;

import java.sql.Timestamp;

/**
 *
 * @author josea
 */
import java.sql.Timestamp;

public class DetalleReserva {

    private int idDetalle;
    private int numReserva;
    private String vehiculo;
    private Timestamp fechaInicio;
    private int cantDias;
    private double descuento;
    private double subTotal;
    private double precioPorDia;

    public DetalleReserva() { }

    public DetalleReserva(int idDetalle, int numReserva, String vehiculo,
                          Timestamp fechaInicio, int cantDias,
                          double descuento, double subTotal, double precioPorDia) {
        this.idDetalle = idDetalle;
        this.numReserva = numReserva;
        this.vehiculo = vehiculo;
        this.fechaInicio = fechaInicio;
        this.cantDias = cantDias;
        this.descuento = descuento;
        this.subTotal = subTotal;
        this.precioPorDia = precioPorDia;
    }

    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public int getNumReserva() { return numReserva; }
    public void setNumReserva(int numReserva) { this.numReserva = numReserva; }

    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String vehiculo) { this.vehiculo = vehiculo; }

    public Timestamp getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Timestamp fechaInicio) { this.fechaInicio = fechaInicio; }

    public int getCantDias() { return cantDias; }
    public void setCantDias(int cantDias) { this.cantDias = cantDias; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }

    public double getPrecioPorDia() { return precioPorDia; }
    public void setPrecioPorDia(double precioPorDia) { this.precioPorDia = precioPorDia; }
}

