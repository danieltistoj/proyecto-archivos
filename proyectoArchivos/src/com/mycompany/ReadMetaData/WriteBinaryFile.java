/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ReadMetaData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriteBinaryFile {
   
    private ArrayList<PDF> pdfs;

    public WriteBinaryFile(ArrayList<PDF> pdfs) {
        this.pdfs =pdfs;
    }
    
    public void writeFile(String name){
        try {
            String documentName = name + ".bin";
            RandomAccessFile file=new RandomAccessFile(documentName,"rw");
            ArrayList<String> nombres=new ArrayList<String>();
            ArrayList<Integer> offsets=new ArrayList<Integer>();
            Integer offsetValue=4;
            file.writeBytes("ADES");
            for(int i=0;i<this.pdfs.size();i++){
                nombres.add(pdfs.get(i).getName());
                offsets.add(offsetValue);
                
                file.write(pdfs.get(i).getName().length());
                file.writeBytes(pdfs.get(i).getName());
                offsetValue+=1+pdfs.get(i).getName().length();
                
                
                file.write(pdfs.get(i).getVersion().length());
                file.writeBytes(pdfs.get(i).getVersion());
                offsetValue+=1+pdfs.get(i).getVersion().length();
                
                file.writeLong(pdfs.get(i).getSize());
                offsetValue+=8;
                
                String[] keys={"Title","Subject","Keywords","Author","CreationDate","ModDate","Creator","Producer"};
                
                //Itera el Map de los metadatos si no existe una key escribe 0
                for(int j=0; j<keys.length;j++){
                    if(pdfs.get(i).getMetadata().get(keys[j])!=null){
                        int len=pdfs.get(i).getMetadata().get(keys[j]).length();
                        String value=pdfs.get(i).getMetadata().get(keys[j]);
                        file.write(len);
                        file.writeBytes(value);
                        offsetValue+=1+len;
                    }
                    else{
                        byte b=0;
                        file.write(b);
                        offsetValue+=1;
                    }
                }
                
                file.writeInt(pdfs.get(i).getPages());
                file.writeInt(pdfs.get(i).getImages());
                offsetValue+=4+4;
                
                //Escribe el tamaño de la lista de fuentes
                file.write(pdfs.get(i).getFonts().size());
                offsetValue+=4;
                //Escribe todas las fuentes
                for(int j=0;j<pdfs.get(i).getFonts().size();j++){
                    int len=pdfs.get(i).getFonts().get(j).length();
                    String value=pdfs.get(i).getFonts().get(j);
                    file.write(len);
                    file.writeBytes(value);
                    offsetValue+=1+len;
                }
                
                
                //Marca de un nuevo registro
               if(i==this.pdfs.size()-1){
                   file.writeBytes("EOF");
               }
               else{
                   file.writeBytes("NXT");
               }
            }
            
           for(int i=0;i<nombres.size();i++){
                System.out.println(nombres.get(i));
                System.out.println(offsets.get(i));
                file.write(nombres.get(i).length());
                file.writeBytes(nombres.get(i));
                file.writeInt(offsets.get(i));
            }
            
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WriteBinaryFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WriteBinaryFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
