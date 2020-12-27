/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Entidades.Objetos.Asociacion;
import Modelo.Entidades.Objetos.Cambio;
import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Herramientas.ControladorIndices;
import Modelo.Herramientas.Conversor;
import Modelo.Herramientas.Kit;
import Modelo.ListaEnlazada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class Registrador {     
    private Connection conexion = ManejadorDB.darConexion();               
    private Kit herramientas = new Kit();
    private Conversor conversor = new Conversor();
    private ListaEnlazada<Cambio> listaCambiosAgregados = new ListaEnlazada<>();
    private ListaEnlazada<Cambio> listaCambiosErrados = new ListaEnlazada<>();  
    private ListaEnlazada<ListaEnlazada<Cambio>> listadoDeListados = new ListaEnlazada<>();
    private ControladorIndices controlador = new ControladorIndices();
    private Exterminador exterminador = new Exterminador();
     
    public void registrarCambioClienteCajero(String tipoUsuarioModificado, int codigoGerente, String tipoCambio, String datoAntiguo, String nuevoDato, int codigoClienteCajeroCambiado){
    String hora = herramientas.darHoraActual();
        String registrar = "INSERT INTO Cambios_"+tipoUsuarioModificado+" (fecha, hora, gerenteACargo, tipoDeCambio, "+tipoUsuarioModificado.toLowerCase()+"Cambiado, datoAnterior, datoNuevo)"
                + " VALUES (?,?,?,?,?,?,?)"; 
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(registrar)){            
            instrucciones.setString(1, herramientas.darFechaActualString());//fecha
            instrucciones.setString(2,hora);//hora
            instrucciones.setInt(3, codigoGerente);//codigo Gerente
            instrucciones.setString(4, tipoCambio);//tipo cambio
            instrucciones.setInt(5, codigoClienteCajeroCambiado);//codigo entidad cambiada...
            instrucciones.setString(6, datoAntiguo);//dato antiguo
            instrucciones.setString(7, nuevoDato);//datoNuevo            
            
            instrucciones.executeUpdate();
            listaCambiosAgregados.anadirAlFinal(new Cambio(herramientas.darFechaActualString(), hora, codigoGerente, tipoCambio, datoAntiguo, nuevoDato));
            
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al REGISTRAR cambios del "+tipoUsuarioModificado+": "+ e.getMessage());
            listaCambiosErrados.anadirAlFinal(new Cambio(herramientas.darFechaActualString(), hora, codigoGerente, tipoCambio, datoAntiguo, nuevoDato));
        }
    }
    
     public void registrarCambioGerente(int codigoGerente, String tipoCambio, String datoAntiguo, String nuevoDato){
     String hora = herramientas.darHoraActual();
        String registrar = "INSERT INTO Cambios_Gerente (fecha, hora, gerenteCambiado, tipoDeCambio, datoAnterior, datoNuevo)"
                + " VALUES (?,?,?,?,?,?)";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(registrar)){            
            instrucciones.setString(1, herramientas.darFechaActualString());//fecha
            instrucciones.setString(2,hora);//hora
            instrucciones.setInt(3, codigoGerente);//codigo Gerente
            instrucciones.setString(4, tipoCambio);//tipo cambio            
            instrucciones.setString(5, datoAntiguo);//dato antiguo
            instrucciones.setString(6, nuevoDato);//datoNuevo            
            
            instrucciones.executeUpdate();
            listaCambiosAgregados.anadirAlFinal(new Cambio(herramientas.darFechaActualString(), hora, codigoGerente, tipoCambio, datoAntiguo, nuevoDato));
            
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al REGISTRAR cambios del Gerente: "+ e.getMessage());
            listaCambiosErrados.anadirAlFinal(new Cambio(herramientas.darFechaActualString(), hora, codigoGerente, tipoCambio, datoAntiguo, nuevoDato));
        }
    }
   
    public Transaccion registrarTransaccion(int codigoCajero, String numeroCuenta, String monto, String tipo){
        String registrar = "INSERT INTO Transaccion (codigo, numeroCuentaAfectada, "
                + "tipo, monto, fecha, hora, codigoCajero) VALUES (?,?,?,?,?,?,?)";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(registrar)){
            int codigo = controlador.autoincrementarEntidad("codigo", 3);
            int numeroDeCuenta = Integer.parseInt(numeroCuenta);                 
            double montoDeposito = Double.parseDouble(monto);
            String hora = herramientas.darHoraActual();            
                
            instrucciones.setInt(1, codigo);
            instrucciones.setInt(2, numeroDeCuenta);
            instrucciones.setString(3, tipo);// == deposito [para el otro debes usar debito...]
            instrucciones.setDouble(4, montoDeposito);
            instrucciones.setString(5, herramientas.darFechaActualString());
            instrucciones.setString(6, hora);
            instrucciones.setInt(7, codigoCajero);
            
            instrucciones.executeUpdate();            
            
            return conversor.convertirATransaccion(codigo, numeroDeCuenta, tipo, montoDeposito, herramientas.darFechaActualString(), hora, codigoCajero);
            
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error al REGISTRAR el"+ tipo +": "  + e.getMessage());
        }
        return null;        
    }
     
    public Transaccion[] registrarTransferencia(int codigoCajero, String numeroCuentaSaliente,
    String numeroCuentaReceptora, String monto){
        Transaccion transacciones[] = new Transaccion[2];
        
        transacciones[0] = registrarTransaccion(codigoCajero, numeroCuentaSaliente, monto, "debito");
        if(transacciones[0]!=null){
            transacciones[1] = registrarTransaccion(codigoCajero, numeroCuentaReceptora, monto, "credito");
            if(transacciones[1]!=null){
                return transacciones;
            }else{
                while(!exterminador.deshacerRegistroTransaccion(transacciones[0].getCodigo())){
                    //sería bueno que se ejcutara hasta que se deshiciera... para ello tendrías que colocar un while, pero si de verdad no funcionara se trabaría la aplicación y ahí si sucedería algo malísimo...                
                }    //jaja lo hice xD                
            }
        }
        System.out.println("Error al registrar la TRANSFERENCIA");
        return null;               
    }    
    
    public Asociacion registrarSolicitud(String codigoSolicitado, int codigoSolicitante, String numeroCuentaSolicitada){
       String registrar ="INSERT INTO Asociacion (codigoSolicitado, numeroCuentaSolicitado, codigoSolicitante, fechaCreacion) "
               + "VALUES (?,?,?,?)";
       
       try(PreparedStatement instrucciones = conexion.prepareStatement(registrar)){
           int codigoDelSolicitado = Integer.parseInt(codigoSolicitado);
           int cuentaSolicitada = Integer.parseInt(numeroCuentaSolicitada);
           
           instrucciones.setInt(1, codigoDelSolicitado);           
           instrucciones.setInt(2, cuentaSolicitada);
           instrucciones.setInt(3, codigoSolicitante);
           instrucciones.setString(4, herramientas.darFechaActualString());
           
           instrucciones.executeUpdate();                   
           return conversor.convertirAAsociacion(codigoDelSolicitado, cuentaSolicitada, codigoSolicitante, "enEspera", herramientas.darFechaActualString());
           
       }catch (SQLException | NumberFormatException e) {
            System.out.println("Error al REGISTRAR la ASOCIACIÓN: "  + e.getMessage());
       }
       return null;            
    }      
    
    public ListaEnlazada<ListaEnlazada<Cambio>> darListaDeListados(){
        if(!listaCambiosAgregados.estaVacia()){
            listaCambiosAgregados.establecerNOmbre("exitosos");
            listadoDeListados.anadirAlFinal(listaCambiosAgregados);
        }
        if(!listaCambiosErrados.estaVacia()){
            listaCambiosErrados.establecerNOmbre("fallidos");
            listadoDeListados.anadirAlFinal(listaCambiosErrados);
        }              
        return listadoDeListados;
    }
    
}
