/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Entidades.Usuarios.Cajero;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Entidades.Usuarios.Gerente;
import Modelo.Entidades.Usuarios.Trabajador;

/**
 *
 * @author phily
 */
public class Conversor {
    public Cliente convertirACliente(String datos[], int codigo, String contrasenia, String path){//al parecer el path lo recibiraás después... porque debes pensar como llamarás al otro servlet... en el que se encarga de la ... O podrías hacerlo con un dopost o un método más en este mismo servlet... pero eso implicaría tener otro para la carga de datos...
        return new Cliente(codigo, datos[0], datos[1], datos[4], datos[2], contrasenia, datos[3], path);
    }
    
    public Trabajador convertirATrabajador(String tipoTrabajador, String datos[], int codigo, String contrasenia){
        switch(tipoTrabajador){
            case "Cajero":
                return convertirACajero(datos, codigo, contrasenia);//Se convierte implícitamente a trabajador xD
            case "Gerente":
                return convertirAGerente(datos, codigo, contrasenia);
        }
        return null;//como YO soy quien le mada el tipo xD NO PROBLEM! XD jajaja
    }
    
    private Cajero convertirACajero(String datos[], int codigo, String contrasenia){
        return new Cajero(codigo, datos[0], datos[1], datos[2], datos[3], contrasenia, datos[4]);
    }
    
    private Gerente convertirAGerente(String datos[], int codigo, String contrasenia){
        return new Gerente(codigo, datos[0], datos[1], datos[2], datos[3], contrasenia, datos[4]);
    }
    
    public Cuenta convertirACuenta(int numeroCuenta, int codigoDueno, int monto, String fechaCreacion){
        return new Cuenta(numeroCuenta, codigoDueno, monto, fechaCreacion);
    }
    
}
