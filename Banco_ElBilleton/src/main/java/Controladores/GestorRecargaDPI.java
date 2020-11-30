/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.io.IOException;
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
@WebServlet("/recargaDPI")
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
    maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class GestorRecargaDPI extends HttpServlet{        
    
     @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Processing file");
        String fileName=null;   
        String[] datosCliente = request.getParameterValues("datosActualizar");        
        System.out.println("path antiguo: "+datosCliente[5]);        
        System.out.println(request.getPart("archDPI").getSize());//puesto que la "Parte" nunca es nula... sino que contiene el nombre del contexto... [si mal no estoy eso es lo que contiene xD... pero la cuestión es qu eno es nulo su valor xD...]
        
        if(request.getPart("archDPI").getSize()>0){//quiere decir que seleccionaron un arch... o eso compribarems xd, es que supongo que solo con saber que tiene un valor basta...
            Part filePart = request.getPart("archDPI");
            fileName = getFileName(filePart);        
        
            String path = GestorCargaDPI.BASE_PATH +"/" +fileName;
            filePart.write(path);
        
            String mimeType = filePart.getContentType();  
            datosCliente[5]=fileName;
            System.out.println("path nuevo: "+ fileName);//bueno... auqnue esto se verá en el historial xD            
        }   
        request.setAttribute("datosClienteNuevo", datosCliente);//no se si sea necesario... pero por si acaso el valor del request de los datos a actualizar no se conservan xd mmm SI ES NECESARIO, porque el dato del nombre del arch cb... entonces solo debería ir en el if... mmm nop, porque cb de nombre... o se puede reemplazar? fmmm xd
        //pero si debe colocarse puesto que si se trbajaran con los datos que se mandaron del JSP, se tendrían que recueprar parámetros... en cambio si se establecieran aquí lo sdatos por el hecho de haber cb de pdf del DPI, tendrían que recuperarse un atributo...
        request.getRequestDispatcher("gestorModificacionCliente").forward(request, response);
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
}
