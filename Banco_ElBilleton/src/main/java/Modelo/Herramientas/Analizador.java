/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Objetos.Asociacion;
import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Manejadores.DB.Buscador;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author phily
 */
public class Analizador {
    private int intentos =0;//no es necesario reiniciarlo en el método puesto que cad vez que se busca se está pasando una nueva instancia de este analizador y por lo tanto todo vuelve como al principio...
    private String tipoSituacion;       
    private Buscador buscador = new Buscador();
    
    public boolean analizarSituacionSolicitudes(Cuenta[] cuentasDeDueno, Asociacion[] asociaciones, String numeroCuenta, int situacionBuscador){                       
        if(situacionBuscador>-1){
            if(asociaciones==null){
                return esCuentaPropia(cuentasDeDueno, numeroCuenta);
            }
            else{//esto es para descartar el hecho de que no se haya generado antes una solicitud de aso al número de cta en cuestión. xD..                                          
                intentos = asociaciones.length;
                
                if(asociaciones[asociaciones.length-1].getEstado().equals("aceptada")){
                    tipoSituacion = "aceptada";
                    return false;
                }else if(asociaciones[asociaciones.length-1].getEstado().equals("rechazada") && asociaciones.length==3){
                    tipoSituacion = "agotadas";
                    return false;
                }else if(asociaciones[asociaciones.length-1].getEstado().equals("enEspera")){
                    tipoSituacion = "sinReaccion";
                    return false;
                }//hago todo esto así y no lo agrupo para que solo exista un retorno false y true, por los msje informativos...
            }
            return true;//puesto que puede que no existan solicitudes enviadas para esa realción [aso == null], el estado no sea aceptado [se rechazado y los intentos <3]            
        }
        return false;
    }  
    
    private boolean esCuentaPropia(Cuenta[] cuentasDeDueno, String numeroCuenta){
        int elNumeroCuenta = Integer.parseInt(numeroCuenta);
        
        if(cuentasDeDueno!=null){
            for (int cuentaActual = 0; cuentaActual < cuentasDeDueno.length; cuentaActual++) {
                if(elNumeroCuenta == cuentasDeDueno[cuentaActual].getNumeroCuenta()){
                    tipoSituacion = "cuentaPropia";
                    return false;
                }            
            }
            return true;
        }//sin importar el caso,pues si es así no tiene con qué hacer la realación... bueno aunque eso se debería ver en el JSP... sip pues con el hecho de que el listado no tenga ningún elemento, no le sería posible establecer la relación... Ó así como se hizo xD mostrar los comp solo si salió bien la buśqueda mejor dicho si salió bien el análisis [es decri solo si devolvió true este método xD] de lo contrario no xD
        return false;
    }
    
    public int darNumeroIntentos(){
        return intentos;
    }
    
    public String darTipoSituacion(){
        return tipoSituacion;
    }
    
    private double darMontoInicial(List<Transaccion> listadoTransacciones, double montoActual){//El monto actual se obtiene por medio delmétodo correp para hallar montos del buscadorParaReportes [A menos que ya exista uno en el ebuscador General xD]
        double[] sumaTransacciones = new double[2];//en la primer ubicación irá la suma de los créditos y en la 2da la de los débitos...        
        
        for (int transaccionActual = 0; transaccionActual < listadoTransacciones.size(); transaccionActual++) {
            sumaTransacciones[(listadoTransacciones.get(transaccionActual).getTipoTransaccion().equals("debito"))?1:0]+= listadoTransacciones.get(transaccionActual).getMonto();                        
        }
        
        if(sumaTransacciones[0]>=sumaTransacciones[1]){//Es decir créditos > debitos [pongo el = por si acaso el saldo inicial era de 0...
            return montoActual- (sumaTransacciones[0]-sumaTransacciones[1]);
        }        
        return montoActual + (sumaTransacciones[1]-sumaTransacciones[0]);//y asi se obtiene el monto inicial xD
    }
    
    public List<Transaccion> hallarSaldosPorTransacciones(double saldoActual, int numeroCuenta, List<Transaccion> listadoTransacciones){                                
        double saldoInicial = darMontoInicial(listadoTransacciones, saldoActual);        

        for (int transaccionActual = 0; transaccionActual < listadoTransacciones.size(); transaccionActual++){
            if(listadoTransacciones.get(transaccionActual).getTipoTransaccion().equalsIgnoreCase("debito")){
                saldoInicial -= listadoTransacciones.get(transaccionActual).getMonto();//de tal forma que puedan tenerse el valor real del saldo [= al monto inicial +/- el monto de la transacción...]
                listadoTransacciones.get(transaccionActual).establecerSaldoActual(saldoInicial);            
            }else{//pues solo puede ser crédito o débito...
                saldoInicial += listadoTransacciones.get(transaccionActual).getMonto();
                listadoTransacciones.get(transaccionActual).establecerSaldoActual(saldoInicial);            
            }                        
        }        
        return listadoTransacciones;
    }
    
    public List<Cliente> hallarClientesConCuentasAbandonadas(ResultSet resultado){
        List<Cliente> listadoClientes = new LinkedList<>();
        
        try {
            Cliente[] clientes = (Cliente[])buscador.buscarUsuarios("Cliente", "orden");
            resultado.last();
            int clientesActivos = resultado.getRow();
            int clientesExcluidos=0;
            
            for (int clienteActual = 0; clienteActual < clientes.length; clienteActual++) {                
                if(clientesExcluidos < clientesActivos && resultado.getInt(1) == clientes[clienteActual].getCodigo()){//Hasta donde yo recuerdo al tener un && si la primer condición resulta ser falsa, entonces ya no revisa la otra...
                    resultado.next();
                    clientesExcluidos++;
                }else{
                    listadoClientes.add(clientes[clienteActual]);
                }                                       
            }                    
        } catch (SQLException | NullPointerException e) {
                System.out.println("Error al analizar a los clientes con Cuentas ABANDONADAS -> "+e.getMessage());
        }
        return listadoClientes;    
    }
    
}
