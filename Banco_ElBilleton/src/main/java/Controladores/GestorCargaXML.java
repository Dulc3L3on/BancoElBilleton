/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Manejadores.ManejadorXML;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author phily
 */
@WebServlet("/cargaXML")
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
    maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class GestorCargaXML extends HttpServlet{
     public static final String BASE_PATH = "/tmp";//entonces lo guardará en la carpeta del gerente...
     ManejadorXML manejador;
        
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {                          
        //aquí debería ser el punto en el que se coloque el gif... pero creo que con esto redirigirás a otra página... la sel servlet...                                     
        manejador = new ManejadorXML();
        
      //  mostrarProcesmientoDatos(request, response);//debo ponerle un fin o tiempo a este proceso para que se mueste porque si no no pasará a lo de abajito xd DEBUGEA XD
        
        Part filePart = request.getPart("archXML");
        String fileName = getFileName(filePart); //no es necesario revisar si es null, porque es obligatorio el campo [required]       
        
        String path = BASE_PATH + "/" + fileName;
        filePart.write(path);
        
        String mimeType = filePart.getContentType();
        manejador.leerXML(path);
        
        request.setAttribute("resultados", "listos");
        request.setAttribute("listadoErrores", manejador.darListaErrores());           
        //aquí con el dispatcher rediriges a la misma página de carga de datos pero ahora con el atrib necesario para mstrar el msje que corresponde... si el listaod está vacío que se cargó correctamente sino sus elementos xD
        request.getRequestDispatcher("CargaRegistrosAntiguos.jsp").forward(request, response);
    }

    private String getFileName(final Part part) {        
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                    content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    } 
    
    private void mostrarProcesmientoDatos(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html"); 
        
         try(PrintWriter salida = response.getWriter()) {  
             salida.println("<!DOCTYTE html>");
             salida.println("<html>");
             salida.println("<head><title>Processing</title></head>");
             salida.println("<body>");
             salida.println("<script src= \"../../js/sweetProcessing.js\">");             
             salida.println("</script>");
             salida.println("</body></html>");
             salida.close();
         } catch (IOException e){
             System.out.println("Error al mostrar la img de carga -> "+e.getMessage());
         }        
    }//serpa reemplazado por la llamada al método del hilo xD 
    
}
//aquí se cargrá el arch al servidor y se iniciará el proceso de analisis, 
//a partir del nombre y la raíz en la que fue almacenado, con los métodos 
//de la clase dedicada al análisis del arch...