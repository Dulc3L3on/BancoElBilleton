/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Entidades.Objetos.Asociacion;
import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Herramientas.Transformador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Buscador {
    private Connection conexion = ManejadorDB.darConexion();
    private Transformador transformador = new Transformador();        
    private int tipoSituacion;//Este será util para informar que tipo de situación surgió... especialmente cuando pueden suceder más de 2, puesto que solo se puede expresar en una de dos formas..., devolver el obj o devolver nulo, entonces de esta manera se evita la ambigüedad...    
    
    public boolean esTablaLlena(int numeroEntidad){
        String entidades[] = {"Cliente", "Gerente", "Cajero", "Transaccion", "Cuenta"};
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
    
    public Usuario[] buscarUsuarios(String tipo, String tipoOrden){//Este tipo está = que el nombre en la DB
        String buscar ="SELECT * FROM "+ tipo+ " ORDER BY "+ tipoOrden;
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            
             ResultSet resultado = instrucciones.executeQuery();
             if(transformador.colocarseAlPrincipio(resultado)){
                return transformador.transformarAUsuarios(tipo.toLowerCase(), resultado);
             }             
        }catch (SQLException e) {
            System.out.println("Error: al buscar "+ tipo.toUpperCase() +" -> "+e.getMessage());
        }        
        return null;
    }  
    
    public Usuario buscarUsuario(String tipo, String columnaBusqueda, String datoBuscar){//Este tipo está = que el nombre en la DB
        String buscar ="SELECT * FROM "+ tipo+ " WHERE "+ columnaBusqueda +" = "+ datoBuscar;
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            
             ResultSet resultado = instrucciones.executeQuery();
             if(transformador.colocarseAlPrincipio(resultado)){
                return transformador.transformarAUsuario(tipo.toLowerCase(), resultado);
             }             
        }catch (SQLException e) {
            System.out.println("Error: al buscar "+ tipo.toUpperCase() +" -> "+e.getMessage());
        }        
        return null;
    }
    
    public int buscarUsuarioBancaVirtual(){//me dan ganas de solo colocar el 101 como parámetro en el registrador [que está en el gestor de transferencia xD
        String buscar ="SELECT * FROM Cajero WHERE nombre =?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            instrucciones.setString(1, "Banca Virtual");
            
            ResultSet resultado = instrucciones.executeQuery();
            
            resultado.first();
            return resultado.getInt(1);//y así retorno el código xD
        }catch (SQLException e) {
            System.out.println("Error: al buscar al cajero de la Banca Virtual "+e.getMessage());
        }        
        return -1;
    }
    
    public Cuenta[] buscarCuentasDeDueno(int codigoUsuario){
        String buscar = "SELECT * FROM Cuenta WHERE codigoDueno = "+String.valueOf(codigoUsuario);
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){ 
        
            ResultSet resultado = instrucciones.executeQuery();
            if(transformador.colocarseAlPrincipio(resultado)){
                tipoSituacion =1;//todo OK xD
                return transformador.transformarACuentas(resultado);
            }else{
                tipoSituacion = 0;//no tiene ninguna :v aunque eso no debería suceder... pues se debería eliminar el usuario del portal... y del sistema... xD o almenos cb el valor del estado del cliente, para evitar este tipo de confusiones o errores [pero ese atrib en la DB aún no existe...]
            }            
        }catch(SQLException sqlE){
            tipoSituacion =-1;//algo salió mal...
            System.out.println("Error al buscar CUENTAS PROPIAS: "+ sqlE.getMessage());
        }
        return null;
    }
    
    public Cuenta[] buscarCuentasAsociadas(String codigoCliente){
        String buscar = "SELECT * FROM Asociacion WHERE  codigoSolicitante = "+ String.valueOf(codigoCliente)+" OR codigoSolicitado = "+ codigoCliente;
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){             
            
            ResultSet resultado = instrucciones.executeQuery();
            if(transformador.colocarseAlPrincipio(resultado)){
                tipoSituacion = 1;
                return transformador.transformarACuentas(resultado);
            }else{
                tipoSituacion=0;//quiere decir que no tiene cuentas asociadas...
            }            
        }catch(SQLException sqlE){
            System.out.println("Error al buscar CUENTAS ASOCIADAS: "+ sqlE.getMessage());
            tipoSituacion=-1;
        }
        return null;               
    }

    public Cuenta buscarCuenta(String codigoCuenta){
        String buscar ="SELECT * FROM Cuenta WHERE numeroCuenta = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
         
            int cuenta = Integer.parseInt(codigoCuenta);
            
            instrucciones.setInt(1, cuenta);
            
            ResultSet resultado = instrucciones.executeQuery();
            if(transformador.colocarseAlPrincipio(resultado)){
                return transformador.transformarACuenta(resultado);
            }                                   
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al buscar la cuenta: "+e.getMessage());
        }
        return null;        
    }    
    
    public Cliente buscarDuenoDeCuenta(String numeroCuenta){
        String buscar = "SELECT * FROM Cuenta WHERE numeroCuenta = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){ 
            int cuenta = Integer.parseInt(numeroCuenta);
            
            instrucciones.setInt(1, cuenta);
            
            ResultSet resultado = instrucciones.executeQuery();
            if(resultado.first()){
                return (Cliente) buscarUsuario("Cliente", "codigo", String.valueOf(resultado.getInt("codigoDueno")));
            }            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error al buscar el dueño de la cuenta -> "+e.getMessage());
        }
        System.out.println("No existe el número de cuenta ingresado");
        return null;            
    }       

    /**
     * esta será útil par hacer los envíos de solicitudes
     * @param codigoSolicitante
     * @param numeroCuentaSolicitado
     * @return
     */
    public Asociacion[] buscarAsociaciones(int codigoSolicitante, String numeroCuentaSolicitado){
        String buscar = "SELECT * FROM Asociacion WHERE codigoSolicitante = ? AND numeroCuentaSolicitado = ?"
                + " ORDER BY fechaCreacion DESC";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){ 
            int cuentaSolicitada = Integer.parseInt(numeroCuentaSolicitado);
            
            instrucciones.setInt(1, codigoSolicitante);
            instrucciones.setInt(2, cuentaSolicitada);
            
            ResultSet resultado = instrucciones.executeQuery();
            if(transformador.colocarseAlPrincipio(resultado)){
                tipoSituacion =1;//todo salió bien xD
                return transformador.transformarAAsociaciones(resultado);
            }else{//no es necesario por el return, pero para evitar posibles confusiones xD
                tipoSituacion =0;//es decir que no existe ninguna igual a la solicitada
            }                   
        }catch(SQLException | NumberFormatException e){
            System.out.println("Surgió un error al buscar la ASOCIACIÓN ->"+e.getMessage());
            tipoSituacion = -1;
        }        
        return null;
    }    
    
    public Asociacion[] buscarSolicitudes(String tipoSolicitud, String columnaBusqueda, String codigoCliente){
        String buscar ="SELECT * FROM Asociacion WHERE "+ columnaBusqueda +" = ? AND estado = ? ORDER BY fechaCreacion DESC";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            int cliente = Integer.parseInt(codigoCliente);
            
            instrucciones.setInt(1, cliente);
            instrucciones.setString(2, "enEspera");
            
            ResultSet resultado = instrucciones.executeQuery();
            if(transformador.colocarseAlPrincipio(resultado)){
                tipoSituacion =1;
                System.out.println("Tiene solicitudes "+ tipoSolicitud);
                return transformador.transformarAAsociaciones(resultado);
            }else{
                tipoSituacion=0;
                System.out.println("No tiene solicitudes "+tipoSolicitud);//le coloco esto, pues se requiere este método para llenar el listado de las solicitudes respectivas
            }
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al buscar las SOLICITUDES "+ tipoSolicitud.toUpperCase()+": "+ e.getMessage());
            tipoSituacion = -1;
        }
        return null;
    } 
    
   public int darTipoSituacion(){
       return tipoSituacion;
   }
}
