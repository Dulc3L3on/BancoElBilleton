/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Herramientas.Transformador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public Usuario[] buscarUsuarios(String tipo, String tipoOrden){//Este tipo está = que el nombre en la DB
        String buscar ="SELECT * FROM "+ tipo+ " ORDER BY "+ tipoOrden;
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            
             ResultSet resultado = instrucciones.executeQuery();
             if(transformador.colocarseAlPrincipio(resultado)){
                return transformador.transformarAUsuarios(tipo.toLowerCase(), resultado);
             }             
        }catch (SQLException e) {
            System.out.println("Error: al buscar "+ tipo.toUpperCase() +" -> "+e.getMessage());
        }        
        return null;
    }  
    
    public Usuario buscarUsuario(String tipo, String columnaBusqueda, String datoBuscar){//Este tipo está = que el nombre en la DB
        String buscar ="SELECT * FROM "+ tipo+ " WHERE "+ columnaBusqueda +" = "+ datoBuscar;
        
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

    public Cuenta buscarCuenta(String codigoCuenta){
        String buscar ="SELECT * FROM Cuenta WHERE numeroCuenta = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
         
            int cuenta = Integer.parseInt(codigoCuenta);
            
            ResultSet resultado = instrucciones.executeQuery();
            if(transformador.colocarseAlPrincipio(resultado)){
                return transformador.transformarACuenta(resultado);
            }                                   
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al buscar la cuenta");
        }
        return null;        
    }
    
    
    private Cliente buscarDuenoDeCuenta(String numeroCuenta){
        String buscar = "SELECT * FROM Cuenta WHERE numeroCuenta = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){ 
            int cuenta = Integer.parseInt(numeroCuenta);
            
            instrucciones.setInt(1, cuenta);
            
            ResultSet resultado = instrucciones.executeQuery();
            if(resultado.first()){
                return (Cliente) buscarUsuario("Cliente", "codigo", String.valueOf(resultado.getInt("codigoDueno")));
            }            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error al buscar el dueño de la cuenta -> "+e.getMessage());
        }
        System.out.println("No existe el número de cuenta ingresado");
        return null;            
    }//YA NO, por la forma de trabajar... pues de lo contrario sería un excelente método xD
    
   
}
