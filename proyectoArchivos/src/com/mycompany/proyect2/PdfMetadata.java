/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyect2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Adrian Quixivix, Jose Chocoy, Emilio medina , Andre Gordillo
 * Clase para extraer metadatos demanera binaria de un fichero PDF
 */

public class PdfMetadata {
    
   private String path;
    private long xref;
    private PDF pdf;
    

    
    

    /**
     * Crea la classe PDFMetadata recibiendo como parametros:
     * @param path  String de el path abosulo del fichero PDF
     */
    public PdfMetadata(String path) {
        this.path = path;
        File file=new File(this.path);
        
        //offset xref Table
         this.xref = getXref(-1);

        //obj
        int objRoot = getRootObj();
        int objInfo = getInfoObj();

        //ofsset Objs
        long offsetobjInfo = getOffsetObj(xref, objInfo);
        long offsetObjRoot = getOffsetObj(xref, objRoot);
        int objPages = getObjPages(offsetObjRoot);
        long offsetObjPages = getOffsetObj(xref, objPages);


        //Info-Pages
        ArrayList<Integer> arr = getKidsPages(offsetObjPages);
        ArrayList<String> fonts = new ArrayList<String>();

        for (int i = 0; i < arr.size(); i++) {
            long offset = getOffsetObj(xref, arr.get(i));
            int objF = getObjFont(offset);
            offset = getOffsetObj(xref, objF);

            String font = getFontName(offset);
            if (!fonts.contains(font)) {
                fonts.add(font);
            }
        }

        //DATOS -OUTPUT
        String verisonPDf=getVersion();
        long size=getFileSize();
        int countPages = getCountPages(offsetObjPages);
        
        //Info-Images
        int Images = 0;
        for(int i=0;i<arr.size();i++){
            long offset = getOffsetObj(xref, arr.get(i));
            Images = contImages(offset);

        }
        
        this.pdf = new PDF();
        this.pdf.setName(file.getName());
        this.pdf.setVersion(verisonPDf);
        this.pdf.setSize(size);
        this.pdf.setPages(countPages);
        this.pdf.setFonts(fonts);
        this.pdf.setMetadata(this.ReadData(offsetobjInfo));
        this.pdf.setImages(Images);
        
        
        //this.namePDF =file.getName();
       // this.versionPDF=verisonPDf;
        //this.sizePDF=size;
       // this.pagesPDF=countPages;
        //this.fontsPDF=fonts;
        //this.metadataPDF=
        //this.imagesPDF=Images;
      
    }
    
    public void ShowInfo(){
        System.out.println("Nombre: " + pdf.getName());
        System.out.println("Version: " + pdf.getVersion());
        System.out.println("Size: " + pdf.getSize());
        System.out.println("Meta Datos: ");
        Set<String> keys = (Set<String>) pdf.getMetadata().keySet();
        for(String key:keys){
            String valor = pdf.getMetadata().get(key);
            System.out.println(key+" = "+valor);
        }
        //System.out.println(pdf.getMetadata());
        System.out.println("Paginas: "+ pdf.getPages());
        System.out.println("Fuentes: ");
        for(int i=0;i<pdf.getFonts().size();i++){
            System.out.println(pdf.getFonts().get(i));
        }
        System.out.println("Cantidad de Imagenes: " + pdf.getImages());
        
        System.out.println();
        System.out.println();
    }

    public PDF getPdf() {
        return pdf;
    }
       
