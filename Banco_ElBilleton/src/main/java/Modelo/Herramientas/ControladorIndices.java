/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Manejadores.DB.ManejadorDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class ControladorIndices {
    private Connection conexion = ManejadorDB.darConexion();
    private int[] indicesMayores = {0,0,0,0,0};    
    private String[] nombresEntidades = {"Cliente", "Gerente", "Cajero", "Transaccion", "Cuenta"};
    //0-> cliente, 1-> gerente. 2-> cajero, 3-> transaccion, 4-> cuenta        
    
    public void hallarMayor(int valor, int tipoIndice){
        if(valor > indicesMayores[tipoIndice]){
           indicesMayores[tipoIndice] = valor; 
        }                
    }//deprecated   
   
    private void autoincrementar(){
        String columanAAfectar = "codigo";
        
        for(int entidadActual =0; entidadActual< indicesMayores.length; entidadActual++){
            if(nombresEntidades[entidadActual].equals("Cuenta")){
                columanAAfectar = "numeroCuenta";
            }
            
            autoincrementarEntidad(columanAAfectar, entidadActual);
        }                
    }//deprecated... ahí lo borras xD igual a los demás xD
    
    public int autoincrementarEntidad(String columnaAlterar, int numeroEntidad){               
        String alterar ="SELECT MAX("+ columnaAlterar + ") FROM "+nombresEntidades[numeroEntidad];
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(alterar)){
            ResultSet resultado = instrucciones.executeQuery();
            
            if(resultado.next()){//si da error es porque tambien debes cb su característica de sensibilidad...
                return resultado.getInt(1) +1;//para tener el valor que corresponde xD
            }            
        } catch (SQLException e) {
            System.out.println("Error al convertir autoincrementable a "+  nombresEntidades[numeroEntidad] +": "+e.getMessage());
        }    
        return 0;//Ahí te recuerdas xD que 0 siempre es porque la operaicón salió mal... xD
    }
    
    
    public int[] darIndicesMayores(){
        return indicesMayores;
    }//deprecated
    
    public int darIndiceMayor(int tipoIndice){
        return indicesMayores[tipoIndice];
    }//deprecated
    
    
}

