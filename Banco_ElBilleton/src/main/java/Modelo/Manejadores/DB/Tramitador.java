/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Tramitador { 
    Connection conexion = ManejadorDB.darConexion();
    
     public boolean depositar(String numeroCuenta, String monto){
        String tramitar = "UPDATE Cuenta SET monto = monto + ? WHERE numeroCuenta = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(tramitar)){
            int numeroDeCuenta = Integer.parseInt(numeroCuenta);                 
            int montoDeposito = Integer.parseInt(monto);            
            
            instrucciones.setInt(1, montoDeposito);//codigo de la transaccion [autoIncre...]       
            instrucciones.setInt(2, numeroDeCuenta);            
            
            instrucciones.executeUpdate();
            return true;            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error al TRAMITAR el DEPOSITO: " + e.getMessage());
        }
        return false;        
    }
    
    public boolean retirar(String numeroCuenta, String monto){
        String tramitar = "UPDATE Cuenta SET monto = monto - ? WHERE numeroCuenta = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(tramitar)){
            int numeroDeCuenta = Integer.parseInt(numeroCuenta);                 
            int montoDebito = Integer.parseInt(monto);            
            
            instrucciones.setInt(1, montoDebito);//codigo de la transaccion [autoIncre...]       
            instrucciones.setInt(2, numeroDeCuenta);            
            
            instrucciones.executeUpdate();//puesto que en la interfaz se establece el máximo como la cantidad que tiene, entonces no habrá problemas de actualizar a una cantidad negativa xD
            return true;            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error al TRAMITAR el RETIRO: " + e.getMessage());
        }
        return false;   
    }
    
    public boolean transferir(String numeroCuentaSaliente, String numeroCuentaReceptora, String monto){
        if(retirar(numeroCuentaSaliente, monto)){
            if(depositar(numeroCuentaReceptora, monto)){
                return true;
            }else{
                while(!depositar(numeroCuentaSaliente, monto)){//para que regrese al saldo que tenía antes de intentar retirar...
                    //jaja si lo repetí mientras sea false xD
                }                
            }//el contenido de este else debería ejecutarse hasta que saliera bien el depósito por el error en el verdadero depósito...
        }     
        System.out.println("Error al TRANSFERIR");
        return false;//puesto que no se hizo nada [ya sea porque el retiro salió mal o el depósito fue el que falló y por lo tanto se deshizo el retiro...]
    }
    
    /**
     * Por el hecho de que ninguna acción debe quedar sin registro
     * por lo cual si al intetar almacenar la acción en la tabla de 
     * transacciones surge al go mal se deshará todo... [quiere decir
     * que este método será ejecutado MIENTRAS su resultado sea falso...
     * @param numeroCuentaSaliente
     * @param numeroCuentaReceptora
     * @param monto
     * @return
     */
    public boolean deshacerTransferencia(String numeroCuentaSaliente, String numeroCuentaReceptora, String monto){
        if(retirar(numeroCuentaReceptora, monto)){
            if(depositar(numeroCuentaSaliente, monto)){
                return true;
            }else{
                while(!depositar(numeroCuentaReceptora, monto)){//puesto que si no sale, debo hacer que no salga todo, porque sino ahí habría desacuerdo, como retirar o depositar el 2ble... [si es que lo llegara a hacer a la 2da, sino sería el 3ple o más...:|]
                }//para que regrese al saldo que tenía antes de intentar retirar...
            }
        }     
        System.out.println("Error al TRANSFERIR");
        return false;
    }
}
