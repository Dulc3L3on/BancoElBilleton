/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

/**
 *
 * @author phily
 */
public class BuscadorExistencia {
    private Buscador buscador = new Buscador();
    private BuscadorParaReportesCliente buscadorParaReportesCliente = new BuscadorParaReportesCliente();
    
    public boolean poseeCuentas(String codigoDueno){//y deberían ser activas... xD, pero no fue requerido... aún así ya tienes la columna xD
        int tipoSituacion;
        
        try{
            int codigoDelDueno = Integer.parseInt(codigoDueno);
            
            buscador.buscarCuentasDeDueno(codigoDelDueno);
            tipoSituacion = buscador.darTipoSituacion();
            
            if(tipoSituacion == 1){
                return true;
            }    
        }catch(NumberFormatException e){
           System.out.println("Error al buscar la existencia de CUENTAS -> "+ e.getMessage());
        }                                  
        return false;//Sin importar que sea por error o porque en realidad no tiene...
    }
    
   
    public boolean existenSolicitudes(String tipoSolicitud, String columnaBusqueda, String codigoDueno){
        buscador.buscarSolicitudes(tipoSolicitud, columnaBusqueda, codigoDueno);
        int tipoSituacion = buscador.darTipoSituacion();
        
       return (tipoSituacion==1);
    }
}
