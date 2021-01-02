/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Herramientas.Kit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Modificador {
    Connection conexion = ManejadorDB.darConexion();    
    Kit herramienta = new Kit();
    
    public boolean modificarCliente(String datosActualizar[]){
        String modificar="UPDATE Cliente SET nombre = ?, direccion = ?, password = ?,  pathDPI = ?"+((!datosActualizar[8].isBlank() && !datosActualizar[8].isEmpty() && datosActualizar[8]!=null)?", correo = ?":"")+" WHERE codigo = ?";
                
        try(PreparedStatement instrucciones = conexion.prepareStatement(modificar)){
        int codigo = Integer.parseInt(datosActualizar[0]);            
        
            instrucciones.setString(1, datosActualizar[4]);            
            instrucciones.setString(2, datosActualizar[6]);
            instrucciones.setString(3, herramienta.encriptarContrasenia(datosActualizar[7]));            
            instrucciones.setString(4, datosActualizar[5]);
            if(!datosActualizar[8].isEmpty() && !datosActualizar[8].isBlank() && datosActualizar[8]!=null){
                instrucciones.setString(5, datosActualizar[8]);
                instrucciones.setInt(6, codigo);
            }else{
                instrucciones.setInt(5, codigo);
            }            
            
            instrucciones.executeUpdate();
            return true;            
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al actualizar al Cliente -> "+ e.getMessage());
        }
        return false;
    }
    
    public boolean modificarCajero(String datosActualizar[]){
        String modificar ="UPDATE Cajero SET nombre = ?, direccion = ?, password = ?, turno = ?"+((!datosActualizar[7].isBlank() && !datosActualizar[7].isEmpty() && datosActualizar[7]!=null)?", correo = ?":"")+" WHERE codigo = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(modificar)){
            int codigo = Integer.parseInt(datosActualizar[0]);           
           
            instrucciones.setString(1, datosActualizar[3]);
            instrucciones.setString(2, datosActualizar[4]);
            instrucciones.setString(3, herramienta.encriptarContrasenia(datosActualizar[5]));
            instrucciones.setString(4, datosActualizar[6]);            
            if(!datosActualizar[7].isBlank() && !datosActualizar[7].isEmpty() && datosActualizar[7]!=null){
                instrucciones.setString(5, datosActualizar[7]);
                instrucciones.setInt(6, codigo);
            }else{
                instrucciones.setInt(5, codigo);
            }            
            
            instrucciones.executeUpdate();
            return true;            
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al actualizar al Cajero -> "+ e.getMessage());
        }
        return false;
    }
  
    public boolean modificarGerente(String datosActualizar[]){
        String modificar ="UPDATE Gerente SET nombre = ?, direccion = ?, password = ?, turno = ?"+((!datosActualizar[7].isBlank() && !datosActualizar[7].isEmpty() && datosActualizar[7]!=null)?", correo = ?":"")+" WHERE codigo = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(modificar)){
            int codigo = Integer.parseInt(datosActualizar[0]);
            
            instrucciones.setString(1, datosActualizar[1]);
            instrucciones.setString(2, datosActualizar[4]);
            instrucciones.setString(3, herramienta.encriptarContrasenia(datosActualizar[2]));                       
            instrucciones.setString(4, datosActualizar[6]);            
            if(!datosActualizar[7].isBlank() && !datosActualizar[7].isEmpty() && datosActualizar[7]!=null){//se que no podrá ser null por el hecho de haber colocado un input de tipo "email" pero por si xD
                instrucciones.setString(5, datosActualizar[7]);
                instrucciones.setInt(6, codigo);
            }else{
                instrucciones.setInt(5, codigo);
            }    
            
            instrucciones.executeUpdate();
            return true;            
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al actualizar al Gerente -> "+ e.getMessage());
        }
        return false;
    }
    
    public boolean modificarEstadoSolicitud(String reaccion, String codigoSolicitante, String cuentaSolicitada, String fechaCreada){//recuerda, los datos fundamentales para saber de que relación exactamente se está hablando son: el número de cta solicitado y el código del solicitante xD
        String modificar = "UPDATE Asociacion SET estado = ? WHERE codigoSolicitante = ? AND numeroCuentaSolicitado = ? AND fechaCreacion = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(modificar)){
            int solicitante = Integer.parseInt(codigoSolicitante);
            int cuentaSolicitado = Integer.parseInt(cuentaSolicitada);
            
            instrucciones.setString(1, reaccion);
            instrucciones.setInt(2, solicitante);
            instrucciones.setInt(3, cuentaSolicitado);
            instrucciones.setString(4, fechaCreada);
            
            instrucciones.executeUpdate();
            return true;            
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al registrar el "+reaccion+" la SOLICITUD");
        }
        return false;
    }
    
    public boolean modificarLimiteMenor(String tipoLimite, String nuevoLimite){
        String modificar = "UPDATE Setting SET "+ tipoLimite +" = ?";
     
        try(PreparedStatement instrucciones = conexion.prepareStatement(modificar)){
            double menorLimite = Double.parseDouble(nuevoLimite);
            
            instrucciones.setDouble(1, menorLimite);
            
            instrucciones.executeUpdate();
            return true;
        }catch(NumberFormatException | SQLException e){
            System.out.println("Error al modificar el "+tipoLimite +" -> "+e.getMessage());
        }
        return false;//Recuerda que lo que se salta Java al existir una excepción en un try-catch es el cuerpo del try no lo que está fuera de él xD
    }
    
}
