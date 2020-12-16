/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Entidades.Objetos.Cambio;
import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Herramientas.Transformador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author phily
 */
public class BuscadorExistencia {
    private Connection conexion = ManejadorDB.darConexion();
    private Buscador buscador = new Buscador();
    private BuscadorParaReportesCliente buscadorParaReportesCliente = new BuscadorParaReportesCliente();
    private BuscadorParaReportesTrabajador buscadorParaReportesTrabajador = new BuscadorParaReportesTrabajador();
    private BuscadorParaReportesGerente buscadorParaReporetesGerente = new BuscadorParaReportesGerente();
    private Transformador transformador = new Transformador();
    
     public boolean esTablaLlena(int numeroEntidad){
        String entidades[] = {"Cliente", "Gerente", "Cajero", "Transaccion", "Cuenta", "Cambios_Cajero", "Cambios_Cliente"};
        String buscar = "SELECT * FROM "+entidades[numeroEntidad];
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            ResultSet resultado = instrucciones.executeQuery();            
            
           if(resultado.first()){
               return true;
           }            
        }catch(SQLException sqlE){
            System.out.println("Error al verificar llenura de " + entidades[numeroEntidad] + " "+ sqlE.getMessage());
        }
        return false;
    }
    
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
    
    public Cuenta[] buscarTodasLasCuentasExistentes(){
        String buscar ="SELECT * FROM Cuenta";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
        
            ResultSet resultado = instrucciones.executeQuery();
            if(transformador.colocarseAlPrincipio(resultado)){
                return transformador.transformarACuentas(resultado);
            }                       
        }catch(SQLException sqlE){
            System.out.println("Error al buscar todos los números de CUENTAS EXISTENTES -> "+ sqlE.getMessage());
        }
        return null;
    }
    
    public boolean haRealizadoCambiosPropios(String codigoGerente){
        List<Cambio> listadoCambios = buscadorParaReporetesGerente.buscarCambios("Gerente", codigoGerente);            
        return (!listadoCambios.isEmpty());  
    }
    
    public boolean haRealizadoOtrosCambios(){
        for (int usuario = 5; usuario < 7; usuario++) {
            if(!esTablaLlena(usuario)){
                return false;
            }
        }
        return true;        
    }//no me será útil puesto que puede que no existan cb en el cliente pero eso no tendría que afectar en el hecho de hallar el reporte de los cajero quienes probablemente sí tengan regsistros...
    
    public boolean existenTransacciones(){
        return (esTablaLlena(4));
    }
}
