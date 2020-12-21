/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Cajero;
import Modelo.Herramientas.Kit;
import Modelo.Manejadores.DB.Buscador;
import Modelo.Manejadores.DB.BuscadorParaReportesTrabajador;
import Modelo.Manejadores.DB.BuscadorPersonaEncargada;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phily
 */
@WebServlet("/gestorParametrosCajero")
public class GestorParametrosCajero extends HttpServlet{
    private Buscador buscador = new Buscador();
    private BuscadorParaReportesTrabajador buscadorParaReportes = new BuscadorParaReportesTrabajador();
    private BuscadorPersonaEncargada buscadorPersonaEncargada = new BuscadorPersonaEncargada();
    private Kit herramientas = new Kit();
   
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] datosBoton = request.getParameter("reporte").split("_");
        request.getSession().setAttribute("nombreArchivo", datosBoton[1]);
        
        Map<String, Object> mapa = darParametrosPersonales(request);        
        
        if(request.getParameter("reporte").contains("Intervalo")){
            darParametrosPersonalesMasFechas(mapa, request);
        }
        
        request.getSession().setAttribute("parametros", mapa);                
        establecerListadoDeTransacciones(datosBoton[1], request, response);                       
    }
    
    
    private Map<String, Object> darParametrosPersonales(HttpServletRequest request){
        Map<String, Object> parametros = new HashMap<>();
            Cajero cajero = (Cajero) buscador.buscarUsuario("Cajero", "codigo", (String)request.getSession().getAttribute("codigo"));
        
        if(cajero!=null){
            parametros.put("nombre", cajero.getNombre());
            parametros.put("codigoCajero", cajero.getCodigo());
            parametros.put("DPI", cajero.getDPI());            
        }                           
        parametros.put("horaActual", herramientas.darHoraActual());//no tendría una razón para no mostrarse si fallara la búsqueda del cliente...
        return parametros;
    }
    
    private void darParametrosPersonalesMasFechas(Map<String, Object> mapa, HttpServletRequest request){
        if(mapa!=null){
            mapa.put("fechaInicial", request.getParameter("fechaInicial"));
            mapa.put("fechaFinal", request.getParameter("fechaFinal"));                
        }
        
    }
    
    private void establecerListadoDeTransacciones(String tipoListado, HttpServletRequest request, HttpServletResponse response){
        try{
            List<Transaccion> transaccionesAtendidas = buscadorParaReportes.buscarTransaccionesAtendidas((String) request.getSession().getAttribute("codigo"), request.getParameter("fechaInicial"), request.getParameter("fechaFinal"));
            
            if(buscadorParaReportes.darTipoSituacion()==1){
                buscadorPersonaEncargada.buscarDuenoDeCuenta(transaccionesAtendidas);
                
                request.getSession().setAttribute("listado", transaccionesAtendidas);//recuerda que todos los atrib del listado deben llamarse igual porque solo habrá 1 gestor para enviar los datos al JR... xD                        
                response.sendRedirect((request.getParameter("reporte").contains("Intervalo"))?"gestorReportesTransaccionesYCambios":"../../gestorReportesTransaccionesYCambios");
            }else{
                request.getSession().setAttribute("sinDatos",true);
                if(tipoListado.contains("Intervalo")){
                    response.sendRedirect("Trabajadores/Cajero/Reportes_Cajero.jsp");//supongo, puesto que con el gerente fue necesario, al igual que ocn el CLiente, ya que aquí se hace una redirección con el response a partir de una redirección previa para mostrar el form...
                }else{
                    response.sendRedirect("Reportes_Cajero.jsp");
                }
                
            }                        
        }catch(IOException e){
            System.out.println("Error al establecer el listado de TRANSACCIONES ATENDIDAS -> "+e.getMessage());
        }              
    }
}
