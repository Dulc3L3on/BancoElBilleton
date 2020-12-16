/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Objetos;

import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Cambio implements Serializable{
    private int codigoGerenteACargo;
    private String nombreGerenteACargo;
    private String fecha;
    private String hora;
    private String tipo;
    private int codigoUsuarioCambiado;
    private String datoNuevo;
    private String datoAntiguo;    
    
    public Cambio(String laFecha, String laHora, int unGerenteACargo, String elTipo, String elDatoAntiguo, String elDatoNuevo){
        codigoGerenteACargo = unGerenteACargo;
        fecha = laFecha; 
        hora = laHora; 
        tipo = elTipo;
        datoNuevo = elDatoNuevo;        
        datoAntiguo = elDatoAntiguo;        
    }      
    
    public void establecerNombreGerenteACargo(String elNombre){
        nombreGerenteACargo = elNombre;
    }
    
    public int getCodigoGerenteACargo(){
        return codigoGerenteACargo;
    }
    
    public String getNombreGerenteACargo(){
        return nombreGerenteACargo;
    }    
    
    public String getFecha(){
        return fecha;
    }
    
    public String getHora(){
        return hora;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public void establecerCodigoUsuarioCambiado(int codigoUsuario){
       codigoUsuarioCambiado = codigoUsuario;
    }
    
    public int getCodigoUsuarioCambiado(){
        return codigoUsuarioCambiado;
    }
    
    public String getDatoNuevo(){
        return datoNuevo;
    }
    
    public String getDatoAntiguo(){
        return datoAntiguo;
    }       
}
