/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Objetos;

import Modelo.Entidades.Usuarios.Cliente;
import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Cuenta implements Serializable{
    private int numeroCuenta;
    private int codigoDueno; 
    private Cliente duenoCuenta;
    private double monto;
    private String fechaCreacion;
    private String estado;
    private String tipo;
    
    public Cuenta(int elNumeroCuenta, int elCodigoDueno, double elMonto, String laFechaCreacion, String elEstado){
        numeroCuenta = elNumeroCuenta;
        codigoDueno = elCodigoDueno;
        monto = elMonto;
        fechaCreacion = laFechaCreacion;
        estado = elEstado;
    }
    
    public void cambiarSaldo(int nuevoSaldo){
        //monto = nuevoSlado;//porque creo que la op se hace en el gestor... tu basate en lo anterior...
    }
    
    public void establecerDuenoCuenta(Cliente dueno){
       duenoCuenta = dueno;
       
       if(duenoCuenta!=null){//creo que no pasará la situación en la que cliente sea nulo, porque eso se revisa en el gestor que se encarga de llenar los listados con los datos correspondientes, de tal manera que pueda informarse que "NO fueron hallados los dato a listar"... [para que pueda darse un msje general sin importar la istuación... es decir hubo un error al buscar o no se poseen los datos que el reporte muestra...
           codigoDueno =duenoCuenta.getCodigo();
       }else{
           codigoDueno = -1;
       }       
    }
    
    public int getNumeroCuenta(){
        return numeroCuenta;
    }
    
    public int getCodigoDuenoCuenta(){
        return codigoDueno;
    }
    
    public String getNombreDuenoCuenta(){
        if(duenoCuenta!=null){
            return duenoCuenta.getNombre();
        }
        return "???";
    }
    
    public double getMonto(){
        return monto;
    }
    
    public String getFechaCreacion(){
        return fechaCreacion;
    }
    
    public String getTipo(){
        return "ahorro";
    }
}
