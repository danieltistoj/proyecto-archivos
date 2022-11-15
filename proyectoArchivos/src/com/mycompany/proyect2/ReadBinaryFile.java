/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyect2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ReadBinaryFile {
    private ArrayList<PDF> pdfList;
    
    public ReadBinaryFile(){
        this.pdfList = new ArrayList<PDF>();
    }

    public ArrayList<PDF> getPdfList() {
        return pdfList;
    }
    
    
    
    public void showInfo() {
        for (int i = 0; i < this.pdfList.size(); i++) {
            PDF pdf=pdfList.get(i);
            System.out.println("Nombre: " + pdf.getName());
            System.out.println("Version: " + pdf.getVersion());
            System.out.println("Size: " + pdf.getSize());
            System.out.println("Meta Datos: ");
            Set<String> keys = (Set<String>) pdf.getMetadata().keySet();
            for (String key : keys) {
                String valor = pdf.getMetadata().get(key);
                System.out.println(key + " = " + valor);
            }
            //System.out.println(pdf.getMetadata());
            System.out.println("Paginas: " + pdf.getPages());
            System.out.println("Fuentes: ");
            for (int j = 0; j < pdf.getFonts().size(); j++) {
                System.out.println(pdf.getFonts().get(j));
            }
            System.out.println("Cantidad de Imagenes: " + pdf.getImages());

            System.out.println();
            System.out.println();

        }
        

    }
    
    public void ReadFile(String documentName){
        
        
        
        try {
            
            RandomAccessFile file = new RandomAccessFile(documentName ,"r");
            
            int tamanio = file.read();
            
            //boolean endfile = false;ss
            file.seek(0);
            byte ades[] = new byte[4];
            file.read(ades);
            
          if("ADES".equals(new String(ades))){
              boolean fin = false;
              
              while(!fin){
              PDF data = new PDF();                
              HashMap<String,String> metadata = new HashMap<>();
              ArrayList<String> fonts = new ArrayList<String>();
              byte size = file.readByte();
              byte nombre[] = new byte[size];
              file.read(nombre);
              String name = new String(nombre);
             
              data.setName(name);
              
              size = file.readByte();
              byte ver[] = new byte[size];
              file.read(ver);
              String version = new String(ver);
             
              data.setVersion(version);
              
              long size2 = file.readLong();
              data.setSize(size2);
              size = file.readByte();
              
              if(size !=0){
                  byte titulo [] = new byte[size];
                  file.read(titulo);
                  String title = new String(titulo);
                 
                  metadata.put("Title", title);
              }
              
               size = file.readByte();
               
              
              if(size !=0){
                  byte subjet [] = new byte[size];
                  file.read(subjet);
                  String subj = new String(subjet);
                  
                  metadata.put("Subject", subj);
              }
              
              size = file.readByte();
              
              
              if(size !=0){
                  byte keywrods [] = new byte[size];
                  file.read(keywrods);
                  String keyW = new String(keywrods);
                  
                  metadata.put("Keywords", keyW);
              }
              
               size = file.readByte();
              
              
              if(size !=0){
                  byte author [] = new byte[size];
                  file.read(author);
                  String autor = new String(author);
                
                  metadata.put("Author", autor);
              }
              
              size = file.readByte();
               
              if(size !=0){
                  byte CreationDate [] = new byte[size];
                  file.read(CreationDate);
                  String date = new String(CreationDate);
                  
                  metadata.put("CreationDate", date); 
              }
              
              size = file.readByte();
               
              if(size !=0){
                  byte ModDate [] = new byte[size];
                  file.read(ModDate);
                  String mdate = new String(ModDate);
                
                  metadata.put("ModDate", mdate);
              }
              
              size = file.readByte();
               
              if(size !=0){
                  byte Creador [] = new byte[size];
                  file.read(Creador);
                  String Creator = new String(Creador);
                  
                  metadata.put("Creator", Creator);
              }
              
              size = file.readByte();
               
              if(size !=0){
                  byte Lproducer [] = new byte[size];
                  file.read(Lproducer);
                  String productor = new String(Lproducer);
                 
                  metadata.put("Producer", productor);
                  
              }
              
              
              int page = file.readInt();
              
              data.setPages(page);
              int images = file.readInt();
             
              data.setImages(images);
              byte items = file.readByte();
             
              
              for(int i=0;i<items;i++){
                  size = file.readByte();
                
                  byte fuente[] = new byte[size];
                  file.read(fuente);
                  String font = new String(fuente);
                  fonts.add(font);
              }
              
              byte marca[] = new byte[3];
              file.read(marca);
              String marc = new String(marca);
              
              if(marc.equals("EOF")){
                  fin = true;
              }
            
              
              data.setFonts(fonts);
              data.setMetadata(metadata);
             
              this.pdfList.add(data);
              }
              
          }
            
            
          file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadBinaryFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadBinaryFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
}
