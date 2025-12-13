/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.pojo;

import java.sql.Date;



public class Reserva {

    private int numReserva;
    private Date fecha;
    private int empleado;
    private int cliente;
    private int temporada;
    private double descuento;
    private double total;

    public Reserva() {
    }

    public Reserva(int numReserva, Date fecha, int empleado, int cliente,
                   int temporada, double descuento, double total) {
        this.numReserva = numReserva;
        this.fecha = fecha;
        this.empleado = empleado;
        this.cliente = cliente;
        this.temporada = temporada;
        this.descuento = descuento;
        this.total = total;
    }

    public int getNumReserva() {
        return numReserva;
    }

    public void setNumReserva(int numReserva) {
        this.numReserva = numReserva;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
