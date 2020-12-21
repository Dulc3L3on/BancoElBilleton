/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Usuarios.Cajero;
import Modelo.Entidades.Usuarios.Cliente;
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
@WebServlet("/gestorReportesUsuarios")
public class GestorReportesUsuarios extends HttpServlet{
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
            List<Object> listadoGenerico =(List<Object>) request.getSession().getAttribute("listado");//no estoy segura si esa conversión no dará error, puesto que estoy convirtiendo algo específico a algo muy general...
            JRBeanCollectionDataSource dataSource = null;
            
            if(listadoGenerico.get(0) instanceof Cliente){
                dataSource = new JRBeanCollectionDataSource((List<Cliente>)request.getSession().getAttribute("listado"));
            }
            else if(listadoGenerico.get(0) instanceof Cajero){
               dataSource = new JRBeanCollectionDataSource((List<Cajero>)request.getSession().getAttribute("listado"));
            }                          
            
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
}
