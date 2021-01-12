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
    private int numeroClientes;
    
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
    
    public List<Usuario> buscarUsuariosNoGerentes(){        
        List<Usuario> listadoUsuarios =  new LinkedList<>();
        
        for (int usuarioBuscado = 0; usuarioBuscado < 2; usuarioBuscado++) {//también hubiera podido hacer un método que buscara todos los usuarios que no fueran gerentes pero no tengo una tabla para todos los usuarios sino una para cada tipo de usuarios...
            Usuario[] usuarios = buscador.buscarUsuarios((usuarioBuscado==0)?"Cliente":"Cajero", "nombre");            
            
            if(usuarios!=null){
                if(usuarioBuscado==0){
                    numeroClientes= usuarios.length;
                }
                
                for (int usuarioActual = 0; usuarioActual < usuarios.length; usuarioActual++) {            
                    listadoUsuarios.add(usuarios[usuarioActual]);
                }                             
            }        
        }                    
        return listadoUsuarios;
    }//the end xD
     
    public double buscarLimiteMinimoReportesTransacciones(String columnaBusqueda){
        String buscar = "SELECT "+columnaBusqueda+" FROM Setting";//lo que hicieron para evitar las query inyection es, permitir el ingreso de datos directamente por medio de la varibable al string que se introduce al prepared statement [o solamente statement xD creo xD] correspondientes a nombres de columnas es decir que todoa quello que se ingrese directametne en el string "query" nombres o valores de columnas y no directamente las especificaciones de lo que se quiere obtener
            
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
        
            ResultSet resultado = instrucciones.executeQuery();
            if(resultado.first()){
                return resultado.getDouble(1);
            }else{
                return 0;
            }            
        }catch(SQLException sqlE){
            System.out.println("Error al buscar el "+columnaBusqueda);
        }
        return -1;
    }       
    
    public int darTipoSituacion(){
        return tipoSituacion;
    }
    
    public int darNumeroDeClientes(){
        return numeroClientes;
    }
    
}
