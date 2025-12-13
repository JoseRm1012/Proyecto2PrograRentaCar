/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rentacar.pojo;


import java.sql.Date;

public class Empleado {

    private int cedula;
    private String nombre;
    private Date fechIng;
    private String direccion;
    private String telefono;
    private String email;
    private String nickName;
    private String pass;

    public Empleado() {
        this.cedula = 0;
        this.nombre = "";
        this.fechIng = null;
        this.direccion = "";
        this.telefono = "";
        this.email = "";
        this.nickName = "";
        this.pass = "";
    }

    public Empleado(int cedula, String nombre, Date fechIng,
                    String direccion, String telefono,
                    String email, String nickName, String pass) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.fechIng = fechIng;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.nickName = nickName;
        this.pass = pass;
    }

    // GETTERS Y SETTERS

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechIng() {
        return fechIng;
    }

    public void setFechIng(Date fechIng) {
        this.fechIng = fechIng;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}