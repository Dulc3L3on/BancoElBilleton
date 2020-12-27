/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Usuarios;

import Modelo.Entidades.Objetos.Cambio;
import Modelo.Herramientas.Kit;
import Modelo.ListaEnlazada;
import Modelo.Manejadores.DB.Registrador;

/**
 *
 * @author phily
 */
public class Gerente extends Trabajador{
    private Kit herramienta = new Kit();
    private Registrador registrador = new Registrador();
    
    public Gerente(int elCodigo, String elNombre, String elDPI, String laDireccion, String elGenero, String elPassword, String laFechaIncorporacion, String elCorreo, String elTurno) {
        super(elCodigo, elNombre, elDPI, laDireccion, elGenero, elPassword, laFechaIncorporacion, elCorreo, elTurno);
    }
    
    public void hallarCambiosCajero(Cajero cajeroAntiguo, String datosNuevos[]){                
      if(!cajeroAntiguo.nombre.equals(datosNuevos[3])){
            registrador.registrarCambioClienteCajero("Cajero", codigo, "nombre", cajeroAntiguo.nombre, datosNuevos[3], cajeroAntiguo.codigo);
        }       
        if(!cajeroAntiguo.direccion.equals(datosNuevos[4])){
            registrador.registrarCambioClienteCajero("Cajero", codigo, "direccion", cajeroAntiguo.direccion, datosNuevos[4], cajeroAntiguo.codigo);
        }
        if(!cajeroAntiguo.password.equals(datosNuevos[5])){
            registrador.registrarCambioClienteCajero("Cajero", codigo, "contraseña", cajeroAntiguo.password, datosNuevos[5], cajeroAntiguo.codigo);
        } 
        if(!cajeroAntiguo.turno.equals(datosNuevos[6])){
            registrador.registrarCambioClienteCajero("Cajero", codigo, "turno", cajeroAntiguo.turno, datosNuevos[6], cajeroAntiguo.codigo);
        }
        if(!datosNuevos[7].isBlank() && !datosNuevos[7].isEmpty() && datosNuevos[7]!=null){
            if(!cajeroAntiguo.correo.equals(datosNuevos[7])){
                registrador.registrarCambioClienteCajero("Cajero", codigo, "correo", cajeroAntiguo.correo, datosNuevos[8], cajeroAntiguo.codigo);
            }
        }
        
    }
    
    public void hallarCambiosCliente(Cliente clienteAntiguo, String datosNuevos[]){               
        if(!clienteAntiguo.nombre.equals(datosNuevos[4])){
            registrador.registrarCambioClienteCajero("Cliente", codigo, "nombre", clienteAntiguo.nombre, datosNuevos[4], clienteAntiguo.codigo);
        }
        if(!clienteAntiguo.getPathDPI().equals(datosNuevos[5])){
            registrador.registrarCambioClienteCajero("Cliente", codigo, "archivo DPI", clienteAntiguo.getPathDPI(), datosNuevos[5], clienteAntiguo.codigo);
        }
        if(!clienteAntiguo.direccion.equals(datosNuevos[6])){
            registrador.registrarCambioClienteCajero("Cliente", codigo, "direccion", clienteAntiguo.direccion, datosNuevos[6], clienteAntiguo.codigo);
        }
        if(!clienteAntiguo.password.equals(datosNuevos[7])){
            registrador.registrarCambioClienteCajero("Cliente", codigo, "contraseña", clienteAntiguo.password, datosNuevos[7], clienteAntiguo.codigo);
        }  
        if(!datosNuevos[8].isBlank() && !datosNuevos[8].isEmpty() && datosNuevos[8]!=null){
            if(!clienteAntiguo.correo.equals(datosNuevos[8])){
                registrador.registrarCambioClienteCajero("Cliente", codigo, "correo", clienteAntiguo.correo, datosNuevos[8], clienteAntiguo.codigo);
            }
        }
    }
    
    public void hallarCambiosPropios(String datosNuevos[]){             
      if(!nombre.equals(datosNuevos[1])){
            registrador.registrarCambioGerente(codigo, "nombre", nombre, datosNuevos[1]);
        }       
        if(!direccion.equals(datosNuevos[4])){
            registrador.registrarCambioGerente(codigo, "direccion", direccion, datosNuevos[4]);
        }
        if(!password.equals(datosNuevos[2])){
            registrador.registrarCambioGerente(codigo, "contraseña", password, datosNuevos[2]);
        } 
        if(!turno.equals(datosNuevos[6])){
            registrador.registrarCambioGerente(codigo, "turno", turno, datosNuevos[6]);
        }
        if(!datosNuevos[7].isBlank() && !datosNuevos[7].isEmpty() && datosNuevos[7]!=null){
            if(!correo.equals(datosNuevos[7])){
                registrador.registrarCambioGerente(codigo, "correo", correo, datosNuevos[7]);
            }
        }
        
    }
    
     public ListaEnlazada<ListaEnlazada<Cambio>> darListaListados(){
        ListaEnlazada<ListaEnlazada<Cambio>> listaListados = registrador.darListaDeListados();        
        return listaListados;
    }//NOTA: si te llega a dar problemas el hecho de no limpiar los listados la respuesta está en crear el registrador en cada uno de los métodos donde se verifican los cambios... de tal manera que no existan los caumulados... pero si mal no recuerdo no daba este problema en la versión anteiror y si es así quiere decir que cada vez que hago al gerente del gestor de modificación [de todos si mal no recuerdo xd( auqneu de esto me di cta al trabajajar en la modif del cajero...)] igual al valor del buscador estoy instanciando un nuevo objeto Oooooh! fíjate bien, porque si es así... NO lo sabía xD
    
    
     
}

/*<%=(pagina.startsWith("Reportes")?"marcadoGerente":"")%> ya no xD porque se mira con muuuuchos colores... xD
  <%=(pagina.startsWith("Modificacion")?"marcadoGerente":"")%>
  <%=(pagina.startsWith("Creacion")?"marcadoGerente":"")%>
*/