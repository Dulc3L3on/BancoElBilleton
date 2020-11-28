/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Manejadores.DB.Buscador;
import Modelo.Manejadores.DB.ManejadorDB;
import java.sql.Connection;

/**
 *
 * @author phily
 */
public class GuardiaSeguridad {
    Buscador buscador = new Buscador();
    Connection conexion = ManejadorDB.darConexion();
    
    public boolean estanTodasLlenas(){
        for(int entidadActual =0; entidadActual<5; entidadActual++){
            if(!buscador.esTablaLlena(entidadActual)){
                return false;
            }
        }        
        return true;
    }
    
    
    public boolean esDPIunico(String DPIingresado){
        String tipoUsuario[] ={"Gerente", "Cajero", "Cliente"};
        
        for (int tipoUsuarioActual = 0; tipoUsuarioActual < tipoUsuario.length; tipoUsuarioActual++) {
            Usuario[] usuarios = buscador.buscarUsuario(tipoUsuario[tipoUsuarioActual], "codigo");
             
            if(usuarios!=null){//puesto que es un arreglo, ya que si hubiera sido una lista enlazada no habría problema pues hubiera dado un tamaño 0 xD y listo xD
                
                for(int usuarioActual =0; usuarioActual< usuarios.length; usuarioActual++){
                    if(usuarios[usuarioActual].getDPI().equals(DPIingresado)){
                        return false;
                    }                      
                }
            }                   
        }                               
        return true;
    }    
    
}
