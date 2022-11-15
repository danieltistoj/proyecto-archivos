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
public class Lista {
    private Pdf inicio;
    private int tamanio;
    
    public void Lista(){
        inicio = null;
        tamanio = 0;
    }
     
    public boolean esVacia(){
        return inicio == null;
    }
        
    public int getTamanio(){
        return tamanio;
    }
    
    public void agregarAlInicio(String TamArchivo,String TamPaginas,String NoPaginas,String Titulo,String Asunto, String PClave,String Tipo,String version,String app,String imagenes,String fuentes){
        // Define un nuevo nodo.
        Pdf nuevo = new Pdf();
        // Agrega al valor al nodo.
        nuevo.setTamArchivo(TamArchivo);
        nuevo.setTamPaginas(TamPaginas);
        nuevo.setNoPaginas(NoPaginas);
        nuevo.setTitulo(Titulo);
        nuevo.setAsunto(Asunto);
        nuevo.setPClave(PClave);
        nuevo.setTipo(Tipo);
        nuevo.setVersion(version);
        nuevo.setApp(app);
        nuevo.setImagenes(imagenes);
        nuevo.setFuentes(fuentes);        
        // Consulta si la lista esta vacia.
        if (esVacia()) {
            // Inicializa la lista agregando como inicio al nuevo nodo.
            inicio = nuevo;
            // Caso contrario va agregando los nodos al inicio de la lista.
        } else{
            // Une el nuevo nodo con la lista existente.
            nuevo.setSiguiente(inicio);
            // Renombra al nuevo nodo como el inicio de la lista.
            inicio = nuevo;
        }
        // Incrementa el contador de tamaño de la lista.
        tamanio++;        
    }
     
     public void listar(){
        // Verifica si la lista contiene elementoa.
        if (!esVacia()) {
            // Crea una copia de la lista.
            Pdf aux = inicio;
            // Posicion de los elementos de la lista.
            int i = 0;
            // Recorre la lista hasta el final.
            while(aux != null){
                // Imprime en pantalla el valor del nodo.
                System.out.println(i + ". " + aux.getTamArchivo());
                System.out.println(i + ". " + aux.getTamPaginas());
                System.out.println(i + ". " + aux.getNoPaginas());
                System.out.println(i + ". " + aux.getTitulo());
                System.out.println(i + ". " + aux.getAsunto());
                System.out.println(i + ". " + aux.getPClave());
                System.out.println(i + ". " + aux.getTipo());
                System.out.println(i + ". " + aux.getVersion());
                System.out.println(i + ". " + aux.getApp());
                System.out.println(i + ". " + aux.getImagenes());
                System.out.println(i + ". " + aux.getFuentes());             
                // Avanza al siguiente nodo.
                aux = aux.getSiguiente();
                // Incrementa el contador de la posión.
                i++;
            }
        }
    }
     
    public String rutaa(){      
      String rut = inicio.getRuta();      
      return rut;      
    }
       
    public void llenar(){
        // Verifica si la lista contiene elementoa.
        if (!esVacia()) {
            // Crea una copia de la lista.
            Pdf aux = inicio;
            // Posicion de los elementos de la lista.      
            // Recorre la lista hasta el final.
            while(aux != null){
                // Imprime en pantalla el valor del nodo.              
               aux.getTitulo();
                // Avanza al siguiente nodo.
                aux = aux.getSiguiente();
                // Incrementa el contador de la posión.             
            }
        }        
    }
      
    Pdf auxiliar;
  
    public Pdf getinicio(){       
       return inicio;
    }
      
    public Pdf getsiguinte(){
         return auxiliar.getSiguiente();
    }
    
    public void setsiguiente(Pdf sig){
        auxiliar=sig;
    }
    
    public String getnombre(){
        return auxiliar.getTitulo();
    }
    
    public String gettamArchivo(){
        return auxiliar.getTamArchivo();
    }
    
    public String getnoPaginas(){
        return auxiliar.getNoPaginas();
    }
    
    public String gettamPaginas(){
        return auxiliar.getTamPaginas();
    }
    
    public String gettipo(){
        return auxiliar.getTipo();
    }
    
    public String getversion(){
        return auxiliar.getVersion();
    }
    
    public String getapp(){
        return auxiliar.getApp();
    }
    
    public String getimagenes(){
        return auxiliar.getImagenes();
    }
    
    public String getfuentes(){
        return auxiliar.getFuentes();
    }
    
    public String getasunto(){
        return auxiliar.getAsunto();
    }
    
    public String getpClave(){
        return auxiliar.getPClave();
    }
}
