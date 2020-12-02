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
public class Exterminador {
    private Connection conexion = ManejadorDB.darConexion();
    
    public boolean deshacerRegistroTransaccion(int codigoTransaccion){
        String eliminar ="DELETE FROM Transaccion WHERE codigo = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(eliminar)){
            instrucciones.setInt(1, codigoTransaccion);
            
            instrucciones.executeUpdate();
            
        }catch(SQLException sqlE){
            System.out.println("Error al deshacer la TRANSACCION: "+ sqlE.getMessage());
        }
        return false;       
    }
    
    
}
