/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Asociacion;
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
@WebServlet("/gestorReportesAsociaciones")
public class GestorReportesAsociaciones extends HttpServlet{
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){                                
        try {

            if (request.getSession().getAttribute("codigo") == null) {
                response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]
            }            
            
            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+(String) request.getSession().getAttribute("nombreArchivo")+".pdf");                       

            File file = new File(request.getServletContext().getRealPath("/resources/"+(String) request.getSession().getAttribute("nombreArchivo")+".jrxml"));
            JasperReport jasperReports = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource((List<Asociacion>)request.getSession().getAttribute("listado"));
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReports, (Map<String, Object>)request.getSession().getAttribute("parametros"),dataSource);
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


//NO OLVIDES: EL requestDispatcher dirige a un POST y el response a un GET [de manera preestablecida xD]