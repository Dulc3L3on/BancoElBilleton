/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Objetos.Asociacion;

/**
 *
 * @author phily
 */
public class Analizador {
    private int intentos =0;//no es necesario reiniciarlo en el método puesto que cad vez que se busca se está pasando una nueva instancia de este analizador y por lo tanto todo vuelve como al principio...
    private String tipoSituacion;
    
    public boolean analizarSituacionSolicitudes(Asociacion[] asociaciones, int situacionBuscador){                       
        if(situacionBuscador>-1){
            if(asociaciones!=null){//esto es para descartar el hecho de que no se haya generado antes una solicitud de aso xD
                intentos = asociaciones.length;
                
                if(asociaciones[asociaciones.length-1].getEstado().equals("aceptada")){
                    tipoSituacion = "aceptada";
                    return false;
                }else if(asociaciones[asociaciones.length-1].getEstado().equals("rechazada") && asociaciones.length==3){
                    tipoSituacion = "agotadas";
                    return false;
                }else if(asociaciones[asociaciones.length-1].getEstado().equals("enEspera")){
                    tipoSituacion = "sinReaccion";
                    return false;
                }//hago todo esto así y no lo agrupo para que solo exista un retorno false y true, por los msje informativos...
            }
            return true;//puesto que puede que no existan solicitudes enviadas para esa realción [aso == null], el estado no sea aceptado [se rechazado y los intentos <3]            
        }
        return false;
    }  
  
    public int darNumeroIntentos(){
        return intentos;
    }
    
    public String darTipoSituacion(){
        return tipoSituacion;
    }
}
