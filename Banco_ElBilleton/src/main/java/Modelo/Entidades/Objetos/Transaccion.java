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
public class Transaccion implements Serializable{
    private int codigo;
    private int numeroCuentaAfectada;
    private Cliente cliente;
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
    
    public void establecerClienteDuenoDeCuenta(Cliente dueno){
        cliente = dueno;
    }
  
    public String getNombreDuenoCuenta(){
        if(cliente!=null){
            return cliente.getNombre();
        }
        return "???";
    }
    
    public int getCodigoDuenoCuenta(){
        if(cliente!=null){
            return cliente.getCodigo();
        }
        return -1;
    }
    
    public String getTipoTransaccion(){
        return tipoTransaccion;
    }
    
    public double getMonto(){// a ver si no dan problemas estos tipos con el JR, así que tienes que averiguar como colocar los valores en tipo float o double en la DB
        return monto;
    }//por la forma de trabajar con las transcciones con el cajero, esta forma será empleada para obtener el monto en genearal, es decir en el caso en el que no nos importe el tipo, sino solo los datos que se registraron en la transacción...

    public double getDeposito(){
        if(tipoTransaccion.equalsIgnoreCase("credito")){
            return monto;
        }                
        return 0;
    }
    
    public double getDebito(){
        if(tipoTransaccion.equalsIgnoreCase("debito")){
            return monto;
        }                
        return 0;
    }//Estos métodos serán empleados para la ocasión en la que las mismas cantidades describan que tipo de transacción fue, sin necesidad de plasmar el tipo, esto por el tipo de reporte requerido por el cajero...
    
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
