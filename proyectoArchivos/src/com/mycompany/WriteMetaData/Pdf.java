/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.WriteMetaData;

/**
 *
 * @author Peke
 */
public class Pdf {
    private String ruta; 
    private String TamArchivo;
    private String TamPaginas;
    private String NoPaginas;
    private String Titulo;
    private String Asunto;
    private String PClave;
    private String Tipo;
    private String Version;
    private String App;
    private String Imagenes;
    private String Fuentes;
    private Pdf siguiente;
    
    
    public Pdf(){
        this.ruta = null;
        this.TamArchivo = null;
        this.TamPaginas = null;
        this.NoPaginas = null;
        this.Titulo = null;
        this.Asunto = null;;
        this.PClave = null;
        this.Tipo = null;
        this.Version = null;
        this.App = null;
        this.Imagenes = null;
        this.Fuentes = null;
        this.siguiente = null;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTamArchivo() {
        return TamArchivo;
    }

    public void setTamArchivo(String TamArchivo) {
        this.TamArchivo = TamArchivo;
    }

    public String getTamPaginas() {
        return TamPaginas;
    }

    public void setTamPaginas(String TamPaginas) {
        this.TamPaginas = TamPaginas;
    }

    public String getNoPaginas() {
        return NoPaginas;
    }

    public void setNoPaginas(String NoPaginas) {
        this.NoPaginas = NoPaginas;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getAsunto() {
        return Asunto;
    }

    public void setAsunto(String Asunto) {
        this.Asunto = Asunto;
    }

    public String getPClave() {
        return PClave;
    }

    public void setPClave(String PClave) {
        this.PClave = PClave;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getApp() {
        return App;
    }

    public void setApp(String App) {
        this.App = App;
    }

    public String getImagenes() {
        return Imagenes;
    }

    public void setImagenes(String Imagenes) {
        this.Imagenes = Imagenes;
    }

    public String getFuentes() {
        return Fuentes;
    }

    public void setFuentes(String Fuentes) {
        this.Fuentes = Fuentes;
    }
    
    public Pdf getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Pdf siguiente) {
        this.siguiente = siguiente;
    }   
    
    
}
