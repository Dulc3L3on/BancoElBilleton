/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import Modelo.Entidades.Objetos.Cambio;
import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Cajero;
import Modelo.Entidades.Usuarios.Cliente;
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
public class BuscadorParaReportesGerente {    
    private Connection conexion = ManejadorDB.darConexion();
    private TransformadorParaReportes transformadorParaReportes = new TransformadorParaReportes();
    private Buscador buscador = new Buscador();
    private BuscadorParaReportesCliente buscadorParaReportesCliente = new BuscadorParaReportesCliente();
    private Kit herramientas = new Kit();
    private Analizador analizador = new Analizador();
    private int tipoSituacion;    
          
    public List<Cambio> buscarCambios(String tipoUsuario, String codigoUsuarioCambiado){
        String buscar = "SELECT * FROM Cambios_"+ tipoUsuario+" WHERE "+tipoUsuario.toLowerCase()+"Cambiado = ?";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE)){
            int codigoUsuario = Integer.parseInt(codigoUsuarioCambiado);            
            instrucciones.setInt(1, codigoUsuario);
            
            ResultSet resultado = instrucciones.executeQuery();
            if(transformadorParaReportes.colocarseAlPrincipio(resultado)){
               tipoSituacion = 1;
               return transformadorParaReportes.transformarACambios(resultado, tipoUsuario);
            }else{
                tipoSituacion = 0;
            }                     
        }catch(SQLException | NumberFormatException e){
            tipoSituacion = -1;
            System.out.println("Error al recuperar el historial de cambios del "+ tipoUsuario);
        }
        return new LinkedList<>();
    }        
    
    public List<Cliente> buscarClientesConMayoresTransacciones(String limiteInferior){
        Cliente[] clientes = (Cliente[])buscador.buscarUsuarios("Cliente", "codigo");
        List<Cliente> clientesGrandes = new LinkedList<>();
        
        if(clientes!=null){
            for (int clienteActual = 0; clienteActual < clientes.length; clienteActual++) {
                if(poseeMayorTransaccion(clientes[clienteActual].getCodigo(), limiteInferior)){
                    clientesGrandes.add(clientes[clienteActual]);
                }                
            }
        }
        return clientesGrandes;
    }
    
    public boolean poseeMayorTransaccion(int codigo, String limiteInferior){//Si llegaras a agergar el subreporte, entonces sería conveniente que este método en lugar de devovler un boolean, devuelva las trasnacciones para agergarlas al listado que se enviaría al subreporte, eso sí tendrían que tenerse 2 listados, uno de solo CLIENTEs y otro de las TRANSACCIONes del cliente, puesto que uno sería para el reporte principal el otro para el secundario; dije que este devolviera de una vez las transacciones para evitar redundancia en los pasos... de tal forma que luego de revisar que el listado que acab de enviar este método no sea nulo [o mejor dicho no esté vacío, porque no devolvería nunca un listado nulo...] se agregaran los ele de este LIST al LIST que se estabelcerá para llenar el subrep y se agregara el usuario que se buscaría por medio del código que se envió para hacer la búsqueda del listado de TRANSACC pero por ese motivo tb tendrías que revisar que el iusaurio que devuelva el método al que se le ordenó buscar al cliente, no sea nul...
        Cuenta[] cuentasDeDueno = buscador.buscarCuentasDeDueno(codigo);   
        
        if(cuentasDeDueno!=null){
            try{//podría ser que al probar el programa no a todos los clientes se les creen uentas, así que colocas el nullPointe en el catch o simplemente dices si cuentasDeDueno != null haces el procedimiento, de lo contrario no xD
                for (int cuentaActual = 0; cuentaActual < cuentasDeDueno.length; cuentaActual++) {
                    if(poseeMayorTransaccionLaCuenta(cuentasDeDueno[cuentaActual].getNumeroCuenta(), Double.parseDouble(limiteInferior))){
                        return true;
                    }
                }
                return false;        
            }catch(NumberFormatException e){
                System.out.println("Error al buscar si el CLIENTE posee MAYOR Transacción -> "+ e.getMessage());
            }
        
        }else{
            System.out.println("El cliente código: "+ codigo +" no posee cuenta alguna");
        }        
        return false;
    }//Creo que al final de cuentas devolverás un listado de Clientes, puesto que eso es lo que solitian, si quieres más trabajín xd, entonces agrega un "sumary" con el listado de las transaccionesd elos clientes mostrados en los detalles...
    
    public boolean poseeMayorTransaccionLaCuenta(int numeroCuenta, double menorMontoPosible){
        String buscar ="SELECT * FROM Transaccion WHERE numeroCuentaAfectada =  ? AND monto > ?";        
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                 ResultSet.CONCUR_UPDATABLE)){
            
            instrucciones.setInt(1, numeroCuenta);
            instrucciones.setDouble(2, menorMontoPosible);
            
            ResultSet resultado = instrucciones.executeQuery();            
            return resultado.first();                
        } catch (SQLException e) {
            System.out.println("Error al buscar si la CUENTA posee una MAYOR Transacción -> "+ e.getMessage());
        }        
        return false;        
    }
    
    public List<Cliente> buscarClientesConMayoresSumasTransaccionales(String limiteInferior){
        Cliente[] clientes = (Cliente[])buscador.buscarUsuarios("Cliente", "codigo");
        List<Cliente> clientesSumasGrandes = new LinkedList<>();
        double[] totales;
        
        if(clientes!=null){
            for (int clienteActual = 0; clienteActual < clientes.length; clienteActual++) {
                totales = poseeMayorSumaTransaccional(clientes[clienteActual].getCodigo(), limiteInferior);
                if(totales!=null){
                    clientes[clienteActual].establecerTotalCreditos(totales[0]);
                    clientes[clienteActual].establecerTotalDebitos(totales[1]);
                    clientesSumasGrandes.add(clientes[clienteActual]);
                }
            }
        }
        return clientesSumasGrandes;
    }
    
    public double[] poseeMayorSumaTransaccional(int codigo, String limiteInferior){//Si llegaras a agergar el subreporte, entonces sería conveniente que este método en lugar de devovler un boolean, devuelva las trasnacciones para agergarlas al listado que se enviaría al subreporte, eso sí tendrían que tenerse 2 listados, uno de solo CLIENTEs y otro de las TRANSACCIONes del cliente, puesto que uno sería para el reporte principal el otro para el secundario; dije que este devolviera de una vez las transacciones para evitar redundancia en los pasos... de tal forma que luego de revisar que el listado que acab de enviar este método no sea nulo [o mejor dicho no esté vacío, porque no devolvería nunca un listado nulo...] se agregaran los ele de este LIST al LIST que se estabelcerá para llenar el subrep y se agregara el usuario que se buscaría por medio del código que se envió para hacer la búsqueda del listado de TRANSACC pero por ese motivo tb tendrías que revisar que el iusaurio que devuelva el método al que se le ordenó buscar al cliente, no sea nul...
        Cuenta[] cuentasDeDueno = buscador.buscarCuentasDeDueno(codigo);   
        double[] sumaTransacciones = {0,0};        
        
        if(cuentasDeDueno!=null){
            try{
                for (int tipoTransaccion = 0; tipoTransaccion < 2; tipoTransaccion++) {
                    for (int cuentaActual = 0; cuentaActual < cuentasDeDueno.length; cuentaActual++) {
                       sumaTransacciones[tipoTransaccion] += buscarSumaTransaccionesDeCuenta(cuentasDeDueno[cuentaActual].getNumeroCuenta(), (tipoTransaccion==0)?"credito":"debito");//con tal de no alargar esto, pues la razón del 0 puede averiguarse al pedir que situación fue la que aconteció xD                                            
                    }
                }                          
                if((sumaTransacciones[0]+ sumaTransacciones[1])> Double.parseDouble(limiteInferior)){//puesto que la suma es de los valores absolutos, es decir sin tomar en cuenta que una sea de créditos y otra de débitos...
                    return sumaTransacciones;
                }
            }catch(NumberFormatException e){
                System.out.println("Error al buscar la SUMA MAYOR Transacción -> "+ e.getMessage());
            }
        }        
        return null;//Sea que halla salido bien la operación o no...
    }
    
    public double buscarSumaTransaccionesDeCuenta(int numeroCuenta, String tipoTransaccionASumar){
        String buscar = "SELECT SUM(monto) FROM Transaccion WHERE numeroCuentaAfectada = ? AND tipo = ?";
            
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                 ResultSet.CONCUR_UPDATABLE)){
        
            instrucciones.setInt(1, numeroCuenta);
            instrucciones.setString(2, tipoTransaccionASumar);
            
            ResultSet resultado = instrucciones.executeQuery();
            if(resultado.first()){
                return resultado.getInt(1);
            }//si no tuviera transccionres, auqnue no es posible por el hecho de que al crear la cuenta se agrega la primer transacción... la unicia situación en la que podría suceder es en la que se eliminaran los registros sin haber tendio esto en cuenta.. o teniendolo pero sabiendo cómo solventarlo...
        }catch(SQLException sqlE){
            System.out.println("Error al buscar la SUMA de "+tipoTransaccionASumar +" -> "+ sqlE.getMessage() );
        }         
        return 0;
    }
    
    public List<Cliente> buscarClientesConMasDinero(){        
        String buscar = "SELECT codigo, nombre, DPI, direccion, sexo, password, birth, pathDPI, fechaIncorporacion, SUM(monto) AS sumaTotal, codigoDueno FROM Cuenta INNER JOIN"
                + " Cliente ON Cliente.codigo = Cuenta.codigoDueno GROUP BY codigoDueno ORDER BY sumaTotal "
                + "DESC LIMIT 10";//no se pudo incluir la condicicón de que se muestren si la suma es mayor a 0, porque da error de sintaxis al parecer es por el inner porque con o sin el alias no se puede por diferenctesrazones, al tenerlo porque dice qu eno la encuentra [ha de ser po el join] y cuando no dice que no se puede por el group by, supongo que tb es por el join, pero si vas a probar prueba nuevamente pero sin el alias xD talvez si te confundiste o pasaste algo por alto xD 
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                 ResultSet.CONCUR_UPDATABLE)){
        
            ResultSet resultado = instrucciones.executeQuery();
            if(resultado.first()){
                return transformadorParaReportes.transformarAListadoClientesAdinerados(resultado);
            }            
        }catch(SQLException | NumberFormatException e){
            System.out.println("Error al buscar a los CLIENTES MÁS ADINERADOS -> "+ e.getMessage());
        }
        return new LinkedList<>();
    }   
    
    public List<Cliente> clientesConCuentasAbandonadas(String fechaInicial, String fechaFinal){        
        String buscar = "SELECT codigoDueno FROM Cuenta INNER JOIN Transaccion ON Cuenta.numeroCuenta = Transaccion.numeroCuentaAfectada WHERE fecha BETWEEN ? AND ? GROUP BY codigoDueno ORDER BY codigoDueno ASC";
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                 ResultSet.CONCUR_UPDATABLE)){
            instrucciones.setString(1, fechaInicial);
            instrucciones.setString(2, fechaFinal);
            
            ResultSet resultado = instrucciones.executeQuery();            
                                
           return analizador.hallarClientesConCuentasAbandonadas(resultado);
        }catch(SQLException sqlE){
            System.out.println("Error al buscar los codigo de los CLIENTES con CTAS ABANDONADAS -> "+ sqlE.getMessage());
        }      
        return new LinkedList<>();
    }
        
    public List<Transaccion> buscarTransaccionesDeCliente(String numeroCuenta){
        List<Transaccion> listadoTransacciones = new LinkedList<>();
        try{
            int cuenta = Integer.parseInt(numeroCuenta);
            listadoTransacciones = buscadorParaReportesCliente.buscarTransaccionesDeCuenta(cuenta, null, herramientas.darFechaActualString());
            
        }catch(NumberFormatException e){
            System.out.println("Error al buscar todas las TRANSACCIONES de la cuenta -> "+e.getMessage());
        }
        return listadoTransacciones;
    }
    
    public List<Cajero> buscarCajeroMasEficiente(String fechaInicial, String fechaFinal){
        List<Cajero> cajeroUnico = new LinkedList<>();
        String buscar = "SELECT COUNT(*) AS conteo, codigoCajero FROM Transaccion WHERE fecha > ? AND fecha < ? GROUP BY codigoCajero ORDER BY conteo DESC LIMIT 1";        
        
        try(PreparedStatement instrucciones = conexion.prepareStatement(buscar, ResultSet.TYPE_SCROLL_SENSITIVE, 
                 ResultSet.CONCUR_UPDATABLE)){            
            instrucciones.setString(1, fechaInicial);
            instrucciones.setString(2, fechaFinal);
        
            ResultSet resultado = instrucciones.executeQuery();
            if(resultado.first()){
                Cajero cajero;
                if(resultado.getInt(2)== 101){
                    cajero = buscador.buscarUsuarioBancaVirtual();
                }else{
                    cajero = (Cajero)buscador.buscarUsuario("Cajero", "codigo", String.valueOf(resultado.getInt(2)));
                }
                
                cajero.establecerNumeroTransacciones(resultado.getInt(1));
                cajeroUnico.add(cajero);
            }
        }catch(SQLException sqlE){
            System.out.println("Error al buscar al CAJERO MÁS EFICIENTE -> "+ sqlE.getMessage());
        }
        return cajeroUnico;
    }
    
    public int darTipoSituacion(){
        return tipoSituacion;
    }    
}
//SELECT * FROM Cliente WHERE Cliente.codigo = (SELECT codigoDueno FROM Cuenta WHERE Cliente.codigo = Cuenta.codigoDueno AND (SELECT COUNT(*) FROM Transaccion WHERE Cuenta.numeroCuenta = Transaccion.numeroCuentaAfectada AND fecha > "2017-05-16" AND fecha < "2017-05-31")=0 GROUP BY codigoDueno) GROUP BY codigo;
//casi pero no da los clientes que no han hecho transacciones en ese tiempo, pero la primer pareja más profunda [es la que usaste en el método] sí da los códigos de los clientes que no realizaron trasnacciones en ese tiempo xD
//si hubiera funcionado, solo hubieromos tenido que transformar ese resultado a Cliente[]:v xD

//SELECT codigoDueno FROM Cuenta WHERE (SELECT COUNT(*) FROM Transaccion WHERE Cuenta.numeroCuenta = Transaccion.numeroCuentaAfectada AND fecha BETWEEN  "2017-05-15" AND "2017-05-31")=0 GROUP BY codigoDueno;
//sI PERO NO xd porue me muestra a Patty cuadno en realidad si deposito el 30 junto con Cnady y Terry...

//SELECT codigoDueno FROM Cuenta INNER JOIN Transaccion ON Cuenta.numeroCuenta = Transaccion.numeroCuentaAfectada WHERE fecha BETWEEN "2017-05-14" AND "2017-05-31" GROUP BY codigoDueno;
//xD tampoco funcionó :| xD