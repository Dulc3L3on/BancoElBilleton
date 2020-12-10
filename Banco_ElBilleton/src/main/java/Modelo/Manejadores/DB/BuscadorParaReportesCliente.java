/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Entidades.Objetos.Asociacion;
import Modelo.Entidades.Objetos.Cambio;
import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Herramientas.Analizador;
import Modelo.Herramientas.Kit;
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
public class BuscadorParaReportesCliente {
    private Connection conexion = ManejadorDB.darConexion();
    private Buscador buscador = new Buscador();
    private TransformadorParaReportes transformadorParaReportes = new TransformadorParaReportes();
    private Kit herramientas = new Kit();
    private Analizador analizador = new Analizador();
    private int tipoSituacion;
    
    
    /**
     * Este será para completar los datos de los cambios...
     * @param cambios
     * @return
     */
    public List<Cambio> buscarNombresGerenteACargo(Cambio[] cambios){
        Usuario usuarioGerente = null;
        List<Cambio> listaCambios = new LinkedList<>();
        
        if(cambios!=null){
            for (int cambioActual = 0; cambioActual < cambios.length; cambioActual++){
                usuarioGerente = buscador.buscarUsuario("Gerente", "codigo", String.valueOf(cambios[cambioActual].getCodigoGerenteACargo()));
            
                if(usuarioGerente!=null){
                    cambios[cambioActual].establecerNombreGerenteACargo(usuarioGerente.getNombre());                                    
                }else{
                    cambios[cambioActual].establecerNombreGerenteACargo("???");//con tal que sepan que surgió un error, posiblemente ya no exista el gerente... pero no tendría que eliminarse los cambios realizados por el gerente que ya no es trabajador, para evitar este problema... y eso fue lo que se hizo xD, por el hecho de haber establecido el NO ACTION al borrar xD
                }
                listaCambios.add(cambios[cambioActual]);                
            }            
        }        
        return listaCambios;
    }//1
    
    //NOTA: Si lso datos de los listado que se reciben en estos métodos que buscan los nombres, se hacen en el mismo servlet.... es mejor que se haga aquí y no que se coloque la búsqueda en los parámetros decada uno de estos métodos...
    public List<Transaccion> buscarNombreCajeroACargo(List<Transaccion> transacciones){
        Usuario usuarioCajero = null;       
        
        if(transacciones!=null){//bueno, si está vacía no habrá problema, puesto que dará un tamaño = 0, pero si está nula ahí si...                                            
            for (int transaccionActual = 0; transaccionActual < transacciones.size(); transaccionActual++) {
                usuarioCajero = buscador.buscarUsuario("Cajero", "codigo", String.valueOf(transacciones.get(transaccionActual).getCodigoCajeroACargo()));
            
                if(usuarioCajero!=null){
                    transacciones.get(transaccionActual).establecerNombreCajeroACargo(usuarioCajero.getNombre());
                }else{
                    transacciones.get(transaccionActual).establecerNombreCajeroACargo("???");//con tal que sepan que surgió un error, posiblemente ya no exista el gerente... pero no tendría que eliminarse los cambios realizados por el gerente que ya no es trabajador, para evitar este problema... y eso fue lo que se hizo xD, por el hecho de haber establecido el NO ACTION al borrar xD
                }                
            }
        }        
        return transacciones;//no es necesario devolver la lista puesto que es "por REFERENCIA" y NO por valor...
    }//2

