/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.archivosbinarios;

import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class Persona implements Serializable{
    private String nombre;
    private String apellido;
    private int edad;
    private String pais;
    Persona(String nombre, String apellido,int edad, String pais){
        setNombre(nombre);
        setApellido(apellido);
        setEdad(edad);
        setPais(pais);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
}
