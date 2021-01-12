/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Objetos.Cambio;
import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Entidades.Usuarios.Trabajador;
import Modelo.Entidades.Usuarios.Usuario;
import Modelo.ListaEnlazada;
import Modelo.Manejadores.DB.Buscador;
import Modelo.Nodo;


/**
 *
 * @author phily
 */
public class CuerpoEmail {
  //los datos para llenar el cuerpo lo recibirá desde el gestor... quiere decir que también estará aquí presente elrequest y response xD                 
    public String darCuerpoPorCreacion(String tipoUsuario, Usuario usuario){
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
         System.out.println("se creo el cuerpo?"+cuerpo.contains("DOCTYPE"));
        return cuerpo;              
    }
    
    public String darCuerpoPorModificacion(ListaEnlazada<Cambio> listadoCambios){       
           String cuerpo = "<!DOCTYPE html>"
                      + "<html>"
                            + "<head>"
                                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                                + "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">"
                                + "<link href=\"https://fonts.googleapis.com/css2?family=Montserrat:wght@300&display=swap\" rel=\"stylesheet\">"
                                + "<style type=\"text/css\">"
                                    + "body {  font-family: Montserrat; }"                                    
                                    + "#subtitulos{ background-color: #ECECEC; width: 135px; }"
                                    + "table{  text-align: left; }"                                                                         
                                + "</style>"
                            + "</head>"
                            + "<body>"
                            + "     <table>"
                            + "         <tr>"
                            + "             <th id=\"subtitulos\"><h4>Hora</h4></th>"
                            + "             <th id=\"subtitulos\"><h4>Tipo</h4></th>"
                            + "             <th id=\"subtitulos\"><h4>Dato Antiguo</h4></th>"
                            + "             <th id=\"subtitulos\"><h4>Reemplazo</h4></th>"
                            + "         </tr>";                                                                             
        
           Nodo<Cambio> nodoAuxiliar = listadoCambios.obtnerPrimerNodo();
        for (int datoActual = 0; datoActual < listadoCambios.darTamanio(); datoActual++) {
            cuerpo+="<tr>" +
                    "   <th><h5>"+nodoAuxiliar.contenido.getHora()+"</h5>" +
                    "   </th>" +
                    "   <th><h5>"+nodoAuxiliar.contenido.getTipo()+"</h5>" +
                    "   </th>" +
                    "   <th><h5>"+nodoAuxiliar.contenido.getDatoAntiguo()+"</h5>" +
                    "   </th>" +
                    "   <th><h5>"+nodoAuxiliar.contenido.getDatoNuevo()+"</h5>" +
                    "   </th>" +
                    "</tr>";
            nodoAuxiliar = nodoAuxiliar.nodoSiguiente;            
        }
        
        cuerpo+= "         </table>"
                + "    </body>"
                + "</html>";
        
        return cuerpo;
    }
    
    public String darCuerpoPorCreacionCuenta(int codigoCliente){
        Buscador buscador = new Buscador();
        Cuenta[] cuentas = buscador.buscarCuentasDeDueno(codigoCliente);
        String cuerpo = null;
        
        if(cuentas!=null){
            cuerpo ="<!DOCTYPE html>"
                      + "<html>"
                            + "<head>"
                                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                                + "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">"
                                + "<link href=\"https://fonts.googleapis.com/css2?family=Montserrat:wght@300&display=swap\" rel=\"stylesheet\">"
                                + "<style type=\"text/css\">"
                                    + "body {  font-family: Montserrat; }"                                    
                                    + "#subtitulos{ background-color: #ECECEC; width: 135px; }"
                                    + "table{  text-align: left; }"                                                                         
                                + "</style>"
                            + "</head>"
                            + "<body>"
                            + "     <table>"
                            + "         <tr>"
                            + "             <th id=\"subtitulos\"><h4>Fecha de Creación</h4></th>"
                            + "             <th id=\"subtitulos\"><h4>No. Cuenta</h4></th>"
                            + "             <th id=\"subtitulos\"><h4>Tipo</h4></th>"
                            + "             <th id=\"subtitulos\"><h4>Estado</h4></th>"
                            + "         </tr>";     
        
    
            for (int cuentaActual = 0; cuentaActual < cuentas.length; cuentaActual++) {
             cuerpo+="<tr>" +
                    "   <th><h5>"+cuentas[cuentaActual].getFechaCreacion()+"</h5>" +
                    "   </th>" +
                    "   <th><h5>"+cuentas[cuentaActual].getNumeroCuenta()+"</h5>" +
                    "   </th>" +
                    "   <th><h5>"+cuentas[cuentaActual].getTipo()+"</h5>" +
                    "   </th>" +
                    "   <th><h5>"+cuentas[cuentaActual].getEstado()+"</h5>" +
                    "   </th>" +
                    "</tr>";
            }
            
          cuerpo+= "         </table>"
                + "    </body>"
                + "</html>";
        }
        
       return cuerpo;
    }
    
    public String darCuerpoPorTransferencia(String nombreDepositante, String numeroCuentaAfectada, String cantidadAcreditada){
        String cuerpo = "<!DOCTYPE html>"
                      + "<html>"
                            + "<head>"
                                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
                                + "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">"
                                + "<link href=\"https://fonts.googleapis.com/css2?family=Montserrat:wght@300&display=swap\" rel=\"stylesheet\">"
                                + "<style type=\"text/css\">"
                                    + "body {  font-family: Montserrat; }"                                                                                                     
                                    + "input{  border: none; }"    
                                    + "table{  text-align: left; }" 
                                + "</style>"
                            + "</head>"
                            + "<body>"
                                + "     <h3>----------->>>Transferencia Efectuada<<<----------</h3>"
                                + "<table>"
                                + "     <tr>"
                                + "         <th><h4>Nombre del depositante: </h4></th>"
                                + "         <th><input type=\"text\" value=\""+nombreDepositante+"\"></th>"
                                + "     </tr>"
                                + "     <tr>"
                                + "         <th><h4>No. cuenta Receptora: </h4></th>"
                                +"          <th><input type=\"text\" value=\""+numeroCuentaAfectada+"\"></th>"
                                + "     </tr>"
                                + "     <tr>"
                                + "         <th><h4>Monto Q. </h4></th>"
                                + "         <th><input type=\"text\" value=\""+cantidadAcreditada+"\"></th>"                                
                                + "     </tr>"
                                + "</table>"
                            + " </body>"
                            + "</html>";                                                
        return cuerpo;
    }
}
