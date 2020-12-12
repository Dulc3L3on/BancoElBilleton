/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Herramientas.TransformadorParaReportes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author phily
 */
public class BuscadorParaReportesTrabajador {
    private Connection conexion = ManejadorDB.darConexion();
    private TransformadorParaReportes transformadorParaReportes = new TransformadorParaReportes();
    private Buscador buscador = new Buscador();
    private int tipoSituacion;
    
    public List<Transaccion> buscarTransaccionesAtendidas(String codigoCajero, String fechaInicial, String fechaFinal){
        List<Transaccion> transaccionesAtendidas = new LinkedList<>();
        String buscar = "SELECT * FROM Transaccion WHERE codigoCajero = ? AND fecha BETWEEN ? AND ?";
                
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            int codigoCajeroACargo = Integer.parseInt(codigoCajero);
            
            instrucciones.setInt(1, codigoCajeroACargo);
            instrucciones.setString(2, fechaInicial);//al parecer [esto lo digo por lo que uscedió con los rep del cliente cuando se le manda una fecha null a la condi con el between [y supongo que a cualquier otra] busca sin tener un límite para el dato que tenía como valor NULL...
            instrucciones.setString(3, fechaFinal);
            
            ResultSet resultado = instrucciones.executeQuery();
            if(transformadorParaReportes.colocarseAlPrincipio(resultado)){
                tipoSituacion =1;
                return transformadorParaReportes.transformarATransacciones(resultado);
            }else{//bueno, aunque pensándolo bien, este else no debería estar aquí... por el hecho de que si tenía algun dato entrará al if y como ahí hay un return, no llegará aquí y si tuviera algun fallo la búsqueda se iría directamente al catch... pero creo que si no lo colocara colocaría un msje en la interfaz java, en el que diría que nunca se llegaría ahí... pero si lo piensas, si se llegaría ahí, por el tipo de situación...
                tipoSituacion = 0;
            }            
        }catch(SQLException | NumberFormatException e){
            tipoSituacion = -1;
            System.out.println("Error al buscar las transacciones TRABAJADAS -> "+ e.getMessage());
        }        
        return transaccionesAtendidas;
    }
    
    public void buscarDuenoDeCuenta(List<Transaccion> transaccionesAtendidas){
        Usuario duenoDeCuenta = null;
        
        if(transaccionesAtendidas!=null){//aunque, esto jamás pasaría porque el método que las halla solo es capaz de devolver una lista llena o vacía, mas no nula...
        for (int transaccionActual = 0; transaccionActual < transaccionesAtendidas.size(); transaccionActual++) {
                duenoDeCuenta = buscador.buscarDuenoDeCuenta(String.valueOf(transaccionesAtendidas.get(transaccionActual).getNumeroCuentaAfectada()));
            
                if(duenoDeCuenta!=null){
                    transaccionesAtendidas.get(transaccionActual).establecerDuenoCuentaAfectada(duenoDeCuenta.getNombre());
                }else{
                    transaccionesAtendidas.get(transaccionActual).establecerDuenoCuentaAfectada("???");//con tal que sepan que surgió un error, posiblemente ya no exista el gerente... pero no tendría que eliminarse los cambios realizados por el gerente que ya no es trabajador, para evitar este problema... y eso fue lo que se hizo xD, por el hecho de haber establecido el NO ACTION al borrar xD
                }                
            }
        }               
    }//Para el caso del cajero, no es necesario hacer la búsqueda del nombre del cajero a cargo, puesto que el listado es del cajero que atendió las transacciones, así que... :v xD
    
    public int darTipoSituacion(){
        return tipoSituacion;
    }
    
}
