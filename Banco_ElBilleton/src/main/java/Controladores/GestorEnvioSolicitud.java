/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Asociacion;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Herramientas.Analizador;
import Modelo.Manejadores.DB.Buscador;
import Modelo.Manejadores.DB.Registrador;
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
@WebServlet("/gestorEnvioSolicitud")
public class GestorEnvioSolicitud extends HttpServlet{
    private Buscador buscador = new Buscador();
    private Asociacion asociaciones[];
    private Asociacion asociacion;
    private Cliente cliente;
    private int situacionBusquedaCuentaInteres=0;
    private Analizador analizador = new Analizador();
    private Registrador registrador = new Registrador();
        
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){//será empleado para obtener la info del cliente si es que existe...
        cliente = null;
        System.out.println("Entre al get");
        try {                      
            if(request.getParameter("cuentaBuscada")!=null){
                asociaciones = buscador.buscarAsociaciones(Integer.parseInt((String)request.getSession().getAttribute("codigo")), request.getParameter("cuentaBuscada"));
                situacionBusquedaCuentaInteres= buscador.darTipoSituacion();
                
                if(analizador.analizarSituacionSolicitudes(buscador.buscarCuentasDeDueno(Integer.parseInt((String)request.getSession().getAttribute("codigo"))),asociaciones, request.getParameter("cuentaBuscada"), situacionBusquedaCuentaInteres)){//bien podrías haber colocado este if encerrando a todo el bloque html a mostrar en la pág y ahí buscar al cliente si cumplía con el requisito y de paso mostrar de una vez los comp con esta condición más que suficiente xD, pero por el hecho de que existen diferentes tipos de situación xD y para seguir con el estándar xD
                    cliente = buscador.buscarDuenoDeCuenta(request.getParameter("cuentaBuscada"));
                    situacionBusquedaCuentaInteres= buscador.darTipoSituacion();
                    if(cliente!=null){
                        request.setAttribute("cliente", cliente);
                    }                    
                }            
            }
            
            request.setAttribute("ubicacionGestor", "gestorEnvioSolicitud");
            request.setAttribute("situacionBusqueda", situacionBusquedaCuentaInteres);//esta es de la última búsqueda realizada... pero ahora por las agergaciones hechas para saber si establecer o no el atributo del cliente, siempre que el cliente sea nulo la situación será -1...
            request.setAttribute("situacionAnalizada", analizador.darTipoSituacion());
            request.setAttribute("numeroIntentos", analizador.darNumeroIntentos()+1);            
            System.out.println("Sali del get");
            request.getRequestDispatcher("Cliente/Enviar_Asociacion.jsp").forward(request, response);
            
        } catch (ServletException | IOException e) {
            System.out.println("Error al buscar DUEÑO de la cuenta -> "+e.getMessage());
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            asociacion = registrador.registrarSolicitud(request.getParameter("codigoSolicitado"), Integer.parseInt((String) request.getSession().getAttribute("codigo")), request.getParameter("numeroCuenta"));
            
            if(asociacion!=null){
                request.setAttribute("mostrarMsje", "exitoso");
            }else{
                request.setAttribute("mostrarMsje", "fallido");
            }
            request.getRequestDispatcher("Cliente/Enviar_Asociacion.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            System.out.println("Error al registrar la SOLICITUD -> "+e.getMessage());
        } 
    }
    
    
}
