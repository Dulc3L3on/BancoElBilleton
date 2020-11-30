/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Manejadores.DB.Registrador;
import Modelo.Manejadores.DB.Tramitador;
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
@WebServlet("gestorDeposito")
public class GestorDeposito extends HttpServlet{
    Tramitador tramitador = new Tramitador();    
    Transaccion transaccion;   
    Registrador registrador = new Registrador();
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
     
        if(tramitador.depositar(request.getParameter("numeroCuenta"), request.getParameter("monto"))){//no se porque lo habrí anombrado opDeposito xd... le queda mejor monto xD
            transaccion = registrador.registrarTransaccion(Integer.parseInt(request.getSession().getAttribute("codigo").toString()),
                  request.getParameter("numeroCuenta"), request.getParameter("monto"), "credito");         
         
            if(transaccion!=null){                                                   
                request.setAttribute("saldoAntiguo", request.getParameter("saldo"));
                request.setAttribute("tipo", "credito");
                request.setAttribute("transaccion", transaccion);                               
            }else{
                request.setAttribute("mostrarError", "mostrar");
            }                                     
        }     
        try {            
            request.getRequestDispatcher("Trabajadores/Cajero/Resultado_Transaccion.jsp").forward(request, response);             
         } catch (ServletException | IOException e) {
                System.out.println("Error al mostrar los resutados del DEPÓSITO: " + e.getMessage());
         }
        
        
    }
    
    
}
