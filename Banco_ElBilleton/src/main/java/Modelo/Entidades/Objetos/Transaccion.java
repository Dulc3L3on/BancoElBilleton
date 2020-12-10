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
public class Transaccion implements Serializable{
    private int codigo;
    private int numeroCuentaAfectada;
    private String tipoTransaccion;
    private double monto;
    private String fecha;
    private String hora;
    private int codigoCajeroACargo;
    private String nombreCajeroACargo;
    private double saldo;
    
    public Transaccion(int elCodigo, int elNumeroCuenta, String elTipo, double elMonto, 
            String laFecha, String laHora, int elCodigoCajero){
        codigo = elCodigo;
        numeroCuentaAfectada = elNumeroCuenta;
        tipoTransaccion = elTipo; 
        monto = elMonto; 
        fecha = laFecha; 
        hora = laHora;
        codigoCajeroACargo = elCodigoCajero;        
    }
    
    public int getCodigo(){
        return codigo;
    }
    
    public int getNumeroCuentaAfectada(){
        return numeroCuentaAfectada;
    }
    
    public String getTipoTransaccion(){
        return tipoTransaccion;
    }
    
    public double getMonto(){// a ver si no dan problemas estos tipos con el JR, así que tienes que averiguar como colocar los valores en tipo float o double en la DB
        return monto;
    }
    
    public String getFecha(){
        return fecha;
    }
    
    public String getHora(){
        return hora;
    }
    
    public int getCodigoCajeroACargo(){
        return codigoCajeroACargo;
    }    
    
    public void establecerNombreCajeroACargo(String nombreCajero){
        nombreCajeroACargo = nombreCajero;
    }
    
    public String getNombreCajeroACargo(){
        return nombreCajeroACargo;
    }
    
    public void establecerSaldoActual(double elSaldo){
        saldo = elSaldo;        
    }
    
    public double getSaldo(){
        return saldo;
    }
}
