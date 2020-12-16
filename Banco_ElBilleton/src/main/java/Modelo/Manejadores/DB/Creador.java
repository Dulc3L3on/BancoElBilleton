/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Herramientas.Conversor;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Entidades.Usuarios.Trabajador;
import Modelo.Herramientas.ControladorIndices;
import Modelo.Herramientas.Kit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Creador {
    private Connection conexion = ManejadorDB.darConexion();
    private Conversor conversor = new Conversor();
    private Kit herramientas = new Kit();
    private ControladorIndices controlador = new ControladorIndices();
    
   public Cliente crearCliente(String datosUsuario[], String path){
        String crear = "INSERT INTO Cliente (codigo, nombre, DPI, pathDPI, direccion, sexo, password, birth, fechaIncorporacion) VALUES(?,?,?,?,?,?,?,?,?)";
        String contrasenia = herramientas.generarContraseniaAleatoria();//Aquí el método para generarlas aleatoriamente xD
        String fechaActual = herramientas.darFechaActualString();
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(crear)){            
            int codigo = controlador.autoincrementarEntidad("codigo", 0);
            
            instrucciones.setInt(1, codigo);
            instrucciones.setString(2,datosUsuario[0]);
            instrucciones.setString(3,datosUsuario[1]);
            instrucciones.setString(4, path);//este será el nombre del documento, el cual agregarás a la dirección en la que se almacenan todos los DPI, por lo cual podrás obtener el que corresponde, media vez obtengas este nombre... xD, depkano que se tendrá que agarrar luego de haberlo "subido" al servidor... entonces piensa como vas a llamar al servlet subidor... está Más que HECHO XD
            instrucciones.setString(5, datosUsuario[4]);
            instrucciones.setString(6, datosUsuario[2]);
            instrucciones.setString(7, herramientas.encriptarContrasenia(contrasenia));
            instrucciones.setString(8, datosUsuario[3]);            
            instrucciones.setString(9, fechaActual);
            
            instrucciones.executeUpdate();
            
            return conversor.convertirACliente(datosUsuario, codigo, contrasenia, fechaActual, path);//recuerda que no usaste el returnGeneratedKeys por el hecho de que no es autoIncre... sino obliAutoIncre xD jajaja
            
        }catch(SQLException sqlE){
            System.out.println("Error al crear el cliente: "+ sqlE.getMessage());           
        }                
        return null;
    }//puesto que estoy haciendo referencia a los nombres de las columnas, no es necesario que estén en orden... pero es lo preferible... por ello debes crear y ver si se guarda correctamente para que después no tengas esta duda xD y luego pasarlo al vardadero orden solo para facilitarle el trabajao a MYSQL xD
    
   public Trabajador crearTrabajador(String tipoTrabajador, String[] datosTrabajador){
       String crear = "INSERT INTO "+ tipoTrabajador + "(codigo, nombre, DPI, direccion, sexo, password, turno, fechaIncorporacion) VALUES (?,?,?,?,?,?,?,?)";
       String contrasenia = herramientas.generarContraseniaAleatoria();
       
       try(PreparedStatement instrucciones = conexion.prepareStatement(crear)){
           int codigo = controlador.autoincrementarEntidad("codigo", (tipoTrabajador.equalsIgnoreCase("CAJERO"))?2:1);
           String fechaActual = herramientas.darFechaActualString();
           
           instrucciones.setInt(1, codigo);
           instrucciones.setString(2,datosTrabajador[0]);
           instrucciones.setString(3,datosTrabajador[1]);           
           instrucciones.setString(4, datosTrabajador[2]);
           instrucciones.setString(5, datosTrabajador[3]);
           instrucciones.setString(6, herramientas.encriptarContrasenia(contrasenia));
           instrucciones.setString(7, datosTrabajador[4]); 
           instrucciones.setString(8, fechaActual);          
           
           instrucciones.executeUpdate();
           
           return conversor.convertirATrabajador(tipoTrabajador, datosTrabajador, codigo, contrasenia, fechaActual);
       }catch(SQLException sqlE){
           System.out.println("Error al crear al "+ tipoTrabajador+ ": "+ sqlE.getMessage());
       }
       return null;
   }
  
   public Cuenta crearCuenta(String datosNuevaCuenta[]){
       String crear="INSERT INTO Cuenta (numeroCuenta, codigoDueno, monto, fechaCreacion) VALUES(?,?,?,?)";
       
       try(PreparedStatement instrucciones = conexion.prepareStatement(crear)){                      
           int elNumeroCuenta = Integer.parseInt(datosNuevaCuenta[2]);
           int dueno = Integer.parseInt(datosNuevaCuenta[0]);
           int saldo = Integer.parseInt(datosNuevaCuenta[3]);           
           
           instrucciones.setInt(1, elNumeroCuenta);
           instrucciones.setInt(2, dueno);
           instrucciones.setDouble(3, saldo);
           instrucciones.setString(4, herramientas.darFechaActualString());
           
           instrucciones.executeUpdate();
           
           return conversor.convertirACuenta(elNumeroCuenta, dueno, saldo, herramientas.darFechaActualString());//Esto por le momento, es solo para saber que si salió bien la creación puesto que para la "creacion de cuentas" no es necesario crear el obj... xD
       }catch(SQLException | NumberFormatException e){
           System.out.println("Error al crear la cuenta -> "+e.getMessage());
       }            
       return null;
   }
   
}
