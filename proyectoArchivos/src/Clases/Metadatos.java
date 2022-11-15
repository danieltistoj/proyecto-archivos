/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import com.mycompany.proyecto.Pdf;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 *
 * @author Usuario
 */
public class Metadatos {
    public void escribir(String ruta, Pdf pdf){
                try {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream( ruta ));
             file.writeObject(pdf);
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
            Pdf pdf = (Pdf) file.readObject();
            //se cierra archivo
            file.close();
            //Se utilizan metodos de la clase asi como variables guardados en el objeto
            System.out.println("Nombre: "+pdf.getTitulo());
        } catch (ClassNotFoundException ex) {
             System.out.println(ex);
        } catch (IOException ex) {
             System.out.println(ex);
       }
    }
}