    /**
     * Metodo para Obtener el offset de la tabla Xref de manera
     * recursiva, con lectura binaria hacia atras
     * @param offset un offset inicial -1 para la primera recusion
     * @return el offset de la tabla xref
     */
    private long getXref(long offset) {
        String offsetString = "";
        long offsetInicial = offset;
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            if (offset == -1) {
                offset = file.length() - 5;
                file.seek(offset);

            } else {
                offset = offset - 7;
                file.seek(offset);
            }
            // COMPRUEBA %%OEF

            byte EOF[] = new byte[5];
            file.read(EOF);
            if ("%%EOF".equals(new String(EOF))) {

                offset = file.getFilePointer() - 8;
                file.seek(offset);
                boolean OA = false;

                // Busca BYTE 0A
                while (!OA) {
                    byte byteB[] = new byte[1];
                    file.read(byteB);
                    if ("a".equals(Integer.toHexString(byteB[0]))) {
                        OA = true;
                    } else {
                        offset -= 1;
                    }

                    file.seek(offset);
                }
                file.seek(offset + 1);
                // Recorre hasta BYTE 0D
                boolean OD = false;
                while (!OD) {
                    byte B[] = new byte[1];
                    file.read(B);
                    if ("d".equals(Integer.toHexString(B[0]))) {
                        OD = true;
                    } else {
                        offsetString += new String(B);

                    }
                }

                offset = Long.parseLong(offsetString);
            } else {
                return offset + 7;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getXref(offset);
    }

    /**
     * Metodo para obtner la etiqueta /Info de un pdf y el objeto 
     * que lo contiene en xref table
     * @return indice del objeto con la etiqueta /Info
     */
    private int getInfoObj() {
        String obj = "";
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");

            long offset = file.length();
            file.seek(offset);
            boolean info = false;
            while (!info) {
                byte byteB[] = new byte[1];
                file.read(byteB);
                if ("/".equals(new String(byteB))) {
                    byte infoString[] = new byte[4];
                    file.read(infoString);
                    if ("Info".equals(new String(infoString))) {
                        file.read(byteB);
                        boolean isRead = false;
                        while (!isRead) {
                            file.read(byteB);
                            if ("20".equals(Integer.toHexString(byteB[0]))) {
                                isRead = true;
                            } else {
                                obj += new String(byteB);
                            }
                        }
                        info = true;
                    } else {
                        offset -= 4;
                    }

                } else {
                    offset -= 1;
                }
                file.seek(offset);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Integer.valueOf(obj);
    }

    /**
     * Metodo para obtner la etiquet /Root de un pdf y el objeto 
     * que lo contiene en xref table
     * @return indice del objeto con la etiquet /Root
     */
    
    private int getRootObj() {
        String obj = "";
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            long offset = file.length();
            file.seek(offset);
            boolean root = false;
            while (!root) {
                byte byteB[] = new byte[1];
                file.read(byteB);
                if ("/".equals(new String(byteB))) {
                    byte rootString[] = new byte[4];
                    file.read(rootString);
                    if ("Root".equals(new String(rootString))) {
                        file.read(byteB);
                        boolean isRead = false;
                        while (!isRead) {
                            file.read(byteB);
                            if ("20".equals(Integer.toHexString(byteB[0]))) {
                                isRead = true;
                            } else {
                                obj += new String(byteB);
                            }
                        }
                        root = true;
                    } else {
                        offset -= 4;
                    }

                } else {
                    offset -= 1;
                }
                file.seek(offset);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Integer.valueOf(obj);
    }

    /**
     * Metodo para obtner el objeto con la fuente de un pagina pdf
     * @param offset de la pagina a la que se hace referencia
     * @return indice del objeto con la fuente
     */
    
    private int getObjFont(long offset) {
        RandomAccessFile file;
        String objFont = "";
        try {
            file = new RandomAccessFile(path, "rw");
            file.seek(offset);
            boolean font = false;
            while (!font) {
                byte byteB[] = new byte[1];
                file.read(byteB);
                if ("/".equals(new String(byteB))) {
                    byte fontLabel[] = new byte[6];
                    file.read(fontLabel);
                    if ("Font<<".equals(new String(fontLabel))) {
                        font = true;
                        file.read();
                        file.read();
                        file.read();
                        file.read();
                        boolean isRead = false;
                        while (!isRead) {
                            file.read(byteB);
                            if ("20".equals(Integer.toHexString(byteB[0]))) {
                                isRead = true;
                            } else {
                                objFont += new String(byteB);
                            }
                        }
                    } else {
                        file.seek(file.getFilePointer() - 5);
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Integer.valueOf(objFont);

    }

    /**
     * Metodo para obtner el objeto con la estructura de las paginas del pdf
     * @param offset del objeto /Root
     * @return indice del objeto /Pages
     */
    private int getObjPages(long offset) {
        RandomAccessFile file;
        String objString = "";
        try {
            file = new RandomAccessFile(path, "rw");
            file.seek(offset);
            boolean obj = false;

            while (!obj) {
                byte byteB[] = new byte[1];
                file.read(byteB);
                if ("/".equals(new String(byteB))) {
                    byte pagesString[] = new byte[5];
                    file.read(pagesString);

                    if ("Pages".equals(new String(pagesString))) {
                        obj = true;
                        file.read();
                        boolean isRead = false;
                        while (!isRead) {
                            file.read(byteB);
                            if ("20".equals(Integer.toHexString(byteB[0]))) {
                                isRead = true;
                            } else {
                                objString += new String(byteB);
                            }
                        }
                    } else {
                        file.seek(file.getFilePointer() - 4);
                    }

                }

            }


        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Integer.valueOf(objString);
    }

    /**
     * Metodo para obtener el offset de un objeto de la tabla xref
     * @param xref offset de la tabla xref
     * @param obj objeto del que se desea el offset
     * @return  offset del Objeto buscado
     */
    private long getOffsetObj(long xref, int obj) {
        byte offsetObj[] = new byte[10];
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            xref += 6;
            file.seek(xref);
            int position = obj - startRange(xref);
            file.read();
            file.read();
            long offset = position * 20 + beforeObj(xref);
            file.seek(offset);
            file.read(offsetObj);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Long.valueOf(new String(offsetObj));
    }

    /**
     * Metodo para verificar que un objeto se encuentra en el rango
     * de la tabla xref
     * @param offset de la tabla xref table
     * @param obj objeto al que se busca en la xref table
     * @return booleano si esta en rango
     */
    private boolean inRange(long offset, int obj) {
        boolean inRange = false;
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            file.seek(offset);
            boolean startRange = false;
            String startRangeString = "";
            while (!startRange) {

                byte byteB[] = new byte[1];
                file.read(byteB);
                if ("20".equals(Integer.toHexString(byteB[0]))) {
                    startRange = true;
                } else {
                    startRangeString += new String(byteB);
                }

            }

            boolean endRange = false;
            String endRangeString = "";
            while (!endRange) {

                byte byteB[] = new byte[1];
                file.read(byteB);
                if ("d".equals(Integer.toHexString(byteB[0]))) {
                    endRange = true;
                } else {
                    endRangeString += new String(byteB);
                }

            }

            if (obj >= Integer.valueOf(startRangeString) && obj <= Integer.valueOf(endRangeString)) {
                inRange = true;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inRange;
    }

    /**
     * Metodo para obtener el primer rango de la tabla Xref
     * @param offset de la tabla xref
     * @return valor inicial de la tabal Xref
     */
    
    private int startRange(long offset) {
        String startRangeString = "";
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            file.seek(offset);
            boolean startRange = false;
            while (!startRange) {

                byte byteB[] = new byte[1];
                file.read(byteB);
                if ("20".equals(Integer.toHexString(byteB[0]))) {
                    startRange = true;
                } else {
                    startRangeString += new String(byteB);
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Integer.valueOf(startRangeString);
    }

    
    /**
     * Metodo para obtener el offset justo antes de los objetos de la Xref Table
     * @param offset de la tabla Xref
     * @return  offset al inicio de los objetos
     */
    private long beforeObj(long offset) {
        boolean OA = false;
        try {
            RandomAccessFile file = new RandomAccessFile(this.path, "rw");
            file.seek(offset);
            while (!OA) {
                byte byteB[] = new byte[1];
                file.read(byteB);
                {
                    if ("a".equals(Integer.toHexString(byteB[0]))) {
                        OA = true;
                        offset = file.getFilePointer();
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return offset;
    }

    /**
     * Metodo para obtener todos los objetos con la etiqueta /Page dentro de un PDF
     * @param offset del objeto Con la estructura de las paginas
     * @return ArrayList con los indices de los objetos /Pages
     */
    private ArrayList<Integer> getKidsPages(long offset) {
        ArrayList<Integer> kidsList = new ArrayList<Integer>();
        String objKid = "";
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            file.seek(offset);
            boolean kids = false;
            while (!kids) {
                byte byteB[] = new byte[1];
                file.read(byteB);
                if ("/".equals(new String(byteB))) {
                    byte kidsLabel[] = new byte[5];
                    file.read(kidsLabel);
                    if ("Kids[".equals(new String(kidsLabel))) {
                        kids = true;
                        boolean isRead = false;
                        int i = 0;
                        while (!isRead) {
                            file.read(byteB);
                            if ("]".equals(new String(byteB))) {
                                isRead = true;
                            } else if ("20".equals(Integer.toHexString(byteB[0]))) {
                                i++;
                            }

                            if (i == 2) {
                                int x = Integer.parseInt(objKid.replace(" ", ""));
                                kidsList.add(x);
                                file.seek(file.getFilePointer() + 3);
                                i = 0;
                                objKid = "";
                            } else {
                                objKid += new String(byteB);

                            }
                        }
                    } else {
                        file.seek(file.getFilePointer() - 4);
                    }

                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kidsList;
    }

    /**
     * Metodo para obtener el peso de un archivo PDF en Bytes
     * @return Bytes del PDF
     */
    private long getFileSize() {
        long sizeF;
        File file = new File(path);
        sizeF = file.length();
        return sizeF;
    }

    /**
     * Metodo para obtener la version del PDF
     * @return Version del PDF
     */
    private String getVersion() {
        String obj = "";
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            long offset = 0;
            file.seek(offset);
            byte byteB[] = new byte[3];
            String line = file.readLine();
            obj = line.substring(5, 8);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    /**
     * Metodo para obtener el nombre de la fuente utilizada en una pagina 
     * especifica del PDF
     * @param offset del objeto en el que se encuentra la etiqueta /Font
     * @return Nombre de la fuente utilizada.
     */
    private String getFontName(long offset) {
        String nameFont = "";
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            file.seek(offset);

            boolean font = false;
            while (!font) {
                byte byteB[] = new byte[1];
                file.read(byteB);
                if ("/".equals(new String(byteB))) {
                    byte baseFont[] = new byte[9];
                    file.read(baseFont);
                    if ("BaseFont/".equals(new String(baseFont))) {
                        font = true;
                        boolean isRead = false;
                        while (!isRead) {
                            file.read(byteB);
                            if ("/".equals(new String(byteB))) {
                                isRead = true;
                            } else {
                                nameFont += new String(byteB);
                            }
                        }
                    } else {
                        file.seek(file.getFilePointer() - 8);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return nameFont;
    }

    /**
     * Metodo para obtener los metadatos del PDF
     * @param offset de la etiqueta /Info
     */
    private HashMap ReadData(long offset) { // Leer infrmacion del PDF
        HashMap<String,String> metaData = new HashMap<>();
        try {
            
            RandomAccessFile file = new RandomAccessFile(path, "r");

            boolean endobj = false;

            file.seek(offset);

            while (!endobj) {
                String etiqueta = "";
                String data = "";
                byte info[] = new byte[1];
                file.read(info);

                if ("/".equals(new String(info))) {
                    boolean fin = false;

                    while (!fin) {

                        byte info_e[] = new byte[1];
                        file.read(info_e);

                        if ("(".equals(new String(info_e))) {
                           // System.out.println("Etiqueta: " + etiqueta);
                            //etiqueta += "|";
                            
                            boolean fin2 = false;

                            while (!fin2) {
                                byte info_d[] = new byte[1];
                                file.read(info_d);

                                if (")".equals(new String(info_d))) {
                                    //System.out.println("Data: " + data);
                                    //data += "|";
                                    fin2 = true;

                                } else {
                                    data += new String(info_d);
                                }

                            }
                            fin = true;

                        } else {
                            etiqueta += new String(info_e);

                        }

                    }
                    
                    metaData.put(etiqueta, data);
                }

                if (">".equals(new String(info))) {

                    endobj = true;

                }

            }
            
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return metaData;
    }

    
    
    
    /**
     * Metodo para obtener el numero de Paginas de un PDF
     * @param offset del objeto con la estructura de las paginas
     * @return contador de paginas en el PDF
     */
    private int getCountPages(long offset) {
        RandomAccessFile file;
        String countPages = "";
        try {
            file = new RandomAccessFile(path, "rw");
            file.seek(offset);
            boolean count = false;
            while (!count) {
                byte byteB[] = new byte[1];
                file.read(byteB);
                if ("/".equals(new String(byteB))) {
                    byte countString[] = new byte[5];
                    file.read(countString);
                    if ("Count".equals(new String(countString))) {
                        count = true;
                        file.read();
                        boolean isRead = false;
                        while (!isRead) {
                            file.read(byteB);
                            if ("/".equals(new String(byteB))) {
                                isRead = true;
                            } else {
                                countPages += new String(byteB);
                            }
                        }
                    } else {
                        file.seek(file.getFilePointer() - 4);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Integer.valueOf(countPages);

    }

    /**
     * Metodo para obtener el numero de Imagenes de un PDF
     * @param offset de la pagina a buscar imagenes
     * @return contador de imagenes en la pagina
     */
    private int contImages(long offset){
        int cont = 0;
        try {
            RandomAccessFile file = new RandomAccessFile(path,"r");
            
            file.seek(offset);
            boolean endobj = false;

            while(!endobj){
                byte[] images = new byte[1];
                file.read(images);
                if("/".equals(new String(images))){
                    
                    boolean xobject = false;
                    while(!xobject){
                        byte[] xobj = new byte[1];
                        file.read(xobj);
                        if("X".equals(new String(xobj))){
                            
                            boolean aux = false;
                            while(!aux){
                                byte[] imagesCont = new byte[1];
                                file.read(imagesCont);
                                if("/".equals(new String(imagesCont))) cont++;
                                if(">".equals(new String(imagesCont))){
                                    aux = true;
                                    
                                }

                            }
                        }
                        if("[".equals(new String(xobj))){
                            
                            xobject = true;
                            endobj = true;
                            break;
                        }
                    }
                }
            }
            
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cont;
       
    }





}
