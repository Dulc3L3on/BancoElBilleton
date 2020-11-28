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
public class Nodo<E> {
     public E contenido;
     private E atributoExtra;
     public Nodo<E> nodoSiguiente;
     private int numeroElementosEnNodo;
     private String nombre;

        /**
         *ctrctor para 1er elemento es decir, cabeza
         * @param elemento
         */
        public Nodo(E elemento){
            this(elemento, null);//mando a llamar al ctrctor que recibe 2 parámetros
        }
        /**
         *ctrctor para nodos addi a la cabeza
         * @param elemento
         * @param siguiente 
         */
        public Nodo(E elemento, Nodo<E> siguiente)
        {//a ver si no te da problema el nodo por no estar especigicando su tipo... puesto que esta clase es genérica y aquí estas creando uno sin saber, asi que creo que debería de  especificarselo
            contenido = elemento;
            nodoSiguiente = siguiente;
        }

        public void reestablecerContenido(E contenidoNuevo)
        {
            contenido = contenidoNuevo;
        }

        /**
        *Añade un nodo si importar [Es decir que tipo de nodo era [null o no]
        *la posición, el conteido antiguo, el nuevo. Por lo tanto hace crecer la lista!
        * @param contenido
        */
        public void AnadirUnSiguiente(E contenido)//no quedaría mejor el nombre añadir UnSIiguiente,
        //puesto que sin importar donde se encuentre, qué posea el nodo ó que se le vaya a agregar de contenido
        //siempre se terminará agregando un nodo, en la posición indicada y por ello  el listado crecerá 
        {
            Nodo<E> nodoAuxiliar = nodoSiguiente;

            nodoSiguiente = new Nodo<>(contenido, nodoAuxiliar);//y aspi preservo todo lo que ese nodo antiguo sigueinte acarrea xD
        }//este es para reestablecer teniendo el contenido y el de abajo teniendo de una vez el nodo :v

        public void establecerAtributoExtra(E contenidoExtra){
            atributoExtra = contenidoExtra;
        }
        
        public void establecerSiguiente(Nodo<E> siguiente)
        {
            nodoSiguiente = siguiente;
        }

        public void establecerNombre(String nombreEstablecido) {
            nombre = nombreEstablecido;
        }

        public E obtenerObjectcEnCasilla()
        {//no será necesario el índice?? para hacer ref a uno específico y obtener sus respect datos??
            return contenido;
        }

        public Nodo<E> obtenerSiguiente()
        {//Aqupi estas refiriendote al nodo, mas no al objeto que dentro de él está contenido
            return nodoSiguiente;
        }

        public E obtenerAtributoExtra(){
            return atributoExtra;
        }
        
        public void incrementarNumeroElementosNodo()
        {
            numeroElementosEnNodo++;
        }//no me gusta, lo voy a borrar :v, igual el de abajo[auqnue talvez ese pasa, pero aún así noup xD] y por ello la var corresp

        public int obtenerNumeroElementosNodo()
        {
            return numeroElementosEnNodo;
        }

        public String darNombre() {
            return nombre;
     
   }
}
