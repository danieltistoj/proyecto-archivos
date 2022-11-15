/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyecto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Guardadatos {
    
    Lista x=new Lista();
    public void escribit(Lista x)
    {
        try {
            RandomAccessFile j=new RandomAccessFile("ListaPdf.bin","rw");
            j.writeByte('F');
            j.writeByte('F');
            j.writeByte('F');
            j.writeByte('0');
            j.writeByte('0');
            j.writeByte('0');
            String noPagina;
            String Titulo;
            int largo;
            String tamArchivo;
            String tamPaginas;
            String asunto;
            String Pclave;
            String autor;
            String version;
            String app;
            String imagenes;
            String fuentes;
            
            
            //escribimos el nombre
            x.setsiguiente(x.getinicio());
            while(x.auxiliar!=null)
            {   
                if(x.getnombre()!=null)
                {
                    
                     Titulo=x.getnombre();
                     
                     largo=Titulo.length();
                    
                      j.writeByte('<');
                      j.writeByte((byte)largo);
                      j.writeByte('>');
                      for (int i = 0; i < Titulo.length(); i++) {
                        j.writeByte(Titulo.charAt(i));
                    }
                      
                }
                else
                {
                    j.writeByte('<');
                    j.writeByte('>');
                    
                }
                //escribimos el tamaño del archivo
                if(x.gettamArchivo()!=null)
                {
                   
                     tamArchivo=x.gettamArchivo();
                     largo=tamArchivo.length();
                     
                      j.writeByte('<');
                      j.writeByte((byte)largo);
                      j.writeByte('>');
                      for (int i = 0; i < tamArchivo.length(); i++) {
                        j.writeByte(tamArchivo.charAt(i));
                    }
                }
                else
                {
                    
                    j.writeByte('<');
                    j.writeByte('>');
                }
                //escribimos el numero de paginas
                 if(x.getnoPaginas()!=null)
                {
                    
                     noPagina=x.getnoPaginas();
                    
                     largo=noPagina.length();
                    
                      j.writeByte('<');
                      j.writeByte((byte)largo);
                      j.writeByte('>');
                      for (int i = 0; i < noPagina.length(); i++) {
                        j.writeByte(noPagina.charAt(i));
                    }
                      
                }
                else
                {
                    j.writeByte('<');
                    j.writeByte('>');
                    
                }
                noPagina=x.getnoPaginas();
                // escribimos el tamaño de las paginas
                if(x.gettamPaginas()!=null)
                {
                   
                     tamPaginas=x.gettamPaginas();
                     largo=tamPaginas.length();
                     
                      j.writeByte('<');
                      j.writeByte((byte)largo );
                      j.writeByte('>');
                      for (int i = 0; i < tamPaginas.length(); i++) {
                        j.writeByte(tamPaginas.charAt(i) );
                    }
                }
                else
                {
                    
                    j.writeByte('<');
                    j.writeByte('>');
                }
                // escribimos el asunto
                 if(x.getasunto()!=null)
                {
                   
                     asunto=x.getasunto();
                     largo=asunto.length();
                     
                      j.writeByte('<');
                      j.writeByte((byte)largo );
                      j.writeByte('>');
                      for (int i = 0; i < asunto.length(); i++) {
                        j.writeByte(asunto.charAt(i) );
                    }
                }
                else
                {
                    
                    j.writeByte('<');
                    j.writeByte('>');
                }
                 // escribimos las palabras clave
                 if(x.getpClave()!=null)
                {
                   
                     Pclave=x.getpClave();
                     largo=Pclave.length();
                     
                      j.writeByte('<');
                      j.writeByte((byte)largo );
                      j.writeByte('>');
                      for (int i = 0; i < Pclave.length(); i++) {
                        j.writeByte(Pclave.charAt(i) );
                    }
                }
                else
                {
                    
                    j.writeByte('<');
                    j.writeByte('>');
                }
                 // escribimos el autor(tipo)
                  if(x.gettipo()!=null)
                {
                   
                     autor=x.gettipo();
                     largo=autor.length();
                     
                      j.writeByte('<');
                      j.writeByte((byte)largo );
                      j.writeByte('>');
                      for (int i = 0; i < autor.length(); i++) {
                        j.writeByte(autor.charAt(i) );
                    }
                }
                else
                {
                    
                    j.writeByte('<');
                    j.writeByte('>');
                }
                  //escribimos la version del pdf
                    if(x.getversion()!=null)
                {
                   
                     version=x.getversion();
                     largo=version.length();
                     
                      j.writeByte('<');
                      j.writeByte((byte)largo );
                      j.writeByte('>');
                      for (int i = 0; i < version.length(); i++) {
                        j.writeByte(version.charAt(i) );
                    }
                }
                else
                {
                    
                    j.writeByte('<');
                    j.writeByte('>');
                }
                  //escribimos la app con la que fue creado 
                      if(x.getapp()!=null)
                {
                   
                     app=x.getapp();
                     largo=app.length();
                     
                      j.writeByte('<');
                      j.writeByte((byte)largo );
                      j.writeByte('>');
                      for (int i = 0; i < app.length(); i++) {
                        j.writeByte(app.charAt(i) );
                    }
                }
                else
                {
                    
                    j.writeByte('<');
                    j.writeByte('>');
                } 
                 // escribimos el numero de imagenes del pdf
                   if(x.getimagenes()!=null)
                {
                   
                     imagenes=x.getimagenes();
                     largo=imagenes.length();
                     
                      j.writeByte('<');
                      j.writeByte((byte)largo );
                      j.writeByte('>');
                      for (int i = 0; i < imagenes.length(); i++) {
                        j.writeByte(imagenes.charAt(i) );
                    }
                }
                else
                {
                    
                    j.writeByte('<');
                    j.writeByte('>');
                } 
                 // escribimos todas la fuentes del pdf
                    if(x.getfuentes()!=null)
                {
                   
                     fuentes=x.getfuentes();
                     largo=fuentes.length();
                     
                      j.writeByte('<');
                      j.writeByte((byte)largo );
                      j.writeByte('>');
                      for (int i = 0; i < fuentes.length(); i++) {
                        j.writeByte(fuentes.charAt(i) );
                    }
                }
                else
                {
                    
                    j.writeByte('<');
                    j.writeByte('>');
                } 
                 
                x.setsiguiente(x.getsiguinte());
            }
            j.close();
            
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Guardadatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Guardadatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
