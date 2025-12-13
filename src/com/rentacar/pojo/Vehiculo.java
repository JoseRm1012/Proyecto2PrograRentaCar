/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.pojo;

/**
 *
 * @author jonat
 */
public class Vehiculo {
    private String placa;
    private String marca;
    private String vin;
    private String modelo;
    private String tipo;
    private Double precio; 

    public Vehiculo() {
        this.placa = "";
        this.marca = "";
        this.vin = "";
        this.modelo = "";
        this.tipo = "";
        this.precio = 0.0;
    }
    
    public Vehiculo(String placa, String marca, String vin, String modelo, String tipo, Double precio) {
        this.placa = placa;
        this.marca = marca;
        this.vin = vin;
        this.modelo = modelo;
        this.tipo = tipo;
        this.precio = precio;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    
}
