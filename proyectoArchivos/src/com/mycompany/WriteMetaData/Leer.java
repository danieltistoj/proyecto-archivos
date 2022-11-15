package com.mycompany.WriteMetaData;
/**
 *
 * @author Peke
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peke
 */

public class Leer {
    private File ruta;
    private int Startxref=0;
    private int root=0;
    private int catalogo=0;

    Lista nuevaa = new Lista();    
    
    
    Guardadatos f = new Guardadatos();     
    
    public Lista devolver(){        
        return nuevaa;        
    }    
    
    public void mostrar(){        
        nuevaa.listar();        
    } 
        
    Leer(File ruta){
        this.ruta = ruta;
    }
    
    private String getResources(String info){
        int referencia = 0;
        try {
            RandomAccessFile archivo = new RandomAccessFile(ruta,"r");
            String buscador="";
            String cadena = "";
            int aux =0;
            String[] partir = info.split("/");
            int a = info.split("/").length;                
            for(int j=1; j<a; j++){
                buscador="";
                for (int k = 0; k < 4; k++) {
                    buscador+=partir[j].charAt(k);
                }   
                if("Reso".equals(buscador)){                               
                    String[] auxPartir = partir[j].split(" ");
                    aux = Integer.parseInt(auxPartir[1]);
                }                
            }
            archivo.seek(this.Startxref);
            byte meta[] = new byte[10];
            int contador = 0;
            info = "";
            while((cadena = archivo.readLine()) != null){
                if(contador == aux +1){
                    archivo.read(meta);                        
                    referencia = Integer.parseInt(new String(meta));
                    archivo.seek(referencia);
                    while(!"endobj".equals(cadena = archivo.readLine())){
                        info+=cadena;
                    }
                }
                contador++;
            }
            archivo.close();          
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
    }
     public String MetadataFinder(){
        String cadenaPrueba="";
        String nombres = "";
        String informacion = "";
        int refMetadatos=0;
        try {
            RandomAccessFile archivo = new RandomAccessFile(ruta,"r");
            byte ref[] = new byte[9];
            archivo.seek(archivo.length() - 350);
            while((cadenaPrueba = archivo.readLine()) != null){
                if("startxref".equals(cadenaPrueba)){               
                    cadenaPrueba = archivo.readLine();               
                    Startxref = Integer.parseInt(cadenaPrueba);                    
                    break;
                }
                else if("trailer".equals(cadenaPrueba)){
                    informacion = archivo.readLine();
                    if("<<".equals(informacion)){
                        informacion += archivo.readLine();
                        informacion += archivo.readLine();
                        informacion += archivo.readLine();
                    }
                }
            }            
            String[] partir = informacion.split("/");
            int a = informacion.split("/").length;
            int auxRef = 0;
            String buscador ="";
            for(int j=1; j<a; j++){
                for(int k=0; k<4; k++){
                    buscador+= partir[j].charAt(k);
                }                 
                if("Info".equals(buscador)){
                    String[] auxPartir = partir[j].split(" ");
                    auxRef = Integer.parseInt(auxPartir[1]);
                    buscador = "";
                }else if ("Root".equals(buscador)) {
                    String[] auxPartir = partir[j].split(" ");
                    root = Integer.parseInt(auxPartir[1]);
                    buscador="";
                }else{
                    buscador="";
                }
            }           
            archivo.seek(Startxref);
            cadenaPrueba = archivo.readLine();
            cadenaPrueba = archivo.readLine();
            int aux =0;
            String dato = "";
            try{
                for(int i = 2; i< cadenaPrueba.length(); i++){
                    dato += cadenaPrueba.charAt(i);
                }
                aux = Integer.parseInt(dato);
            }catch(NumberFormatException e){
                aux = 0;
            }
            if(aux != auxRef + 1){
                if(auxRef == 0){
                    auxRef = aux;
                    nombres = "Error al momento de encontrar los metadatos";
                }
                aux = auxRef+1;
            }       
            byte meta[] = new byte[10];
            int contador = 0;
            archivo.seek(Startxref);
            if(!"Error al momento de encontrar los metadatos".equals(nombres)){
                while((cadenaPrueba = archivo.readLine()) != null){                    
                    if(contador == aux){
                        archivo.read(meta);
                        refMetadatos = Integer.parseInt(new String(meta));
                        archivo.seek(refMetadatos);
                        for(int i = 0; i<10; i++){
                            cadenaPrueba = archivo.readLine();
                            if(cadenaPrueba.equals("endobj")){
                                break;
                            } else{
                                nombres += cadenaPrueba;
                            }
                        }
                        break;
                    }
                    contador++;
                }            
            }
            archivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e) {            
        }
        return nombres;
    } 
      public String TamanoFinder(){
        String tamano="";    
        try {
            RandomAccessFile archivo = new RandomAccessFile(ruta,"r");
            tamano=  archivo.getChannel().size() + " Bytes.";
            archivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e) {            
        }
        return tamano;
    }
    
    
    public String TamPagFinder(){
        float x = 0;
        float y = 0;
        float x2 = 0;
        float y2 = 0;
        String tamano = "";
        String cadena = "";
        String informacion = "";
        int auxRef = 0;
        int referencia = 0;
        String buscador = "";
        double pixel = 0.35277777778;
        boolean espacio= false;
        try {
            RandomAccessFile archivo = new RandomAccessFile(ruta,"r");
            byte meta[] = new byte[10];
            int contador = 0;
            archivo.seek(Startxref);
            while((cadena = archivo.readLine()) != null){            
                if(contador == catalogo+1){
                    archivo.read(meta);
                    referencia = Integer.parseInt(new String(meta));
                    archivo.seek(referencia);
                    informacion = archivo.readLine();
                    informacion = archivo.readLine();
                    informacion = archivo.readLine();
                    if("<<".equals(informacion)){
                        informacion += archivo.readLine();
                        informacion += archivo.readLine();
                        informacion += archivo.readLine();
                        espacio = true;
                    }else{
                        archivo.seek(referencia);
                        informacion = archivo.readLine();
                        informacion = archivo.readLine();
                    }
                    String[] partir = informacion.split("/");                 
                    int a = informacion.split("/").length;
                    for(int j=1; j<a; j++){
                        buscador="";
                        for (int k = 0; k < 4; k++) {
                            buscador+=partir[j].charAt(k);
                        }
                        if("Kids".equals(buscador)){
                            String[] auxPartir = partir[j].split(" ");
                            if(espacio){
                                auxRef = Integer.parseInt(auxPartir[2]);
                            }else{
                            auxRef = Integer.parseInt(auxPartir[1]);
                            }
                            break;
                        }         
                    }
                    break;
                }
                contador++;
            }       
            archivo.seek(Startxref);
            contador=0;
            buscador="";
            while((cadena = archivo.readLine()) != null){
                if(contador == auxRef+1){
                    archivo.read(meta);
                    referencia = Integer.parseInt(new String(meta));
                    archivo.seek(referencia);
                    informacion = archivo.readLine();
                    informacion = archivo.readLine();
                    informacion = archivo.readLine();
                    if("<<".equals(informacion)){
                        informacion += archivo.readLine();
                        informacion += archivo.readLine();
                        informacion += archivo.readLine();
                    }else{
                        archivo.seek(referencia);
                        informacion = archivo.readLine();
                        informacion = archivo.readLine();
                    }
                    String[] partir = informacion.split("/");
                    int a = informacion.split("/").length;
                    for(int j=1; j<a; j++) {
                        for (int k = 0; k < 1; k++) {
                            buscador+=partir[j].charAt(k);
                        }                
                        if("M".equals(buscador)){
                            String auxY="";
                            String[] auxPartir = partir[j].split(" ");
                            if(espacio){
                                x = (float) Double.parseDouble(auxPartir[4]);
                                for (int i = 0; i < auxPartir[5].length()-1; i++) {
                                    auxY+=auxPartir[5].charAt(i);
                                }
                            } else{
                                x = (float) Double.parseDouble(auxPartir[3]);
                                for (int i = 0; i < auxPartir[4].length()-1; i++) {
                                    auxY+=auxPartir[4].charAt(i);
                                }
                            }
                            y= (float) Double.parseDouble(auxY);
                        } else {
                            buscador="";
                        }
                    }
                    break;
                }
                contador++;
            }
            x2=(float) (x*pixel);
            y2=(float) (y*pixel);
            tamano=String.valueOf(x2);
            tamano+=" X "+ String.valueOf(y2);
            archivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException e) {
            
        }
        return tamano;
    }
   
    public String FuentesFinder(){
        int Tamanio = 0;
        String cadena = "";
        String info = "";  
        int referencia = 0;
        String buscador ="";
        String valores = "";
        int fuentes[] = new int[2];
        int sons[] = new int [2];        
        String NombreFuente = "";
        int hojas1 = 0;
        int hojas2 = 0;
        boolean espacio = false;
        try {            
            RandomAccessFile archivo = new RandomAccessFile(ruta,"r");
            byte meta[] = new byte[10];
            int contador = 0;
            archivo.seek(Startxref);
            while((cadena = archivo.readLine()) != null){            
                if(contador == catalogo+1){
                    archivo.read(meta);
                    referencia = Integer.parseInt(new String(meta));
                    archivo.seek(referencia);
                    info = archivo.readLine();
                    info = archivo.readLine();
                    info = archivo.readLine();
                    if("<<".equals(info)){
                        info += archivo.readLine();
                        info += archivo.readLine();
                        info += archivo.readLine();
                        espacio = true;
                    } else{
                        archivo.seek(referencia);
                        info = archivo.readLine();
                        info = archivo.readLine();
                    }
                    String[] partir = info.split("/");
                    int a = info.split("/").length;
                    for(int j=1; j<a; j++){
                        int contaux = 0;
                        for (int k = 0; k < 4; k++) {
                            buscador+=partir[j].charAt(k);
                        }
                        if("Kids".equals(buscador)){
                            String[] auxPartir = partir[j].split(" ");
                            if(espacio){
                                sons = new int [(partir[j].split(" ").length / 3)-1];                     
                                hojas1 = (partir[j].split(" ").length / 3) - 1;
                                for (int k = 2; k < partir[j].split(" ").length - 2; k = k+3) {
                                    sons[contaux]=Integer.parseInt(auxPartir[k]);
                                    contaux++;
                                }
                            } else{
                                sons = new int [partir[j].split(" ").length / 3];                     
                                hojas1 = partir[j].split(" ").length / 3;
                                for (int k = 1; k < partir[j].split(" ").length - 2; k = k+3) {
                                    sons[contaux]=Integer.parseInt(auxPartir[k]);
                                    contaux++;
                                }
                            }
                            break;
                        } else {
                            buscador="";
                        }
                    }
                    break;
                }
                contador++;
            }
            for(int c=0; c<hojas1; c++){
                archivo.seek(Startxref);
                contador=0;
                info ="";
                while((cadena = archivo.readLine()) != null){
                    if(contador==sons[c]+1){
                        archivo.read(meta);
                        referencia = Integer.parseInt(new String(meta));
                        if(espacio){
                            archivo.seek(referencia);
                            info += archivo.readLine();
                            info += archivo.readLine();
                            info += archivo.readLine();
                            info += archivo.readLine();
                            info += archivo.readLine();
                            info += archivo.readLine();
                            info += archivo.readLine();
                            info += archivo.readLine();
                            info += archivo.readLine();
                            String aux = info;
                            info = getResources(aux);
                        } else{
                            archivo.seek(referencia);
                            info = archivo.readLine();
                            info = archivo.readLine();
                        }                        
                        String[] partir = info.split("/");
                        int a = info.split("/").length;                        
                        for(int j=1; j<a; j++){
                            buscador="";
                            for (int k = 0; k < 1; k++) {
                                buscador+=partir[j].charAt(k);
                            }
                            if("F".equals(buscador)){                               
                                do{
                                    j++;
                                    if(j==a) {
                                        break;
                                    }
                                    buscador = "";
                                    for (int k = 0; k < 1; k++) {
                                        buscador+=partir[j].charAt(k);
                                    }
                                    if("F".equals(buscador)){
                                        String[] auxPartir = partir[j].split(" ");
                                        valores += Integer.parseInt(auxPartir[1]) + "/";
                                        Tamanio++;

                                    }
                                }while(buscador.equals("F"));
                            } else {
                            buscador="";
                            }                 
                        }
                        fuentes =new int[Tamanio];
                        hojas2 = Tamanio;
                        Tamanio = 0;
                        break;
                    }
                    contador++;
                }
            }
            System.out.println(valores);
            String[] unificar = valores.split("/");
            int contvalores = valores.split("/").length;
            String[] unAux = new String[contvalores];
            boolean entrada=true;
            for (int i = 0; i < contvalores; i++) {
                unAux[i]="";
            }
            valores = "";
            for(int c = 0; c <contvalores; c++){
                for (int i = 0; i <= c; i++) {
                    if (entrada) {
                        unAux[i]=unificar[c];
                        valores += unificar[c]+"/";
                        entrada=false;
                    } else if(!unificar[c].equals(unAux[i])&&unAux[i]==""){
                        unAux[i]=unificar[c];
                        valores += unificar[c]+"/";
                        break;
                    } else if (unificar[c].equals(unAux[i])) {
                        break;
                    }
                }
            }
            String[] partir3 = valores.split("/");
            System.out.println(valores);
            hojas2 = valores.split("/").length;
            fuentes = new int [hojas2];
            try{
                 for(int c = 0; c < hojas2; c++){
                     fuentes[c] = Integer.parseInt(partir3[c]);
                 }
            } catch(NumberFormatException e){
            }
            for(int c=0; c<hojas2; c++){
                archivo.seek(Startxref);
                contador=0;
                while((cadena = archivo.readLine()) != null){
                    if(contador==fuentes[c]+1){
                        archivo.read(meta);
                        referencia = Integer.parseInt(new String(meta));
                        archivo.seek(referencia);
                        info = archivo.readLine();
                        info = archivo.readLine();
                        info = archivo.readLine();
                        if("<<".equals(info)){
                            info += archivo.readLine();
                            info += archivo.readLine();
                            info += archivo.readLine();
                            espacio = true;
                        } else{
                            archivo.seek(referencia);
                            info = archivo.readLine();
                            info = archivo.readLine();
                        }
                        String[] partir = info.split("/");
                        int a = info.split("/").length;                        
                        for(int j=1; j<a; j++){
                            for (int k = 0; k < 2; k++) {
                                buscador+=partir[j].charAt(k);
                            }                
                            if("Ba".equals(buscador)){
                                j++;
                                buscador = "";
                                for (int k = 0; k < 3; k++) {
                                    buscador+=partir[j].charAt(k);
                                }
                                if("ABC".equals(buscador)){
                                    for (int k = 7; k < partir[j].length(); k++) {
                                        NombreFuente +=partir[j].charAt(k);
                                    }
                                    NombreFuente += "/";
                                } else{
                                    NombreFuente += partir[j] + "/";
                                }
                            } else {
                                buscador="";
                            }                 
                        }
                        break;
                    }
                    contador++;
                }
            }             
            archivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException e) {
            
        }
        return NombreFuente; 
    }
    
    public int NumPagFinder(){
    int paginas=0;
    String cadena="";
    String info="";
    int referencia=0;  
    try {
        RandomAccessFile archivo = new RandomAccessFile(ruta,"r");
        byte meta[] = new byte[10];
        int contador = 0;
        archivo.seek(Startxref);
        while((cadena = archivo.readLine()) != null){            
            if(contador == root+1){
                archivo.read(meta);
                System.out.println("esto es meta " +meta);
                referencia = Integer.parseInt(new String(meta));
                archivo.seek(referencia);
                info = archivo.readLine();
                info = archivo.readLine();
                info = archivo.readLine();
                if("<<".equals(info)){
                    info += archivo.readLine();
                    info += archivo.readLine();
                    info += archivo.readLine();
                }else{
                    archivo.seek(referencia);
                    info = archivo.readLine();
                    info = archivo.readLine();
                }                
                String[] partir = info.split("/");
                int a = info.split("/").length;
                String buscador ="";
                for(int j=1; j<a; j++){
                    buscador ="";
                    for (int k = 0; k < 4; k++) {
                        buscador+=partir[j].charAt(k);
                    }
                    if("Page".equals(buscador)){
                        String[] auxPartir = partir[j].split(" ");
                        catalogo = Integer.parseInt(auxPartir[1]);
                        break;
                    } 
                }  
                break;
            }
            contador++;
        }
        archivo.seek(Startxref);
        contador=0;
        while((cadena = archivo.readLine()) != null){
            if(contador == catalogo+1){
                archivo.read(meta);
                referencia = Integer.parseInt(new String(meta));
                archivo.seek(referencia);
                info = archivo.readLine();
                info = archivo.readLine();
                info = archivo.readLine();
                if("<<".equals(info)){
                    info += archivo.readLine();
                    info += archivo.readLine();
                    info += archivo.readLine();
                }else{
                    archivo.seek(referencia);
                    info = archivo.readLine();
                    info = archivo.readLine();
                }
                String[] partir = info.split("/");
                int a = info.split("/").length;
                String buscador = "";
                for(int j=1; j<a; j++){
                    buscador ="";
                    for(int k=0; k<4; k++){
                        buscador+= partir[j].charAt(k);
                    }
                    if("Coun".equals(buscador)){
                        String[] auxPartir = partir[j].split(" ");
                        paginas = Integer.parseInt(auxPartir[1]);
                    }
                }
                break;
            }
            contador++;
        }        
        archivo.close();
    }
    catch (FileNotFoundException ex) {
        Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
    }
        return paginas;
    }
      
    public int ImagenesFinder(){
        int tamano = 0;
        String cadena = "";
        String informacion = "";  
        int ref = 0;
        String buscador = "";
        int sons[] = new int [2];
        int hojas = 0;
        boolean espacio = false;
        try {            
            RandomAccessFile archivo = new RandomAccessFile(ruta,"r");
            byte meta[] = new byte[10];
            int contador = 0;
            archivo.seek(Startxref);
            while((cadena = archivo.readLine()) != null){
                if(contador == catalogo+1){
                    archivo.read(meta);
                    ref = Integer.parseInt(new String(meta));
                    archivo.seek(ref);
                    informacion = archivo.readLine();
                    informacion = archivo.readLine();
                    informacion = archivo.readLine();
                    if("<<".equals(informacion)){
                        informacion += archivo.readLine();
                        informacion += archivo.readLine();
                        informacion += archivo.readLine();
                        espacio = true;
                    } else{
                        archivo.seek(ref);
                        informacion = archivo.readLine();
                        informacion = archivo.readLine();
                    }
                    String[] partir = informacion.split("/");
                    int a = informacion.split("/").length;
                    for(int j=1; j<a; j++){
                        int contaux = 0;
                        for (int k = 0; k < 4; k++) {
                            buscador+=partir[j].charAt(k);
                        }
                        if("Kids".equals(buscador)){
                            String[] auxPartir = partir[j].split(" ");
                            if(espacio){
                                sons = new int [(partir[j].split(" ").length / 3)-1];                     
                                hojas = (partir[j].split(" ").length / 3) - 1;
                                for (int k = 2; k < partir[j].split(" ").length - 2; k = k+3) {
                                    sons[contaux]=Integer.parseInt(auxPartir[k]);
                                    contaux++;
                                }
                            } else{
                                sons = new int [partir[j].split(" ").length / 3];                     
                                hojas = partir[j].split(" ").length / 3;
                                for (int k = 1; k < partir[j].split(" ").length - 2; k = k+3) {
                                    sons[contaux]=Integer.parseInt(auxPartir[k]);
                                    contaux++;
                                }
                            }
                            break;
                        } else{
                            buscador="";
                        }
                    }
                    break;
                }
                contador++;
            }
            for(int c=0; c<hojas; c++){
                archivo.seek(Startxref);
                contador=0;                
                while((cadena = archivo.readLine()) != null){
                    informacion = "";
                    if(contador==sons[c]+1){
                        archivo.read(meta);
                        ref = Integer.parseInt(new String(meta));
                        if(espacio){
                            archivo.seek(ref);
                            informacion += archivo.readLine();
                            informacion += archivo.readLine();
                            informacion += archivo.readLine();
                            informacion += archivo.readLine();
                            informacion += archivo.readLine();
                            informacion += archivo.readLine();
                            informacion += archivo.readLine();
                            informacion += archivo.readLine();
                            informacion += archivo.readLine();
                            String auxiliar = informacion;
                            informacion = getResources(auxiliar);
                        }else{
                            archivo.seek(ref);
                            informacion = archivo.readLine();
                            informacion = archivo.readLine();
                        }
                        String[] partir = informacion.split("/");
                        int a = informacion.split("/").length;                        
                        for(int j=1; j<a; j++){
                            for (int k = 0; k < 1; k++) {
                                buscador+=partir[j].charAt(k);
                            }                
                            if("X".equals(buscador)){                               
                                do{                              
                                    j++;
                                    if(j == a){
                                        break;
                                    }
                                    buscador = "";
                                    for (int k = 0; k < 5; k++) {
                                        buscador+=partir[j].charAt(k);
                                    }
                                    if("Image".equals(buscador)){
                                        tamano++;
                                    }
                                }while(buscador.equals("Image"));                            
                            } else {
                                buscador="";
                            }                 
                        }                  
                        break;
                    }
                    contador++;
                }
            }          
            archivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e) {
            
        }
        return tamano;        
    }
    
     public String VersionFinder(){
        String version="";
        try {
            RandomAccessFile archivo = new RandomAccessFile(ruta,"r");
            version=archivo.readLine();
            archivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        }
  
        return version; 
    }
}
