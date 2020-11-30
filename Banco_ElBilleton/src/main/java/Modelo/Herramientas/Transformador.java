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
import Modelo.Entidades.Usuarios.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Transformador {//recuerda para qué habías dicho que serviría esta clase...
    
    public boolean colocarseAlPrincipio(ResultSet resultado){
        try{
            return resultado.first();            
        }catch(SQLException sqlE){
            System.out.println("Error: al colocarse al principio del resultado -> "+ sqlE.getMessage());
        }
        return false;
    }//Aunque si lo piensas, cuando esto diera una excep, entonces se llevaría al método siguiente y por lo tanto tb propvocaría una excep y por lo tanto devolvería null, justo lo que debería devoler...
    
    public Usuario[] transformarAUsuarios(String tipo, ResultSet resultado){
        switch(tipo){
            case "gerente":
                return transformarAGerentes(resultado);
            case "cajero":
                return transformarACajeros(resultado);
        }
        return transformarAClientes(resultado);//no es necesario el casteo, puesto que es implícita su genealogía xD...
    }
    
    public Usuario transformarAUsuario(String tipo, ResultSet resultado){
        switch(tipo){
            case "cliente":
               return transformarACliente(resultado);
            case "cajero":
               return transformarACajero(resultado);             
        }
        return transformarAGerente(resultado);
    }
    
    private Cliente[] transformarAClientes(ResultSet resultado){                
        Cliente[] clientes = null;
        
        try {
            resultado.last();            
            clientes = new Cliente[resultado.getRow()];
            resultado.first();
            
            for(int clienteActual =0; clienteActual<clientes.length; clienteActual++){
                clientes[clienteActual] = transformarACliente(resultado);
                resultado.next();                
            }
        } catch (SQLException e) {
            System.out.println("Error al transformar a CLIENTES");
        }
        return clientes;        
    }
    
    private Cliente transformarACliente(ResultSet resultado){
        try {                        
            return new Cliente(resultado.getInt(1),resultado.getString(2),
                    resultado.getString(3), resultado.getString(4), resultado.getString(5),
                    resultado.getString(6), resultado.getString(7), resultado.getString(8));
        } catch (SQLException ex) {
            System.out.println("Error al transformar a CLIENTE: "+ ex.getMessage());
        }        
        return null;
    }
    
     public Trabajador[] transformarATrabajador(String tipo, ResultSet resultado){                
        Trabajador[] trabajadores;//no lo pongo de una vez en su propio tipo general [trabajador] porque requiero que se vuelva un usuario... aunque si podría... como de todos modos trabajador es un hijo de usuario y de todos modos tendrías que mantener los métodos de transformación a un solo individuo, entonces no habría problema [creo que lo que no puedes es,pasar el papá a la forma del hijo... así que por eso debes mantener la transformación individual...
        
        switch(tipo){
            case"gerente":
               return transformarAGerentes(resultado);
            case "cajero":
               return transformarACajeros(resultado);
        }
        return null;//pero no debería llegar aquí porque YO soy quien le manda los tipos xD       
    }//será empleado en el gestor de creación... lo cual te será útil al mostrar los resultados de la creación...
    
    public Cajero[] transformarACajeros(ResultSet resultado){
        Cajero[] cajeros = null;
        
        try {
            resultado.last();            
            cajeros = new Cajero[resultado.getRow()];
            resultado.first();
            resultado.next();//con tal de que no se muestre el cajero de la banca virtual...
            
            for(int clienteActual =0; clienteActual<cajeros.length; clienteActual++){
                cajeros[clienteActual] = transformarACajero(resultado);
                resultado.next();                
            }
        } catch (SQLException e) {
            System.out.println("Error al transformar a CAJEROS: "+ e.getMessage());
        }
        return cajeros;                        
    }
     
    private Cajero transformarACajero(ResultSet resultado){
        try {       
            if(resultado.getInt(1)!=101){//para que no puedan mostrarse los datos del cajero virtual xD
            return new Cajero(resultado.getInt(1),resultado.getString(2),
                    resultado.getString(3), resultado.getString(4), resultado.getString(5),
                    resultado.getString(6), resultado.getString(7));
            }            
        } catch (SQLException e) {
            System.out.println("Error al transformar a CAJERO: "+ e.getMessage());
        }        
        return null;
    }
    
    public Gerente[] transformarAGerentes(ResultSet resultado){
        Gerente[] gerentes = null;
        
        try {
            resultado.last();            
            gerentes = new Gerente[resultado.getRow()];
            resultado.first();
            
            for(int gerenteActual =0; gerenteActual<gerentes.length; gerenteActual++){
                gerentes[gerenteActual] = transformarAGerente(resultado);
                resultado.next();                
            }
        } catch (SQLException e) {
            System.out.println("Error al transformar a GERENTES: "+e.getMessage());
        }
        return gerentes;                        
    }
     
    private Gerente transformarAGerente(ResultSet resultado){
        try {                        
            return new Gerente(resultado.getInt(1),resultado.getString(2),
                    resultado.getString(3), resultado.getString(4), resultado.getString(5),
                    resultado.getString(6), resultado.getString(7));
        } catch (SQLException e) {
            System.out.println("Error al transformar a GERENTE: "+ e.getMessage());
        }        
        return null;
    }
    
    public Cuenta[] transformarACuentas(ResultSet resultado){
        Cuenta[] cuentas = null;
        
        try{
            resultado.last();
            cuentas = new Cuenta[resultado.getRow()];
            resultado.first();
            
            for(int cuentaActual = 0; cuentaActual < cuentas.length; cuentaActual++) {
               cuentas[cuentaActual] = transformarACuenta(resultado);
               resultado.next();
            }            
        }catch(SQLException sqlE){
            System.out.println("Error al transformar a CUENTAS: "+ sqlE.getMessage());
        }
        return cuentas;
    }
    
    public Cuenta transformarACuenta(ResultSet resultado){
        try{
            return new Cuenta(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3),
            resultado.getString(4), resultado.getString(5));
        }catch(SQLException sqlE){
            System.out.println("Error al transformar a CUENTA: "+sqlE.getMessage());
        }
        return null;
    }
    
}
