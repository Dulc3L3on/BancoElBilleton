/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Objetos;

/**
 *
 * @author phily
 */
public class Asociacion {
    private int codigoSolicitado;
    private int numeroCuentaSolicitado;
    private int codigoSolicitante;
    private String fechaCreacion;
    private String estado;    
    
    public Asociacion(int elCodigoSolicitado, int laCuentaSolicitada, int elCodigoSolicitante,
         String laFechaCreacion, String elEstado){
    
        codigoSolicitado = elCodigoSolicitado;
        numeroCuentaSolicitado = laCuentaSolicitada;
        codigoSolicitante =  elCodigoSolicitante;
        estado = elEstado;
        fechaCreacion = laFechaCreacion;
    }
    
    public int getCodigoSolicitado(){
        return codigoSolicitado;
    }
    
    public int getNumeroCuentaSolicitado(){
        return numeroCuentaSolicitado;
    }
    
    public int getCodigoSolicitante(){
        return codigoSolicitante;
    }
    
    public String getFechaCreacion(){
        return fechaCreacion;
    }
    
    public String getEstado(){
        return estado;
    }
    
    
}
