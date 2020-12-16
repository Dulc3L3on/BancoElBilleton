/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Usuarios;

import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Cajero extends Trabajador implements Serializable{
    private int numeroTransaccionesAtendidas; 
    
    public Cajero(int elCodigo, String elNombre, String elDPI, String laDireccion, String elGenero, String elPassword, String laFechaIncorporacion, String elTurno) {
        super(elCodigo, elNombre, elDPI, laDireccion, elGenero, elPassword, laFechaIncorporacion, elTurno);
    }
    
    public void establecerNumeroTransacciones(int numero){
        numeroTransaccionesAtendidas = numero;
    }
    
    public int getNumeroTransaccionesTrabajadas(){
        return numeroTransaccionesAtendidas;
    }
}
