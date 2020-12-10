/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.RegistrosAntiguos;

import Modelo.Herramientas.ControladorIndices;
import Modelo.Herramientas.Kit;
import Modelo.Herramientas.Verificador;
import Modelo.ListaEnlazada;
import Modelo.Manejadores.DB.ManejadorDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author phily
 */
public class CreadorEntidadesXML {
    private Connection conexion = ManejadorDB.darConexion();    
    private Kit herramienta = new Kit();
    private ControladorIndices controlador = new ControladorIndices();
    private ListaEnlazada<String[]> listaErrados = new ListaEnlazada();
    private Verificador verificador = new Verificador();
    
     public boolean crearCliente(String codigo, String nombre, String CUI, String direccion, String sexo, String password, String birth, String pathDPI){
        String crear = "INSERT INTO Cliente (codigo, nombre, DPI, direccion, sexo, password, birth, pathDPI) VALUES(?,?,?,?,?,?,?,?) "
                     + "ON DUPLICATE KEY UPDATE nombre = Cliente.nombre";//xD ya que no hay algo como no hagas nada y deja lo de antes...xD
        String resultadoVerificacionDatos = verificador.verificarDatosCompletosUsuario(codigo, CUI, nombre, sexo, null, pathDPI);                       
        
        if(resultadoVerificacionDatos.equals("???")){
            try(PreparedStatement instrucciones = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){            
                int codigoCliente = Integer.parseInt(codigo);
            
                instrucciones.setInt(1, codigoCliente);
                instrucciones.setString(2,nombre);
                instrucciones.setString(3, CUI);//este será el nombre del documento, el cual agregarás a la dirección en la que se almacenan todos los DPI, por lo cual podrás obtener el que corresponde, media vez obtengas este nombre... xD, depkano que se tendrá que agarrar luego de haberlo "subido" al servidor... entonces piensa como vas a llamar al servlet subidor...
                instrucciones.setString(4, direccion);
                instrucciones.setString(5, sexo.toLowerCase());
                instrucciones.setString(6, herramienta.encriptarContrasenia(password));
                instrucciones.setString(7, birth);            
                instrucciones.setString(8, pathDPI);            
            
                instrucciones.executeUpdate();
                controlador.hallarMayor(codigoCliente, 0);
                return true;
            }catch(SQLException | NumberFormatException e){
                System.out.println("Error al crear el cliente del XML: "+ e.getMessage());                      
            }   
        }                    
         String[] datos = {"Cliente", codigo, nombre, CUI, direccion, sexo, password, birth, pathDPI, resultadoVerificacionDatos};
         listaErrados.anadirAlFinal(datos);//no muestro el número de línea en el que se encontraba en el XML por el hecho de que no lee línea por línea... sino saltado
         System.out.println("Error en los datos antiguos del Cliente del XML");
        return false;
    }
     
     public boolean crearCajero(String codigo, String nombre, String CUI, String direccion, String sexo, String password, String turno){
        String crear = "INSERT INTO Cajero (codigo, nombre, DPI, direccion, sexo, password, turno) VALUES(?,?,?,?,?,?,?) "
                + "ON DUPLICATE KEY UPDATE nombre = Cajero.nombre";        
        String resultadoVerificacionDatos = verificador.verificarDatosCompletosUsuario(codigo, CUI, nombre, sexo, turno, null);        
        
        if(resultadoVerificacionDatos.equals("???")){
            try(PreparedStatement instrucciones = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){            
                int codigoCajero = Integer.parseInt(codigo);
            
                instrucciones.setInt(1,codigoCajero);
                instrucciones.setString(2,nombre);
                instrucciones.setString(3, CUI);//este será el nombre del documento, el cual agregarás a la dirección en la que se almacenan todos los DPI, por lo cual podrás obtener el que corresponde, media vez obtengas este nombre... xD, depkano que se tendrá que agarrar luego de haberlo "subido" al servidor... entonces piensa como vas a llamar al servlet subidor...
                instrucciones.setString(4, direccion);
                instrucciones.setString(5, sexo.toLowerCase());
                instrucciones.setString(6, herramienta.encriptarContrasenia(password));
                instrucciones.setString(7, turno.toLowerCase());           
            
                instrucciones.executeUpdate();
                controlador.hallarMayor(codigoCajero, 2);
                return true;
            }catch(SQLException | NumberFormatException e){
                System.out.println("Error al crear el cajero del XML: "+ e.getMessage());                           
            } 
        }
        String datos[] = {"Cajero", codigo, nombre, CUI, direccion, sexo, password, turno, resultadoVerificacionDatos};
        listaErrados.anadirAlFinal(datos);
         System.out.println("Error en los datos antiguos del Cajero");
        return false;   
    } 
     
