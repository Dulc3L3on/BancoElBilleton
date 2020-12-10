/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author phily
 */
public class ListaEnlazada <E>{
    private Nodo<E> primerNodo;
    private Nodo<E> ultimoNodo;
    private String nombreLista;
    private int tamanioLista=0;    
    
    public ListaEnlazada(){
        crearListar();
    }
    
    public ListaEnlazada(String nombreLista){//Este será empleado para las propiedades del jugador
        crearListar();
        establecerNOmbre(nombreLista);
    }
    
    public void insertarAlInicio(E elementoInsertar){
        primerNodo=ultimoNodo= new Nodo<>(elementoInsertar);
        tamanioLista++;
    }      
    
    public void crearListar(){
        //si tine nombre aquí deberías indicarlo
        primerNodo=ultimoNodo=null;//porque no tienen ningun elemento
    }
    
    public void establecerNOmbre(String nombre){
        nombreLista=nombre;
    }
      
    public void anadirAlFinal(E elementoInsertar){
        if(estaVacia()){
            insertarAlInicio(elementoInsertar);            
        }        
        else{
             ultimoNodo=ultimoNodo.nodoSiguiente= new Nodo<>(elementoInsertar);
             tamanioLista++;
        }        
    }
    
    public void anadirContenidoDeLista(ListaEnlazada<E> otraLista){
        Nodo<E> nodoAuxiliar = otraLista.obtnerPrimerNodo();
        
        for (int elementoActual = 0; elementoActual < otraLista.darTamanio(); elementoActual++) {
            anadirAlFinal(nodoAuxiliar.contenido);           

            nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
        }                
    }//y así se añade contenido de otra lista en la actual xD es decir en la lista para la cual se llamó este método xD
    
    public void eliminarUltimoNodo() {
            if (ultimoNodo!=null) {
                if (tamanioLista == 1)
                {
                    primerNodo = ultimoNodo = null;
                }
                else
                {
                    Nodo<E> nodoAuxiliar = primerNodo;

                    for (int nodoActual = 1; nodoActual < (tamanioLista - 1); nodoActual++)//debe ser -1 puesto que quiero quedarme en el penúltimo nodo para así eliminar al último, además por el hehco de tener antes de comenzar con el ciclo el primer nodo, es necesario que considere el valor al que quiero llegar a sabiendas de que ya llevo adelantado un paso...
                    { //Así cabal se queda en el penúltimo nodo...
                        nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
                    }

                    nodoAuxiliar.nodoSiguiente = null;//bye bye xD GRACIAS POR TUS SERVICIOS... XD
                    ultimoNodo = nodoAuxiliar;//Si la lista tiene 1 solo ele cabal 
                }

                tamanioLista--;
           }            
     }
      
    public void limpiar(){
        primerNodo = ultimoNodo =null;
        tamanioLista=0;
        nombreLista=null;//no he usado este campo, pero por si acaso xD
    }
      
    /**
     * Añade un nuevo siguiente al nodo de la lista qué invocó
     * al método [si se ingersa un nodo que no le pertenezca, se
     * generará una inconsistencia con el tamaño...]     
     * 
     * @param nodoBase
     * @param contenido
     */
    public void agregarNuevoSiguiente(Nodo<E> nodoBase, E contenido){//ese problema se resolvería enviando la listaENlazada a la que le pertenece el nodo y el nodo o la lista y la posición donde se add, pero eso no lo quería hacer así porque implicaría recorrer nuevamente la lista, eso último sería para add donde sea no habiendo recorrido dicho listado antes, de preferencia...
         nodoBase.AnadirUnSiguiente(contenido);
         tamanioLista++;
     }  

     public E darContenidoUltimoNodo() {
            return ultimoNodo.contenido;
     }
    
     public boolean estaVacia(){
        return primerNodo==null;
    }
    
    public Nodo<E> obtnerPrimerNodo (){
        return primerNodo;
    }
    
    public Nodo<E> obtenerUltimoNodo(){
        return ultimoNodo;
    }   
    
    public int darTamanio(){
        return tamanioLista;
    }
   
    public String obtenerNombre(){
        return nombreLista;
    }
    
}