/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class PDF {
      private String version;
      private File archivo;
    
     public PDF(File archivo){
        this.archivo = archivo;
    }
    public String getVersion(){ 
          try {
              RandomAccessFile newarchivo = new RandomAccessFile(archivo,"r");
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
}