    public int buscarCuentaConMasDinero(String codigoDUeno){
        String buscar = "SELECT numeroCuenta FROM Cuenta  WHERE codigoDueno = ? ORDER BY monto DESC LIMIT 1";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            int codigoDelDueno = Integer.parseInt(codigoDUeno);
            
            instrucciones.setInt(1, codigoDelDueno);
            
            ResultSet resultado = instrucciones.executeQuery();
            if(resultado.first()){
               return resultado.getInt(1);
            }         
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al buscar Cta con + dinero -> "+e.getMessage());
        }
        return -1;
    }
    
    public int[] buscarNumerosDeCuentasDeDueno(String codigoDueno){
        int[] numerosDeCuenta = null;
        
        try{           
            int codigoDelDueno = Integer.parseInt(codigoDueno);            
            Cuenta[] cuentas = buscador.buscarCuentasDeDueno(codigoDelDueno);
            
            if(cuentas!=null){
                numerosDeCuenta = new int[cuentas.length];
                tipoSituacion = 1;//todo NICE XD
                
                for (int cuentaActual = 0; cuentaActual < cuentas.length; cuentaActual++) {
                    numerosDeCuenta[cuentaActual] = cuentas[cuentaActual].getNumeroCuenta();
                }
            }else{
                tipoSituacion = buscador.darTipoSituacion();//no hay cuentas... bueno esto no está bien, porque tb las ctas tienen su propia situación...
            }            
        }catch(NumberFormatException e){
            System.out.println("Error al buscar los números de cuenta del dueño -> "+ e.getMessage());
        }
        return numerosDeCuenta;              
    }      
    
    public List<Transaccion> buscarUltimas15Transacciones(String codigoDueno){               
        int[] numeroCuentas = buscarNumerosDeCuentasDeDueno(codigoDueno);
        List<Transaccion> listaTransaccionesTodasLasCuentas = new LinkedList<>();
       
        for (int cuentaActual = 0; cuentaActual < numeroCuentas.length; cuentaActual++) {
            List<Transaccion> listaTransaccionesHalladas = buscarTransaccionesDeCuenta(numeroCuentas[cuentaActual], String.valueOf(herramientas.darAnioActual())+"-01-01", String.valueOf(herramientas.darAnioActual())+"-12-31");            
            
            for (int transaccionActual = 0; transaccionActual < ((listaTransaccionesHalladas.size()<=15)?listaTransaccionesHalladas.size():15); transaccionActual++) {
                listaTransaccionesTodasLasCuentas.add(listaTransaccionesHalladas.get(transaccionActual));
            }                        
        }
        return listaTransaccionesTodasLasCuentas;                       
    }
    
    public List<Transaccion> buscarTodasLasTransacciones(String numeroCuenta, String fechaInicial, String fechaFinal){
        int numeroDeCuenta = Integer.parseInt(numeroCuenta);
        List<Transaccion> transaccionesDeCuenta = buscarTransaccionesDeCuenta(numeroDeCuenta, fechaInicial, fechaFinal);
        
        double saldoActual = buscarSaldoActual(numeroDeCuenta);
        return analizador.hallarSaldosPorTransacciones(saldoActual, numeroDeCuenta, transaccionesDeCuenta);            
    }//ya solo faltaría introducirlo en el método para hallar el nombre de los cajeros responsables de la transacción...       
    
    public List<Transaccion> buscarTransaccionesDeCuenta(int numeroCuenta, String fechaInicial, String fechaFinal){//este será ejecutado dentro de los parámetros del método que busca el nombre del cajero...
        String buscar = "SELECT * FROM Transaccion WHERE numeroCuentaAfectada =  ? AND fecha BETWEEN ? AND ? ";        
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            
            instrucciones.setInt(1, numeroCuenta);
            instrucciones.setString(2, fechaInicial);
            instrucciones.setString(3, fechaFinal);
            
            //obtienes el resultado, lo transformas a arreglo de transacción y con eso terminarías la búsqueda para el reporte de las transacc de la cta con  > Dinero...
            ResultSet resultado = instrucciones.executeQuery();
            if(transformadorParaReportes.colocarseAlPrincipio(resultado)){
                tipoSituacion =1;//quiere decir que TODO salió bien xD
                return transformadorParaReportes.transformarATransacciones(resultado);
            }else{
                tipoSituacion = 0;//quiere decir que no hay registro alguno de este tipo...
            }
        }catch(SQLException sqlE){
             System.out.println("Error al buscar las TRANSACCIONES de la Cuenta -> "+ sqlE.getMessage());   
             tipoSituacion = -1;//quiere decir que hubo un error
        }        
        return new LinkedList<>();//con tal de que no de null pointer, así que puede que retorne una lista vacía porque no tiene o porque salió mal la búsqueda...
    }
    
    public List<Asociacion> buscarNombrePersonaInvolucrada(String tipoInvolucrado, Asociacion[] solicitudes){//en el parámetro de este método se 
        Usuario usuarioInvolucrado = null; 
        List<Asociacion> listaAsociaciones = new LinkedList<>();
     
        if(solicitudes!=null){
            for (int solicitudActual = 0; solicitudActual < solicitudes.length; solicitudActual++) {
                usuarioInvolucrado = buscador.buscarUsuario("Cliente", "codigo", String.valueOf(solicitudes[solicitudActual].darNombrePersonaInvolucrada(tipoInvolucrado)));
            
                if(usuarioInvolucrado!=null){
                     solicitudes[solicitudActual].establecerNombreInvolucrado(tipoInvolucrado , usuarioInvolucrado.getNombre());
                }else{
                    solicitudes[solicitudActual].establecerNombreInvolucrado(tipoInvolucrado, "???");//con tal que sepan que surgió un error, posiblemente ya no exista el gerente... pero no tendría que eliminarse los cambios realizados por el gerente que ya no es trabajador, para evitar este problema... y eso fue lo que se hizo xD, por el hecho de haber establecido el NO ACTION al borrar xD
                }
                
                listaAsociaciones.add(solicitudes[solicitudActual]);
            }
        }        
       return listaAsociaciones;        
     }//no olvides que el "tipoSolicitud" lo obtendrás en el servlet establecedor de paráms del Cliente...
    
    public double buscarSaldoActual(int numeroCuenta){
        String buscar ="SELECT monto FROM Cuenta WHERE numeroCuenta = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){                       
            instrucciones.setInt(1, numeroCuenta);
            
            ResultSet resultado = instrucciones.executeQuery();            
            if(resultado.first()){
                return resultado.getDouble(1);
            }                      
        }catch(SQLException sqlE){
            System.out.println("Error al buscar el SALDO ACTUAL de la cuenta -> "+ sqlE.getMessage());
        }
        return -1;        
    }
    
    public int darTipoSituacion(){
        return tipoSituacion;
    }
    
}
    
   //lo que tienes que hacer es, volver a listado todas las transacciones, hacer que el método de buscar transaccionesd e ctaMayor, sea general para buscar las transacciones de cada una de las cuentas del arreglo de números de cuentas, de tal manera qu epueda hallar las transacciones de más de 1, puesto que hay que hallar para e cliente las transacc de más de 1 cta, pero lo que pasa con él es que no deben ser más de 15 por cada 1, entonces talvez hay que hacer otra cosa como, hacer que el listado en el que se add las transacc sea global y así no halla problema con el hecho de crear un método para formar un listado de  un arreglo de número de ctas y dejar al método que era para la cta con mayor monto como un métdo que halla las transacc de 1 sola cta...

/*reportes que emplean al método 
    1. Gerente: hist Cbs
    2. Cliente: ultimas15, historialTransacc, Cta>Dinero
   */