/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Entidades.Usuarios.Trabajador;
import Modelo.Entidades.Usuarios.Usuario;


/**
 *
 * @author phily
 */
public class CuerpoEmail {
  //los datos para llenar el cuerpo lo recibirá desde el gestor... quiere decir que también estará aquí presente elrequest y response xD           
    public String darCuerpo(Usuario usuario, String tipoCuerpo, String tipoUsuario){
        switch(tipoCuerpo){
            case"resultadoCreacion":
                return darCuerpoPorCreacion(tipoUsuario, usuario);
                
        }
        return null;//pero nunca llegará aquí porque los cuerpos los da mua xD
    }
    
    private String darCuerpoPorCreacion(String tipoUsuario, Usuario usuario){
        String cuerpo = "<!DOCTYPE html>"
                      + "<html>"
                            + "<head>"
                                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                                + "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">"
                                + "<link href=\"https://fonts.googleapis.com/css2?family=Montserrat:wght@300&display=swap\" rel=\"stylesheet\">"
                                + "<style type=\"text/css\">"
                                    + "body {  font-family: Montserrat; }"
                                    + "input{ color: #575656; border: none; background: transparent;}"
                                    + "#subtitulos{ background-color: #C7D4D8; width: 119px; }"
                                    + "table{  text-align: left; }"                                    
                                    + "#contenido{ background-color: #ECECEC; }"      
                                + "</style>"
                            + "</head>"
                            + "<body>"
                                  + "<table>"
                                        + "<tr>"
                                            + "<th id=\"subtitulos\"><h3><strong>Código:</strong></h3></th>"
                                            + "<th id=\"contenido\"><input type=\"text\" value=\""+usuario.getCodigo()+"\" readonly></th>"                                        
                                            + "<th id=\"subtitulos\"><h3><strong>Nombre:</strong></h3></th>"
                                            + "<th id=\"contenido\"><input type=\"text\" value=\""+usuario.getNombre()+"\" readonly></th>"
                                        + "</tr>"
                                        + "<tr>"
                                            + "<th id=\"subtitulos\"><h3><strong>Password:</strong></h3></th>"
                                            + "<th id=\"contenido\"><input type=\"text\" value=\""+usuario.getPassword()+"\" readonly></th>"                                        
                                            + "<th id=\"subtitulos\"><h3><strong>DPI:</strong></h3></th>"
                                            + "<th id=\"contenido\"><input type=\"text\" value=\""+usuario.getDPI()+"\" readonly></th>"
                                        + "</tr>"
                                        + "<tr>"
                                            + "<th id=\"subtitulos\"><h3><strong>Dirección:</strong></h3></th>"
                                            + "<th id=\"contenido\"><input type=\"text\" value=\""+usuario.getDireccion()+"\" readonly></th>"                                        
                                            + "<th id=\"subtitulos\"><h3><strong>Género:</strong></h3></th>"
                                            + "<th id=\"contenido\"><input type=\"text\" value=\""+usuario.getSexo()+"\" readonly></th>"
                                        + "</tr>"
                                        + "<tr>"
                                            + "<th id=\"subtitulos\"><h3><strong>Correo:</strong></h3></th>"
                                            + "<th id=\"contenido\"><input type=\"text\" value=\""+usuario.getCorreo()+"\" readonly></th>"                                        
                                            + "<th id=\"subtitulos\"><h3><strong>"+((tipoUsuario.equals("Cliente"))?"Cumpleaños:":"Turno:")+"</strong></h3></th>";
                        
                if(tipoUsuario.equals("Cliente")){//aunque pensándolo bien xD es algo absurdo que se muestre el correo agergado xD, pues por eso le calló a ese correo xD
                    Cliente cliente = (Cliente) usuario;
                    
                    cuerpo+= "<th id=\"contenido\"><input type=\"text\" value=\""+cliente.getBirth()+"\" readonly></th>"
                                        + "</tr>"
                                  + "</table>"
                            + "</body>"
                      + "</html>";        
                }else{
                    Trabajador trabajador = (Trabajador) usuario;
                    
                    cuerpo+= "<th id=\"contenido\"><input type=\"text\" value=\""+trabajador.getTurno()+"\" readonly></th>"
                                        + "</tr>"
                                  + "</table>"
                            + "</body>"
                      + "</html>";        
                }                        
        return cuerpo;
    }
    
}
