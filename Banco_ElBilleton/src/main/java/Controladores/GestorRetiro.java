/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Cajero;
import Modelo.Manejadores.DB.Buscador;
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
@WebServlet("/gestorRetiro")
public class GestorRetiro extends HttpServlet{    
    private Transaccion transaccion;         
    private Cajero cajero;
    private Buscador buscador = new Buscador();
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        //no sé para que tenía la varibale de saldo actual si ni la usaba :v xD
        cajero = (Cajero)buscador.buscarUsuario("Cajero", "codigo", request.getSession().getAttribute("codigo").toString());
        
        if(cajero!=null){
            transaccion = cajero.realizarTransaccion(request.getParameter("numeroCuenta"), request.getParameter("monto"), "debito");
            
             if(transaccion!=null){                                                     
                request.setAttribute("saldoAntiguo", request.getParameter("saldo"));
                request.setAttribute("tipo", "debito");
                request.setAttribute("transaccion", transaccion);                
            }
        }
        if(transaccion== null){
            request.setAttribute("mostrarError", "mostrar");     
        }
      
        try {            
            request.getRequestDispatcher("Trabajadores/Cajero/Resultado_Transaccion.jsp").forward(request, response);             
         } catch (ServletException | IOException e) {
                System.out.println("Error al mostrar los resutados del RETIRO: " + e.getMessage());
         }
    }//Si se mantienen los valores de los request aunque el lugar donde se emplee no haya sido redireccionado de forma directa por el lugar en donde se encontraba [en este caso el JSP] esto por el hecho de haber usado un dispatcher... xD          
}
