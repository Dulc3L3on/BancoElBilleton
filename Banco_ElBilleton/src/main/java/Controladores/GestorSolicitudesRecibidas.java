/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Asociacion;
import Modelo.Manejadores.DB.Modificador;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phily
 */
@WebServlet("/gestorSolicitudesRecibidas")
public class GestorSolicitudesRecibidas extends HttpServlet{
    private Asociacion[] solicitudes;//Es equivalente xD al menos en este caso de las Aso xD
    private Modificador modificador = new Modificador();         
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){        
        try {                   
            String[] datosInteraccion = request.getParameter("reaccion").split(" ");            
            solicitudes = (Asociacion[]) request.getSession().getAttribute("solicitudes");
            
            if(!modificador.modificarEstadoSolicitud(datosInteraccion[1],
                    String.valueOf(solicitudes[Integer.parseInt(datosInteraccion[0])].getCodigoSolicitante()), 
                    String.valueOf(solicitudes[Integer.parseInt(datosInteraccion[0])].getNumeroCuentaSolicitada()), solicitudes[Integer.parseInt(datosInteraccion[0])].getFechaCreacion())){
                request.setAttribute("mostrarMsjeError", "true");
            }
            request.setAttribute("ubicacionGestor", "gestorSolicitudesRecibidas");
            request.getRequestDispatcher("Cliente/Solicitudes_Recibidas.jsp").forward(request, response);//Esto hasta donde yo se no dará problema con el hecho de que elgesto cb de dirección por el hecho de volver a caer a la página desde donde se le llamó y no desde el btn normal xD          
            
        } catch (ServletException | IOException e) {
            System.out.println("Error al redirigir a Solicitudes_Recibidas...");
        }
    }
}
