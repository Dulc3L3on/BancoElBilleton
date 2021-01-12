/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Asociacion;
import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Herramientas.Kit;
import Modelo.Manejadores.DB.Buscador;
import Modelo.Manejadores.DB.BuscadorParaReportesCliente;
import Modelo.Manejadores.DB.BuscadorPersonaEncargada;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author phily
 */
@WebServlet("/gestorParametrosCliente")
public class GestorParametrosCliente extends HttpServlet{
    private Buscador buscador = new Buscador();
    private BuscadorParaReportesCliente buscadorParaReportes = new BuscadorParaReportesCliente();
    private BuscadorPersonaEncargada buscadorPersonaEncargada = new BuscadorPersonaEncargada();
    private Kit herramientas = new Kit();
    private int numeroCuentaMayor;
    
     @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] datosBoton = request.getParameter("reporte").split("_");
        request.getSession().setAttribute("nombreArchivo", datosBoton[1]);
        
        establecerParametros(request.getParameter("reporte"), request);        
        establecerListados(request.getParameter("reporte") ,request, response);                        
    }
    
    private void establecerParametros(String tipoParametros, HttpServletRequest request){
        Map<String, Object> mapa = darParametrosPersonales(request, tipoParametros);                
        
        if(tipoParametros.contains("Fechas")){
            darParametrosPersonalesMasFechas(mapa, request);                        
        }
        if(tipoParametros.contains("Cuenta")){
            darParametrosPersonalesMasCuenta(mapa, request);            
        }
        request.getSession().setAttribute("parametros", mapa);
    }
    
    private Map<String, Object> darParametrosPersonales(HttpServletRequest request, String tipoParametros){
        Map<String, Object> parametros = new HashMap<>();
            Cliente cliente = (Cliente) buscador.buscarUsuario("Cliente", "codigo", 
               (tipoParametros.contains("DesdeCajero") || (tipoParametros.contains("Resumen"))?request.getParameter("codigoDueno"):(String)request.getSession().getAttribute("codigo")));
        
        if(cliente!=null){
            parametros.put("nombre", cliente.getNombre());
            parametros.put("codigoCliente", cliente.getCodigo());
            parametros.put("DPI", cliente.getDPI());            
        }                           
        parametros.put("horaActual", herramientas.darHoraActual());//no tendría una razón para no mostrarse si fallara la búsqueda del cliente...
        return parametros;
    }
    
    private void darParametrosPersonalesMasFechas(Map<String, Object> mapa, HttpServletRequest request){
        if(mapa!=null){
            mapa.put("fechaInicial", (request.getParameter("fechaInicial")==null)?"???":request.getParameter("fechaInicial"));
            mapa.put("fechaFinal", request.getParameter("fechaFinal"));//Se supone que nunca debería ser null y si fuese así y por ello fallara la búsqueda, entonces debería mostrar un msje indicando que algo mal salió en la búsqueda xD                
        }        
    }
    
    private void darParametrosPersonalesMasCuenta(Map<String, Object> mapa, HttpServletRequest request){
        if(mapa!=null){
            if(request.getParameter("numeroCuenta")==null){
                numeroCuentaMayor = buscadorParaReportes.buscarCuentaConMasDinero((String) request.getSession().getAttribute("codigo"));
                if(numeroCuentaMayor!=-1){
                    mapa.put("numeroCuentaAfectada", numeroCuentaMayor);                
                }            
            }else{
               mapa.put("numeroCuentaAfectada", Integer.parseInt(request.getParameter("numeroCuenta")));  
            }                        
        }
    }
    
    private void establecerListados(String tipoListado, HttpServletRequest request, HttpServletResponse response){//aquí se hace la búsqueda de los datos que se mostrarán en las tablas, según el tipo de reporte, y así se establezcan como atributos...                 
        switch(tipoListado){
            case "Personales_GrandesTransaccionesdelAnio":
                establecerListadoUltimas15Transacciones(request, response);
            break;
            case "PersonalesFechasCuenta_EstadoDeCuenta": case "PersonalesFechasCuentaDesdeCajero_EstadoDeCuenta":
                establecerListadoTodasLasTransacciones(request, response);
            break;
            case "PersonalesCuenta_CuentaMasDinero":
                establecerListadoCuentaConMasDinero(request, response);
            break;
            case "Personales_ResumenCreacionCuenta":
                establecerResumenCreacionCuentas(request, response);
            break;
            default://puesto que este método ya posee la condición para hacer bien la separación xD
                establecerListadoSolicitudes(request, response);
            break;            
        }    
    }
    
    private void establecerListadoUltimas15Transacciones(HttpServletRequest request, HttpServletResponse response){       
        try {
            List<Transaccion> ultimas15Transacciones = buscadorParaReportes.buscarUltimas15Transacciones((String) request.getSession().getAttribute("codigo"));
            
            if(ultimas15Transacciones!=null && !ultimas15Transacciones.isEmpty()){//El método para buscar la ersona encargada no dará problemas puesto que si salió algo mal dará los ??? sino dará el nombre correspondiente, es decir solito resuleve los problemas que en él puedan surgir xD
                ultimas15Transacciones = buscadorPersonaEncargada.buscarNombreCajeroACargo(ultimas15Transacciones);//NO es necesario que este método devuelva algo por el hecho de ser un cb por referencia y NO por valor... xD
            
                request.getSession().setAttribute("listado", ultimas15Transacciones);//recuerda que todos los atrib del listado deben llamarse igual porque solo habrá 1 gestor para enviar los datos al JR... xD        
                response.sendRedirect("../gestorReportesTransaccionesYCambios");
            }else{//si el tipoSItuación == 0 entonces SIN DATOS sino ERROR BÚSQUEDA
                request.getSession().setAttribute((buscadorParaReportes.darTipoSituacion()==0)?"sinDatos":"errorBusqueda", true);                
                response.sendRedirect("Reportes_Cliente.jsp");
            }                        
        } catch (IOException e) {
            System.out.println("Error al establecer el listado de ULTIMAS 15 GRANDES -> "+ e.getMessage());
        }       
    }
    
    private void establecerListadoTodasLasTransacciones(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Transaccion> todasLasTransaccionesDeCuenta = buscadorParaReportes.buscarTodasLasTransacciones(request.getParameter("numeroCuenta"), request.getParameter("fechaInicial"), request.getParameter("fechaFinal"));
            if(buscadorParaReportes.darTipoSituacion()==1){
                todasLasTransaccionesDeCuenta = buscadorPersonaEncargada.buscarNombreCajeroACargo(todasLasTransaccionesDeCuenta);
            
                request.getSession().setAttribute("listado", todasLasTransaccionesDeCuenta);
                response.sendRedirect("gestorReportesTransaccionesYCambios");
            }else{
                request.getSession().setAttribute((buscadorParaReportes.darTipoSituacion()==0)?"sinDatos":"errorBusqueda", true);                
                if(request.getParameter("reporte").contains("DesdeCajero")){
                    response.sendRedirect("Trabajadores/Cajero/Reportes_Cajero.jsp");
                }else{
                    response.sendRedirect("Cliente/Reportes_Cliente.jsp");
                }                
            }
             
        } catch (IOException e) {
            System.out.println("Error al establecer el listado del ESTADO DE CUENTA -> "+ e.getMessage());
        }
    }
    
    private void establecerListadoCuentaConMasDinero(HttpServletRequest request, HttpServletResponse response){
        try {
            int cuentaConMasDinero = buscadorParaReportes.buscarCuentaConMasDinero((String) request.getSession().getAttribute("codigo"));
            List<Transaccion> listadoTransacciones = new LinkedList<>();
            
            if(cuentaConMasDinero!=-1){//par mi que debería ser !=-1 puesto que cuando es así todo salió bien... pero ya lo había modificado y terminé dejándolo así, no se por qué :v
                listadoTransacciones = buscadorParaReportes.buscarTransaccionesDeCuenta(cuentaConMasDinero, request.getParameter("fechaInicial"), request.getParameter("fechaFinal"));
                listadoTransacciones = buscadorPersonaEncargada.buscarNombreCajeroACargo(listadoTransacciones);//también tendría que haber colocado para las transacciones pero si se hace todo como se debe y no se borran los registros de las trasnacciones de la base de datos, no tendría por qué suceder...                
            }
            if(listadoTransacciones.isEmpty()){//puesto que si el error se da al buscar la cuenta, el listado estará vacío y si el error surge al buscar el listado de transacciones el método devolverá una lista vacía xD así que pum xD 2 pájaros de un tiro xD
                request.getSession().setAttribute((buscadorParaReportes.darTipoSituacion()==-1 || cuentaConMasDinero==-1)?"errorBusqueda":"sinDatos", true);                
                response.sendRedirect("Cliente/Reportes_Cliente.jsp");
            }else{
                request.getSession().setAttribute("listado", listadoTransacciones);
                response.sendRedirect("gestorReportesTransaccionesYCambios");
            }        
        } catch (IOException e) {
            System.out.println("Error al establecer el listado de la CUENTAS CON MÁS DINERO -> "+ e.getMessage());
        }
    }
    
    private void establecerListadoSolicitudes(HttpServletRequest request, HttpServletResponse response){        
        try {
            Asociacion[] solicitudes = null;
            
            if(request.getParameter("reporte").contains("Recibidas")){
                solicitudes = buscador.buscarSolicitudes("recibidas", "codigoSolicitado", (String)request.getSession().getAttribute("codigo"), true);//puesto que son todas las recibidas y no importa que estado tengan...
            }else{
                solicitudes = buscador.buscarSolicitudes("enviadas", "codigoSolicitante", (String)request.getSession().getAttribute("codigo"), true);
            }
            
            if(buscador.darTipoSituacion()==1){
                List<Asociacion> listadoSolicitudes = buscadorPersonaEncargada.buscarNombrePersonaInvolucrada((request.getParameter("reporte").contains("Recibidas"))?"solicitante":"solicitado", solicitudes);
                request.getSession().setAttribute("listado", listadoSolicitudes);                    
                response.sendRedirect("../gestorReportesAsociaciones");
            }else{
                request.getSession().setAttribute((buscador.darTipoSituacion()==-1)?"errorBusqueda":"sinDatos", true);                
                response.sendRedirect("Reportes_Cliente.jsp");
            }//aunque, pensando bien, es redundante puesto que deshabilito el btn si es que no existen el tipo de solicitudes que el reporte en cuestión muestra...
        } catch (IOException e) {
            System.out.println("Error al establecer el listado de SOLICITUDES -> "+ e.getMessage());
        }
    }
    
    private void establecerResumenCreacionCuentas(HttpServletRequest request, HttpServletResponse response){
        GestorReportesTransacciones gestorReportes = new GestorReportesTransacciones();
        
        try {            
            List<Cuenta> listadoCuentasDelCliente = buscadorParaReportes.buscarCuentasDelCliente(request.getParameter("codigoDueno"));
        
            if(!listadoCuentasDelCliente.isEmpty()){
                request.getSession().setAttribute("listado", listadoCuentasDelCliente);                    
                gestorReportes.doGet(request, response);
            }else{
            
                request.setAttribute("errorReporte", true);
                request.getRequestDispatcher("ResultadoCreacionCuenta").forward(request, response);//puesto qu epara llegar aquí ya me encontraba del lado de los JSP... [pues la redirección fue directa...]
            }       
        }catch (ServletException | IOException e) {
            System.out.println("Error al establecer el listado de CREACIÓN DE CUENTA -> "+e.getMessage());
        }
            
    }
}
