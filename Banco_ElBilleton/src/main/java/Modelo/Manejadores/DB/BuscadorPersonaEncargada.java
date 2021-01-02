/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Entidades.Objetos.Asociacion;
import Modelo.Entidades.Objetos.Cambio;
import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Entidades.Usuarios.Usuario;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author phily
 */
public class BuscadorPersonaEncargada {
    private Buscador buscador = new Buscador();
    
    public void buscarGerenteACargo(List<Cambio> listadoCambios){//será empleado luego de hacer la búsqueda de los respectivos cambios...
        Usuario usuarioGerente = null;                
        
        for (int cambioActual = 0; cambioActual < listadoCambios.size(); cambioActual++){
            usuarioGerente = buscador.buscarUsuario("Gerente", "codigo", String.valueOf(listadoCambios.get(cambioActual).getCodigoGerenteACargo()));
            
            if(usuarioGerente!=null){
                listadoCambios.get(cambioActual).establecerNombreGerenteACargo(usuarioGerente.getNombre());                                    
            }else{
                listadoCambios.get(cambioActual).establecerNombreGerenteACargo("???");//con tal que sepan que surgió un error, posiblemente ya no exista el gerente... pero no tendría que eliminarse los cambios realizados por el gerente que ya no es trabajador, para evitar este problema... y eso fue lo que se hizo xD, por el hecho de haber establecido el NO ACTION al borrar xD
            }                
        }                    
    }//Este es para el GERENTE...

    /**
     * Este será para completar los datos de los cambios...
     * @param cambios
     * @return
     */
    public List<Cambio> buscarNombresGerenteACargo(Cambio[] cambios){
        Usuario usuarioGerente = null;
        List<Cambio> listaCambios = new LinkedList<>();
        
        if(cambios!=null){
            for (int cambioActual = 0; cambioActual < cambios.length; cambioActual++){
                usuarioGerente = buscador.buscarUsuario("Gerente", "codigo", String.valueOf(cambios[cambioActual].getCodigoGerenteACargo()));
            
                if(usuarioGerente!=null){
                    cambios[cambioActual].establecerNombreGerenteACargo(usuarioGerente.getNombre());                                    
                }else{
                    cambios[cambioActual].establecerNombreGerenteACargo("???");//con tal que sepan que surgió un error, posiblemente ya no exista el gerente... pero no tendría que eliminarse los cambios realizados por el gerente que ya no es trabajador, para evitar este problema... y eso fue lo que se hizo xD, por el hecho de haber establecido el NO ACTION al borrar xD
                }
                listaCambios.add(cambios[cambioActual]);                
            }            
        }        
        return listaCambios;
    }//Este es para el Cliente [y es el que se ejecuta al primicpio... creo que para eso era el # 1 que tenía xD
    
     //NOTA: Si lso datos de los listado que se reciben en estos métodos que buscan los nombres, se hacen en el mismo servlet.... es mejor que se haga aquí y no que se coloque la búsqueda en los parámetros decada uno de estos métodos...
    public List<Transaccion> buscarNombreCajeroACargo(List<Transaccion> transacciones){
        Usuario usuarioCajero = null;               
        
        if(transacciones!=null){//bueno, si está vacía no habrá problema, puesto que dará un tamaño = 0, pero si está nula ahí si...                                            
            for (int transaccionActual = 0; transaccionActual < transacciones.size(); transaccionActual++) {
                if(transacciones.get(transaccionActual).getCodigoCajeroACargo() != 101){
                   usuarioCajero = buscador.buscarUsuario("Cajero", "codigo", String.valueOf(transacciones.get(transaccionActual).getCodigoCajeroACargo()));
                   
                   if(usuarioCajero!=null){
                    transacciones.get(transaccionActual).establecerNombreCajeroACargo(usuarioCajero.getNombre());
                   }else{
                      transacciones.get(transaccionActual).establecerNombreCajeroACargo("???");//con tal que sepan que surgió un error, posiblemente ya no exista el gerente... pero no tendría que eliminarse los cambios realizados por el gerente que ya no es trabajador, para evitar este problema... y eso fue lo que se hizo xD, por el hecho de haber establecido el NO ACTION al borrar xD
                   }
                }else{
                    transacciones.get(transaccionActual).establecerNombreCajeroACargo("Banca Virtual");
                }                      
            }
        }        
        return transacciones;//no es necesario devolver la lista puesto que es "por REFERENCIA" y NO por valor...
    }//2 También es empleado por el Cliente...
    
    public List<Asociacion> buscarNombrePersonaInvolucrada(String tipoInvolucrado, Asociacion[] solicitudes){//en el parámetro de este método se 
        Usuario usuarioInvolucrado = null; 
        List<Asociacion> listaAsociaciones = new LinkedList<>();
     
        if(solicitudes!=null){
            for (int solicitudActual = 0; solicitudActual < solicitudes.length; solicitudActual++) {
                usuarioInvolucrado = buscador.buscarUsuario("Cliente", "codigo",String.valueOf(tipoInvolucrado.equals("solicitado")?solicitudes[solicitudActual].getCodigoSolicitado():solicitudes[solicitudActual].getCodigoSolicitante()));
            
                if(usuarioInvolucrado!=null){
                     solicitudes[solicitudActual].establecerNombreInvolucrado(tipoInvolucrado, usuarioInvolucrado.getNombre());
                }else{
                    solicitudes[solicitudActual].establecerNombreInvolucrado(tipoInvolucrado, "???");//con tal que sepan que surgió un error, posiblemente ya no exista el gerente... pero no tendría que eliminarse los cambios realizados por el gerente que ya no es trabajador, para evitar este problema... y eso fue lo que se hizo xD, por el hecho de haber establecido el NO ACTION al borrar xD
                }
                
                listaAsociaciones.add(solicitudes[solicitudActual]);
            }
        }        
       return listaAsociaciones;        
     }//También es empleado por el cliente xD
    
    public void buscarDuenoDeCuenta(List<Transaccion> transaccionesAtendidas){
        Usuario duenoDeCuenta = null;
        
        if(transaccionesAtendidas!=null){//aunque, esto jamás pasaría porque el método que las halla solo es capaz de devolver una lista llena o vacía, mas no nula...
        for (int transaccionActual = 0; transaccionActual < transaccionesAtendidas.size(); transaccionActual++) {
                duenoDeCuenta = buscador.buscarDuenoDeCuenta(String.valueOf(transaccionesAtendidas.get(transaccionActual).getNumeroCuentaAfectada()));            
                
                transaccionesAtendidas.get(transaccionActual).establecerClienteDuenoDeCuenta((Cliente)duenoDeCuenta);               
            }
        }               
    }//Este es empleado por el Cajero
}
