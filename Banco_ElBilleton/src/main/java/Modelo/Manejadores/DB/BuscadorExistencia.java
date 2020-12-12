/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class BuscadorExistencia {
    private Connection conexion = ManejadorDB.darConexion();
    private Buscador buscador = new Buscador();
    private BuscadorParaReportesCliente buscadorParaReportesCliente = new BuscadorParaReportesCliente();
    private BuscadorParaReportesTrabajador buscadorParaReportesTrabajador = new BuscadorParaReportesTrabajador();
    
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
    
    public boolean aAtendidoTransacciones(String codigoCajero){
        String buscar = "SELECT * FROM Transaccion WHERE codigoCajero = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            int codigoCajeroACargo = Integer.parseInt(codigoCajero);
            
            instrucciones.setInt(1, codigoCajeroACargo);
            
            ResultSet resultado = instrucciones.executeQuery();
            return resultado.first();                
        }catch(NumberFormatException | SQLException e){
            System.out.println("Error al buscar la EXISTENCIA de transacciones ATENDIDAS -> "+e.getMessage());
            return false;//por si llegara a fallar la búsqueda xD
        }        
    }
}
