/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Objetos.Asociacion;
import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Cajero;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Entidades.Usuarios.Gerente;
import Modelo.Entidades.Usuarios.Trabajador;
import Modelo.Manejadores.DB.Buscador;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Conversor {    
    Buscador buscador;
    
    public Cliente convertirACliente(String datos[], int codigo, String contrasenia, String fechaIncorporacion, String path){//al parecer el path lo recibiraás después... porque debes pensar como llamarás al otro servlet... en el que se encarga de la ... O podrías hacerlo con un dopost o un método más en este mismo servlet... pero eso implicaría tener otro para la carga de datos...
        return new Cliente(codigo, datos[0], datos[1], datos[4], datos[2], contrasenia, fechaIncorporacion, datos[5], datos[3], path);                
    }
    
    public Trabajador convertirATrabajador(String tipoTrabajador, String datos[], int codigo, String contrasenia,
      String fechaIncorporacion){
        switch(tipoTrabajador){
            case "Cajero":
                return convertirACajero(datos, codigo, contrasenia, fechaIncorporacion);//Se convierte implícitamente a trabajador xD
            case "Gerente":
                return convertirAGerente(datos, codigo, contrasenia, fechaIncorporacion);
        }
        return null;//como YO soy quien le mada el tipo xD NO PROBLEM! XD jajaja
    }
    
    private Cajero convertirACajero(String datos[], int codigo, String contrasenia, String fechaIncorporacion){
        return new Cajero(codigo, datos[0], datos[1], datos[4], datos[2], contrasenia, fechaIncorporacion, datos[5], datos[3]);        
    }
    
    private Gerente convertirAGerente(String datos[], int codigo, String contrasenia, String fechaIncorporacion){
        return new Gerente(codigo, datos[0], datos[1], datos[4], datos[2], contrasenia, fechaIncorporacion, datos[5], datos[3]);
    }
    
    public Cuenta convertirACuenta(int numeroCuenta, int codigoDueno, double monto, String fechaCreacion){
        return new Cuenta(numeroCuenta, codigoDueno, monto, fechaCreacion, "activa");
    }
    
    public Cuenta[] convertirACuentas(ResultSet resultado){
        buscador = new Buscador();
        
        try {         
            resultado.last();
            Cuenta[] cuentas = new Cuenta[resultado.getRow()];
            resultado.first();
                
            for (int cuentaActual = 0; cuentaActual < cuentas.length; cuentaActual++) {                                           
                cuentas[cuentaActual] = buscador.buscarCuenta(String.valueOf(resultado.getInt(1)));            
            }                  
            return cuentas;
        } catch (SQLException e){
            System.out.println("Error al convertir a CUENTA los números de cuenta hallados -> "+ e.getMessage());
        }
        
        return null;
    }
    
    public Transaccion convertirATransaccion(int elCodigo, int elNumeroCuenta, String elTipo, 
        double elMonto, String laFecha, String laHora, int elCodigoCajero){
        
        return new Transaccion(elCodigo, elNumeroCuenta, elTipo, elMonto, laFecha, laHora, elCodigoCajero);    
    }
    
    public Asociacion convertirAAsociacion(int codigoSolicitado, int cuentaDelSolicitado, int codigoSolicitante, 
            String estado, String fechaCreacion){
        
        return new Asociacion(codigoSolicitado, cuentaDelSolicitado, codigoSolicitante, estado, fechaCreacion);
    }    
}//CREO que me será más útiles con los JR xD... pues en el regisrador solo uso 2 :v xD
