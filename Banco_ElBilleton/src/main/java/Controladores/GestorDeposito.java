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
@WebServlet("/gestorDeposito")
public class GestorDeposito extends HttpServlet{    
    private Transaccion transaccion;       
    private Cajero cajero;
    private Buscador buscador = new Buscador();
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        cajero = (Cajero)buscador.buscarUsuario("Cajero", "codigo", request.getSession().getAttribute("codigo").toString());
        
        if(cajero!= null){
            transaccion = cajero.realizarTransaccion(request.getParameter("numeroCuenta"), request.getParameter("monto"), "credito");
                    
             if(transaccion!=null){                                                   
                request.setAttribute("saldoAntiguo", request.getParameter("saldo"));
                request.setAttribute("tipo", "credito");
                request.setAttribute("transaccion", transaccion);                               
            }
        }
        if(transaccion==null){
            request.setAttribute("mostrarError", "mostrar");
        }        
       
        try {            
            request.getRequestDispatcher("Trabajadores/Cajero/Resultado_Transaccion.jsp").forward(request, response);             
         } catch (ServletException | IOException e) {
                System.out.println("Error al mostrar los resutados del DEPÓSITO: " + e.getMessage());
         }                
    }        
}
