/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Usuarios.Trabajador;
import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Manejadores.DB.Buscador;
import Modelo.Manejadores.DB.BuscadorExistencia;
import Modelo.Manejadores.DB.ManejadorDB;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phily
 */
public class GuardiaSeguridad {
    Buscador buscador = new Buscador();
    BuscadorExistencia buscadorExistencia = new BuscadorExistencia();
    Connection conexion = ManejadorDB.darConexion();
    Usuario usuario;
    Kit herramienta = new Kit();
    
    public boolean estanTodasLlenas(){
        for(int entidadActual =0; entidadActual<5; entidadActual++){
            if(!buscadorExistencia.esTablaLlena(entidadActual)){
                return false;
            }
        }        
        return true;
    }
        
    public boolean esDPIunico(String DPIingresado){
        String tipoUsuario[] ={"Gerente", "Cajero", "Cliente"};
        
        for (int tipoUsuarioActual = 0; tipoUsuarioActual < tipoUsuario.length; tipoUsuarioActual++) {
            Usuario[] usuarios = buscador.buscarUsuarios(tipoUsuario[tipoUsuarioActual], "codigo");
             
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
    
    public boolean esUsuarioAutentico(String nombreUsuario, String contraseniaIngresada, String tipoUsuario){            
        return esContraseniaCorrecta(contraseniaIngresada, esNombreUsuarioCorrecto(nombreUsuario, tipoUsuario));        
    }
    
     private String esNombreUsuarioCorrecto(String codigoUsuario, String tipoUsuario){
        //Se busca al usuario... y se devulven los datos completos para que peuda crearse el obj entidad corresp...
        
        if(tipoUsuario!=null && codigoUsuario!=null){//por si las moscas es ejecutado de forma anormal el proceso...
            usuario = buscador.buscarUsuario(tipoUsuario, "codigo", codigoUsuario);
            if(usuario!=null){
                return usuario.getPassword();
            }            
        }                
        return null;
    }
    
    private boolean esContraseniaCorrecta (String contraseniaIngresada, String contraseniaRegistrada){
        if(contraseniaRegistrada!=null){
            if(contraseniaRegistrada.equals(contraseniaIngresada)){
                return true;
            }                        
        }        
        return false;
    }  
    
    public boolean esPermitidaEstadia(HttpServletRequest request, HttpServletResponse response, String codigo, String tipoUsuario){              
        return (request.getSession().getAttribute("codigo") != null);             
    }//puesto que en algunas páginas [como el Home] de los trabajadores NO DEBO revisar si está en horario o no xD    
    
    public boolean estaEnHorario(String tipoTrabajador, String codigo){
        Trabajador trabajador = (Trabajador) buscador.buscarUsuario(tipoTrabajador, "codigo", codigo);
        
        if((trabajador.getTurno().equals("matutino") && herramienta.darNumeroDeHoraActual() >= 6 && Double.parseDouble(herramienta.darNumeroDeHoraActual()+"."+herramienta.darMinutosActuales()) <=13.30)
            || (trabajador.getTurno().equals("vespertino") && herramienta.darNumeroDeHoraActual() >= 13 && herramienta.darNumeroDeHoraActual() <= 22)){//para las 6 AM 46800000000 para las 13 PM 21600000000
            return true;
        }//Solo debes revisar la hora en milliss para hacerlo más fácil si se pasa o es menor según el tipo de turno correspondiente, eso lo harás en otro método, ahí revisas si haces uno por cada tipo o no xD
        return false;
    }
    
}
