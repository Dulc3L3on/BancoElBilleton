/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Herramientas.Transformador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Buscador {
    Connection conexion = ManejadorDB.darConexion();
    Transformador transformador = new Transformador();        
        
    public boolean esTablaLlena(int numeroEntidad){
        String entidades[] = {"Cliente", "Gerente", "Cajero", "Transaccion", "Cuenta"};
        String buscar = "SELECT * FROM "+entidades[numeroEntidad];
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            ResultSet resultado = instrucciones.executeQuery();            
            
           if(resultado.first()){
               return true;
           }
            
        }catch(SQLException sqlE){
            System.out.println("Error al verificar llenura de " + entidades[numeroEntidad] + " "+ sqlE.getMessage());
        }
        return false;
    }
    
    public Usuario[] buscarUsuario(String tipo, String tipoOrden){//Este tipo está = que el nombre en la DB
        String buscar ="SELECT * FROM "+ tipo+ " ORDER BY "+ tipoOrden;
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            
             ResultSet resultado = instrucciones.executeQuery();
             if(transformador.colocarseAlPrincipio(resultado)){
                return transformador.transformarAUsuario(tipo.toLowerCase(), resultado);
             }             
        }catch (SQLException e) {
            System.out.println("Error: al buscar "+ tipo.toUpperCase() +" -> "+e.getMessage());
        }        
        return null;
    }  
    
}
