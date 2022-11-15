/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author Usuario
 */
public class Metodos {
    private String ruta = "src/main/java/com/mycompany/archivos";
  
    public void crearArchivo(String archivo){
        File fl = new File(archivo);
        PrintWriter pw;
        try {
            pw = new PrintWriter(fl);
            pw.close();
            System.out.println("Archivo creado");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
    }
    
  
        }
    public void escribir(String ruta,Persona persona){
        try {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream( ruta ));
             file.writeObject(persona);
             file.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public void leer(String ruta){
        try {
            //Stream para leer archivo
            ObjectInputStream file = new ObjectInputStream(new FileInputStream(ruta ));
            //Se lee el objeto de archivo y este debe convertirse al tipo de clase que corresponde
            Persona persona = (Persona) file.readObject();
            //se cierra archivo
            file.close();
            //Se utilizan metodos de la clase asi como variables guardados en el objeto
            System.out.println("Nombre: "+persona.getNombre());
        } catch (ClassNotFoundException ex) {
             System.out.println(ex);
        } catch (IOException ex) {
             System.out.println(ex);
       }
    }
    }
    

