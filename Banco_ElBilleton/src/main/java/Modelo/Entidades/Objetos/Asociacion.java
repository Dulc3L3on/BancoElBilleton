/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Objetos;

import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Asociacion implements Serializable{
    private int codigoSolicitado;
    private String nombreSolicitado;//recuerda que estos datos que tienen métodos para establecer sus valores, existen por el hehcho de que en algunas partes se requiere mostrar a estos datos, que si se agregaran en la tabla para evitar hacer esto, provocaría redundancia en lso datos... y queremos evitar eso xD
    private int numeroCuentaSolicitada;
    private int codigoSolicitante;
    private String nombreSolicitante;
    private String fechaCreacion;
    private String estado;        
    
    public Asociacion(int elCodigoSolicitado, int laCuentaSolicitada, int elCodigoSolicitante,
         String elEstado, String laFechaCreacion){
    
        codigoSolicitado = elCodigoSolicitado;
        numeroCuentaSolicitada = laCuentaSolicitada;
        codigoSolicitante =  elCodigoSolicitante;
        estado = elEstado;
        fechaCreacion = laFechaCreacion;
    }
    
    public int getCodigoSolicitado(){
        return codigoSolicitado;
    }
    
    public void establecerNombreInvolucrado(String tipoInvolucrado, String nombrePersona){
        if(tipoInvolucrado.equals("solicitado")){
           nombreSolicitado = nombrePersona;            
        }else{
            nombreSolicitante = nombrePersona;   
        }//puedo hacer esto sin necesidad de especificar que sea del solicitante, por el hecho de que los datos se los mando YO xD, así que O PROBLEM! XD        
    }
    
    public String getNombreSolicitado(){
        return nombreSolicitado;
    }
    
    public int getNumeroCuentaSolicitada(){
        return numeroCuentaSolicitada;
    }
    
    public int getCodigoSolicitante(){
        return codigoSolicitante;
    }    
    
    public String getNombreSolicitante(){
        return nombreSolicitante;
    }
    
    public String getFechaCreacion(){
        return fechaCreacion;
    }
    
    public String getEstado(){
        return estado;
    }    
    
    public String darNombrePersonaInvolucrada(String tipoSolicitud){//coloco el verbo dar en español, por el hecho de que este método lo emplearé solo yopi xD y no el que se encarga de llenar el JR que NECESITA que el método comience por get y tenga el nombre de la var correspondiente sin importar mayús o minús xD
        if(tipoSolicitud.equalsIgnoreCase("ENVIADAS")){
            return nombreSolicitado;
        }
        return nombreSolicitante;//esto por la estructura de los reportes xD, lo cual es lógico xD        
    }
}
