/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Tramitador { 
    Connection conexion = ManejadorDB.darConexion();
    
     public boolean depositar(String numeroCuenta, String monto){
        String tramitar = "UPDATE Cuenta SET monto = monto + ? WHERE numeroCuenta = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(tramitar)){
            int numeroDeCuenta = Integer.parseInt(numeroCuenta);                 
            int montoDeposito = Integer.parseInt(monto);            
            
            instrucciones.setInt(1, montoDeposito);//codigo de la transaccion [autoIncre...]       
            instrucciones.setInt(2, numeroDeCuenta);            
            
            instrucciones.executeUpdate();
            return true;            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error al TRAMITAR el DEPOSITO: " + e.getMessage());
        }
        return false;        
    }
    
    public boolean retirar(String numeroCuenta, String monto){
        String tramitar = "UPDATE Cuenta SET monto = monto - ? WHERE numeroCuenta = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(tramitar)){
            int numeroDeCuenta = Integer.parseInt(numeroCuenta);                 
            int montoDebito = Integer.parseInt(monto);            
            
            instrucciones.setInt(1, montoDebito);//codigo de la transaccion [autoIncre...]       
            instrucciones.setInt(2, numeroDeCuenta);            
            
            instrucciones.executeUpdate();//puesto que en la interfaz se establece el máximo como la cantidad que tiene, entonces no habrá problemas de actualizar a una cantidad negativa xD
            return true;            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error al TRAMITAR el RETIRO: " + e.getMessage());
        }
        return false;   
    }
}
