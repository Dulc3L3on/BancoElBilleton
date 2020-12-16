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
        String modificar="INSERT INTO Cliente (codigo, nombre, DPI, direccion, sexo, password, birth, pathDPI)"
                + "VALUES (?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE nombre = ?, direccion = ?, password = ?, birth = ?, pathDPI = ?";
                
        try(PreparedStatement instrucciones = conexion.prepareStatement(modificar)){
        int codigo = Integer.parseInt(datosActualizar[0]);
            
            instrucciones.setInt(1, codigo);
            instrucciones.setString(2, datosActualizar[4]);
            instrucciones.setString(3, datosActualizar[1]);
            instrucciones.setString(4, datosActualizar[6]);
            instrucciones.setString(5, datosActualizar[2]);
            instrucciones.setString(6, herramienta.encriptarContrasenia(datosActualizar[7]));
            instrucciones.setString(7, datosActualizar[3]);
            instrucciones.setString(8, datosActualizar[5]);//datos con posible actualización...
            instrucciones.setString(9, datosActualizar[4]);            
            instrucciones.setString(10, datosActualizar[6]);
            instrucciones.setString(11, herramienta.encriptarContrasenia(datosActualizar[7]));
            instrucciones.setString(12, datosActualizar[3]);
            instrucciones.setString(13, datosActualizar[5]);
            
            instrucciones.executeUpdate();
            return true;            
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al actualizar al Cliente -> "+ e.getMessage());
        }
        return false;
    }
    
    public boolean modificarCajero(String datosActualizar[]){
        String modificar ="INSERT INTO Cajero (codigo, nombre, DPI, direccion, sexo, password, turno)"
                + "VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE nombre = ?, direccion = ?, password = ?, turno = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(modificar)){
            int codigo = Integer.parseInt(datosActualizar[0]);
            
            instrucciones.setInt(1, codigo);
            instrucciones.setString(2, datosActualizar[3]);
            instrucciones.setString(3, datosActualizar[1]);
            instrucciones.setString(4, datosActualizar[4]);
            instrucciones.setString(5, datosActualizar[2]);
            instrucciones.setString(6, herramienta.encriptarContrasenia(datosActualizar[5]));
            instrucciones.setString(7, datosActualizar[6]);//datos con posible actualización...
            instrucciones.setString(8, datosActualizar[3]);
            instrucciones.setString(9, datosActualizar[4]);
            instrucciones.setString(10, herramienta.encriptarContrasenia(datosActualizar[5]));
            instrucciones.setString(11, datosActualizar[6]);
            
            instrucciones.executeUpdate();
            return true;            
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al actualizar al Cajero -> "+ e.getMessage());
        }
        return false;
    }
  
    public boolean modificarGerente(String datosActualizar[]){
        String modificar ="INSERT INTO Gerente (codigo, nombre, DPI, direccion, sexo, password, turno)"
                + "VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE nombre = ?, direccion = ?, password = ?, turno = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(modificar)){
            int codigo = Integer.parseInt(datosActualizar[0]);
            
            instrucciones.setInt(1, codigo);
            instrucciones.setString(2, datosActualizar[1]);
            instrucciones.setString(3, datosActualizar[3]);
            instrucciones.setString(4, datosActualizar[4]);
            instrucciones.setString(5, datosActualizar[5]); 
            instrucciones.setString(6, herramienta.encriptarContrasenia(datosActualizar[2]));                       
            instrucciones.setString(7, datosActualizar[6]);//datos con posible actualización...
            instrucciones.setString(8, datosActualizar[1]);
            instrucciones.setString(9, datosActualizar[4]);
            instrucciones.setString(10, herramienta.encriptarContrasenia(datosActualizar[2]));                       
            instrucciones.setString(11, datosActualizar[6]);            
            
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
