/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Transaccion;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author phily
 */
@WebServlet("/gestorReportesTransacciones")
public class GestorReportesTransacciones extends HttpServlet{
 
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){                               
        try {

            if (request.getSession().getAttribute("codigo") == null) {
                response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]
            }            
            
            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+(String) request.getSession().getAttribute("nombreArchivo")+".pdf");                       

            System.out.println("path para exportacion: "+request.getServletContext().getRealPath("../../src/main/webbapp/resources/"+(String) request.getSession().getAttribute("nombreArchivo")+".jrxml\n"));//los ../ solo funcionana cuando se esté en HTML XD
            File file = new File(request.getServletContext().getRealPath("/resources/"+(String) request.getSession().getAttribute("nombreArchivo")+".jrxml"));
            JasperReport jasperReports = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource((List<Transaccion>)request.getSession().getAttribute("listado"));
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReports, (Map<String, Object>)request.getSession().getAttribute("parametros"), dataSource);
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

            request.getSession().removeAttribute("nombreArchivo");
            request.getSession().removeAttribute("listado");
            request.getSession().removeAttribute("parametros");
            
            response.getOutputStream().flush();
            response.getOutputStream().close();            
        } catch (IOException | NumberFormatException   e) {
            System.out.println("Error al buscar el ARCHIVO del reporte -> " + e.getMessage());

        } catch (JRException e) {
            System.out.println("Error EXPORTAR el reporte -> "+ e.getMessage());
        }
    }
    
    
    //recuerda que andas probando como estblecer el path de forma "relativa" xD... adndabas xD porque ya ta xD con el doGet sí se puede usar el contexto del servlet y obtener el path real de donde se encuentra el arch [que se ingresa como parám]... por ello ue que no pudo aplicarse al método que se encarga de recargar el DPI, porque es un doPost...
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*Veo que si es posible tener 1 para lso reportes de las 3 entidades
      puesto que lo que debería escogerse según el tipo de devolución, debería ser
      el método del buscador [individual o de convergencia] con tal de hacer l aconversión
      al objeto que realmente es [esto para los que heredan [los usuarios xD], entonces
      lo que debes pensar y fijarte es de los obj que se devolverá para establecerlos 
      en la receta xD del JR xD... y listo xD, si se llegara a hacer muy extenso, entonces 
      habría que hacer un gestor por cada agrupación general xD
    
    RECUERDA: dijiste que los valores de los button, sería los que emplearías para
                > nombrar los arch JR y así llamar fácilmente, puesto que todos
                  se encuentran en la misma carpeta [es decir a la misma profundidad xD]
                                        y 
         (solo recuerda que si te da un problema podría ser lo que dijo el auxi, es decir el hecho 
         de que no funcionara al enviarle el nombre del arch con una var... yo sé como hacerlo funcionar xD
         GRACIAS DIOS!!! XD :3
                                
                > para establecer la el titulo del arch abierto xD*/
    
}
