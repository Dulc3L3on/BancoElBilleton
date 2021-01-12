/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Usuarios;

import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Manejadores.DB.Registrador;
import Modelo.Manejadores.DB.Tramitador;
import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Cajero extends Trabajador implements Serializable{
    private int numeroTransaccionesAtendidas; 
    private Tramitador tramitador = new Tramitador();
    private Registrador registrador = new Registrador();
    
    public Cajero(int elCodigo, String elNombre, String elDPI, String laDireccion,
        String elGenero, String elPassword, String laFechaIncorporacion, String elCorreo, String elTurno) {
        super(elCodigo, elNombre, elDPI, laDireccion, elGenero, elPassword, laFechaIncorporacion, elCorreo, elTurno);
    }
    
    public Transaccion realizarTransaccion(String numeroCUenta, String monto, String tipo){
      Transaccion transaccion = null;
        
      if(tipo.equals("debito")){
           if(tramitador.retirar(numeroCUenta, monto)){
                transaccion = registrador.registrarTransaccion(codigo, numeroCUenta, monto, tipo);
            }       
      }else{
          if(tramitador.depositar(numeroCUenta, monto)){
                transaccion = registrador.registrarTransaccion(codigo, numeroCUenta, monto, tipo);
          }
      }      
      
      return transaccion;     
    }
    
    public Transaccion[] realizarTransferencia(String cuentaOrigen, String cuentaDestino, String monto){
        Transaccion transacciones[] = null;
        
        if(tramitador.transferir(cuentaOrigen, cuentaDestino, monto)){
            transacciones = registrador.registrarTransferencia(codigo, cuentaOrigen, cuentaDestino, monto);
        }
        return transacciones;
    }
    
    public void establecerNumeroTransacciones(int numero){
        numeroTransaccionesAtendidas = numero;
    }
    
    public int getNumeroTransaccionesTrabajadas(){
        return numeroTransaccionesAtendidas;
    }
}
