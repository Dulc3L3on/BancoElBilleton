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
@WebServlet("/cargaDPI")
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
    maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class GestorCargaDPI extends HttpServlet{
   public static final String BASE_PATH = "/home/phily/Documentos/Carpeta_estudios/CuartoSemestre/IPC2/LabIPC2/Proyectos/BancoElBilleton/BancoElBilleton/Banco_ElBilleton/src/main/webapp/Trabajadores/Gerente/DPIsClientes";//entonces lo guardará en la carpeta del gerente...
        
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Processing file");                      
        Part filePart = request.getPart("archDPI");
        String fileName = getFileName(filePart); //no es necesario revisar si es null, porque es obligatorio el campo [required]       
        
        String path = BASE_PATH + "/" + fileName;
        filePart.write(path);
        
        String mimeType = filePart.getContentType();
        System.out.println("type: " + mimeType);
        System.out.println("file name: " + fileName);
        System.out.println("Stored at: " + path);        
        
        request.setAttribute("nombreArchDPI", fileName);
        request.getRequestDispatcher("gestorCreacionCliente").forward(request, response);
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
