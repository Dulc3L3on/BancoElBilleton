/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Objetos.Cambio;
import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Cajero;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Manejadores.DB.ManejadorDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author phily
 */
public class TransformadorParaReportes {
    private Connection conexion = ManejadorDB.darConexion();
    private Transformador transformador = new Transformador();
    
    public boolean colocarseAlPrincipio(ResultSet resultado){
        try{
            return resultado.first();            
        }catch(SQLException sqlE){
            System.out.println("Error: al colocarse al principio del resultado -> "+ sqlE.getMessage());
        }
        return false;
    }
    
    public List<Transaccion> transformarATransacciones(ResultSet resultado){
        List<Transaccion> listaTransacciones = new LinkedList<>();
        
        try{    
            if(resultado.first()){
                do{                    
                    Transaccion transaccion = transformarATransaccion(resultado);
                   
                   if(transaccion!=null){
                        listaTransacciones.add(transaccion);
                    }//no hay que agregar si != null entonces agergarlo al listado???                             
                }while(resultado.next());                            
            }            
        }catch(SQLException sqlE){
            System.out.println("Error al transformar a TRANSACCIONES -> "+ sqlE.getMessage());
        }
        return listaTransacciones;
    }
    
    public Transaccion transformarATransaccion(ResultSet resultado){
        try {        
            return new Transaccion(resultado.getInt(1),resultado.getInt(2),
                resultado.getString(3), resultado.getDouble(4), resultado.getString(5),
                resultado.getString(6), resultado.getInt(7));
       
        } catch (SQLException e) {
            System.out.println("Error al transformar a TRANSACCION: "+ e.getMessage());
        }        
         return null;               
    }
    
    public List<Cambio> transformarACambios(ResultSet resultado, String tipoUsuario){
        List<Cambio> listaCambios = new LinkedList<>();
        
        try{            
           do{
                Cambio cambio = transformarACambio(resultado, tipoUsuario);
                if(cambio!=null){
                    listaCambios.add(cambio);  
                }                    
            }while(resultado.next());                  
        }catch(SQLException sqlE){
            System.out.println("Error al transformar a CAMBIOS -> "+ sqlE.getMessage());
        }
        return listaCambios;    
    }
    
    public Cambio transformarACambio(ResultSet resultado, String tipoUsuario){
        try{
            Cambio cambio = new Cambio(resultado.getString(1), resultado.getString(2), resultado.getInt(3), resultado.getString(4), resultado.getString((tipoUsuario.equalsIgnoreCase("gerente"))?5:6), resultado.getString((tipoUsuario.equalsIgnoreCase("Gerente")?6:7)));                       
            cambio.establecerCodigoUsuarioCambiado((tipoUsuario.equalsIgnoreCase("Gerente"))?resultado.getInt(3):resultado.getInt(5));
            return cambio;
        }catch(SQLException e){
            System.out.println("Error al transformar a CAMBIO -> "+ e.getMessage());
        }//
        return null;
    }
    
    public List<Cliente> transformarAListadoClientesAdinerados(ResultSet resultado){
        List<Cliente> listadoClientes = new LinkedList<>();
        
        try {             
            do{//PUESTO que ya se revisó que posea como mínimo un registro para emplear este método..
                Cliente cliente = transformador.transformarACliente(resultado);
                    
                if(cliente!=null){
                    cliente.establecerSaldoTodasCuentas(resultado.getDouble(10));
                    listadoClientes.add(cliente);
                }                                        
            }while(resultado.next());//no bastaba cn colocar solo un while??? puesto que la primera ve que se exe el .next colocaría en el primer registro y si devolvía false es porque no hay registro alguno...                
        } catch (SQLException e) {
            System.out.println("Error al transformar a CLIENTES");
        }
        return listadoClientes; 
    } 
    
    public Cajero transformarACajeroVirtual(ResultSet resultado){
        try{
            return new Cajero(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
                    resultado.getString(4), resultado.getString(5), resultado.getString(6), 
                    resultado.getString(8), resultado.getString(9), resultado.getString(7));
        }catch(SQLException e){
            System.out.println("Error al transformar el resultado a CAJERO VIRTUAL -> "+e.getMessage());
        }        
        return null;        
    }       
}