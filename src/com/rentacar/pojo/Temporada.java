/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.pojo;

/**
 *
 * @author josea
 */
public class Temporada {
    
    private int idTemporada;
    private String nombre;
    private int porcDesc;

    public Temporada() {
        this.idTemporada = 0;
        this.nombre = "";
        this.porcDesc = 0;
    }

    public Temporada(int idTemporada, String nombre, int porcDesc) {
        this.idTemporada = idTemporada;
        this.nombre = nombre;
        this.porcDesc = porcDesc;
    }

    public int getIdTemporada() {
        return idTemporada;
    }

    public void setIdTemporada(int idTemporada) {
        this.idTemporada = idTemporada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPorcDesc() {
        return porcDesc;
    }

    public void setPorcDesc(int porcDesc) {
        this.porcDesc = porcDesc;
    }
}
