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
public class Transaccion {
    private int codigo;
    private int numeroCuentaAfectada;
    private String TipoTransaccion;
    private int monto;
    private String fecha;
    private String hora;
    private int codigoCajero;
    
    public Transaccion(int elCodigo, int elNumeroCuenta, String elTipo, int elMonto, 
            String laFecha, String laHora, int elCodigoCajero){
        codigo = elCodigo;
        numeroCuentaAfectada = elNumeroCuenta;
        TipoTransaccion = elTipo; 
        monto = elMonto; 
        fecha = laFecha; 
        hora = laHora;
        codigoCajero = elCodigoCajero;        
    }
    
    public int getCodigo(){
        return codigo;
    }
    
    public int getNumeroCuenta(){
        return numeroCuentaAfectada;
    }
    
    public String getTipoTransaccion(){
        return TipoTransaccion;
    }
    
    public int getMonto(){
        return monto;
    }
    
    public String getFechaRealizacion(){
        return fecha;
    }
    
    public String getHoraRealizacion(){
        return hora;
    }
    
    public int getCodigoCajero(){
        return codigoCajero;
    }    
}
