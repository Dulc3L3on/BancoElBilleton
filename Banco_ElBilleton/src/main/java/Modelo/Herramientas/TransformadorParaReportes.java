/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Objetos.Transaccion;
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
    Connection conexion = ManejadorDB.darConexion();
    
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
                    listaTransacciones.add(transformarATransaccion(resultado));                               
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
}