    public boolean crearGerente(String codigo, String nombre, String CUI, String direccion, String sexo, String password, String turno){
        String crear = "INSERT INTO Gerente (codigo, nombre, DPI, direccion, sexo, password, turno) VALUES(?,?,?,?,?,?,?) "
               + "ON DUPLICATE KEY UPDATE nombre = Gerente.nombre"; 
        String resultadoVerificacionDatos = verificador.verificarDatosCompletosUsuario(codigo, CUI, nombre, sexo, turno, null);        
        
        if(resultadoVerificacionDatos.equals("???")){
            try(PreparedStatement instrucciones = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){            
                int codigoGerente = Integer.parseInt(codigo);
            
                instrucciones.setInt(1,codigoGerente);
                instrucciones.setString(2,nombre);
                instrucciones.setString(3, CUI);//este será el nombre del documento, el cual agregarás a la dirección en la que se almacenan todos los DPI, por lo cual podrás obtener el que corresponde, media vez obtengas este nombre... xD, depkano que se tendrá que agarrar luego de haberlo "subido" al servidor... entonces piensa como vas a llamar al servlet subidor...
                instrucciones.setString(4, direccion);
                instrucciones.setString(5, sexo.toLowerCase());
                instrucciones.setString(6, herramienta.encriptarContrasenia(password));
                instrucciones.setString(7, turno.toLowerCase());                        
            
                instrucciones.executeUpdate();            
                controlador.hallarMayor(codigoGerente, 1);
                return true;
            }catch(SQLException | NumberFormatException e){
                System.out.println("Error al crear el gerente del XML: "+ e.getMessage());                         
            }
        }
        String[] datos = {"Gerente", codigo, nombre, CUI, direccion, sexo, password, turno, resultadoVerificacionDatos};
        listaErrados.anadirAlFinal(datos);
        System.out.println("Error en los datos antiguos del Gerente");
        return false;        
    }      
    
    public void crearTransaccion(String codigo, String numeroCuenta, String fecha, String hora, String tipo, String monto, String codigoCajero){
        String crear = "INSERT INTO Transaccion (codigo, numeroCuentaAfectada, tipo, monto, fecha, hora, codigoCajero) VALUES(?,?,?,?,?,?,?)"
                + "ON DUPLICATE KEY UPDATE tipo = Transaccion.tipo";        
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){            
            int codigoTransaccion = Integer.parseInt(codigo);
            int cuenta = Integer.parseInt(numeroCuenta);
            int cajero_id = Integer.parseInt(codigoCajero);
            
            instrucciones.setInt(1,codigoTransaccion);
            instrucciones.setInt(2, cuenta);
            instrucciones.setString(3, tipo);//este será el nombre del documento, el cual agregarás a la dirección en la que se almacenan todos los DPI, por lo cual podrás obtener el que corresponde, media vez obtengas este nombre... xD, depkano que se tendrá que agarrar luego de haberlo "subido" al servidor... entonces piensa como vas a llamar al servlet subidor...
            instrucciones.setString(4, monto);//vamos a dejarlo así, pues tal parece que un int adminte un string... porque me estuvo cargando los datos si darme error al establecer el mosnto con un tipo string...si da error quiere decir que el double no acepta esto...
            instrucciones.setString(5, fecha);
            instrucciones.setString(6, hora);
            instrucciones.setInt(7, cajero_id);                                   
            
            instrucciones.executeUpdate();
            controlador.hallarMayor(codigoTransaccion, 3);
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al crear la transacción del XML: "+ e.getMessage());           
            String[] datos = {"Transaccion", codigo,fecha, hora, numeroCuenta, tipo, monto, codigoCajero, "???"};
            listaErrados.anadirAlFinal(datos);//sería bueno mostrar tb el código del cajero a cargo... pero deberías ver como de tal forma que no se vea desordenado... [quizá sería útil que la lista vaciara sus datos en las cols de una tabla de cada fila creada, pero eso implicaría que el listado de errores recibiera un arreglo xD... eso lo veráa mañanita XD
        }                
    }  
    
    public void crearCuenta(String numeroCuenta, String codigoDueno, String monto, String fechaCreacion, String nombre){
        String crear = "INSERT INTO Cuenta (numeroCuenta, codigoDueno, monto, fechaCreacion) VALUES(?,?,?,?)"
                + "ON DUPLICATE KEY UPDATE monto = Cuenta.monto";        
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)){            
            int cuenta = Integer.parseInt(numeroCuenta);
            int codigo = Integer.parseInt(codigoDueno);
            double saldo = Double.parseDouble(monto);
            
            instrucciones.setInt(1, cuenta);
            instrucciones.setInt(2,codigo);
            instrucciones.setDouble(3, saldo);//este será el nombre del documento, el cual agregarás a la dirección en la que se almacenan todos los DPI, por lo cual podrás obtener el que corresponde, media vez obtengas este nombre... xD, depkano que se tendrá que agarrar luego de haberlo "subido" al servidor... entonces piensa como vas a llamar al servlet subidor...
            instrucciones.setString(4, fechaCreacion);                 
            
            instrucciones.executeUpdate();
            controlador.hallarMayor(cuenta, 4);
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al crear la cuenta del XML: "+ e.getMessage());   
            String[] datos = {"Cuenta", fechaCreacion, numeroCuenta, codigoDueno, monto, "???"};
            listaErrados.anadirAlFinal(datos);//XD si se entinede xD
        }                                
    }
    
    public ListaEnlazada<String[]> darListadoErrores(){
        return listaErrados;
    } 
    public void limpiarListadoCodigosUsuario(){
        verificador.limpiarListadoCodigos();
    }
}
