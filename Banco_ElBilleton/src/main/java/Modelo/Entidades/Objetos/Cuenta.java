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
public class Cuenta{
    private int numeroCuenta;
    private int codigoDueno;    
    private int monto;
    private String fechaCreacion;
    private String estado;
    
    public Cuenta(int elNumeroCuenta, int elCodigoDueno, int elMonto, String laFechaCreacion, String elEstado){
        numeroCuenta = elNumeroCuenta;
        codigoDueno = elCodigoDueno;
        monto = elMonto;
        fechaCreacion = laFechaCreacion;
        estado = elEstado;
    }
    
    public void cambiarSaldo(int nuevoSaldo){
        //monto = nuevoSlado;//porque creo que la op se hace en el gestor... tu basate en lo anterior...
    }
    
    public int getNumeroCuenta(){
        return numeroCuenta;
    }
    
    public int getCodigoDueno(){
        return codigoDueno;
    }
    
    public int getMonto(){
        return monto;
    }
    
    public String getFechaCreacion(){
        return fechaCreacion;
    }
    
}
