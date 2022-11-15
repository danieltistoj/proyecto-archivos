/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.html.HTMLEditorKit.Parser;

/**
 *
 * @author Usuario
 */
public class PDF {
      private String version;
      private String ruta;
      private File archivo;
    
     public PDF(File archivo,String ruta){
        this.archivo = archivo;
        this.ruta = ruta;
        
     
    }
    public String getVersion(){ 
        
          try {
              RandomAccessFile newarchivo = new RandomAccessFile(ruta,"r");
       
              this.version=newarchivo.readLine();
              newarchivo.close();
             
              return this.version;
          } catch (FileNotFoundException ex) {
              Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
          }catch (IOException ex) {
            Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
        }
          return this.version;
    }
    public String SizePdf(){
        int size = (int) this.archivo.length();
        String sizeString = String.valueOf(size) + " bytes ";
        return sizeString;
    }
    public String[] getAutor() {
        String autor =  "no hay referencia";
        String titulo = "no hay referencia";
        String[] datos  = new String[3];
        datos[0]=autor;
        datos[1]=titulo;
          try {
              FileReader fileReader = new FileReader(this.ruta);
              BufferedReader bf = new BufferedReader(fileReader);
              while(bf.readLine()!=null){
                  String cadena = bf.readLine();
                  if(cadena!=null){
                     if(cadena.contains("/Author")){
                        
                         autor = obtenerAutor(cadena);
                         datos[0]= autor;
                  }
                     if(cadena.contains("/Title")){
                         titulo = cadena;
                         datos[1]=cadena;
                     }
                }
 
              }
              //String cadena = "<</Author(JOSE DANIEL TISTOJ REYES)";

          } catch (FileNotFoundException ex) {
              Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
          } catch (IOException ex) {
              Logger.getLogger(PDF.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          return datos;
    }
    
    
    public String obtenerAutor(String cadena){
        String autor = "";
        for(int i = 0;i<cadena.length();i++){
            if(i >=10){
                if(cadena.charAt(i) != ')'){
                    autor+=cadena.charAt(i);
                }else{
                    break;
                }
           
            }
        }
        return autor;
    }
}
