/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import java.sql.Connection;

/**
 *
 * @author phily
 */
public class Alterador {
    Connection conexion = ManejadorDB.darConexion();
    
    public void autoincrementarEntidad(int tipoEntidad){
        String alterar ="SELECT (codigo) ";
        
        
    }    
    
    
    
}
