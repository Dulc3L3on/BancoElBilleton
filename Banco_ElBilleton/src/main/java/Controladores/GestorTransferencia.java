/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Cajero;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Herramientas.CuerpoEmail;
import Modelo.Manejadores.DB.Buscador;
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
@WebServlet("/gestorTransferencia")
public class GestorTransferencia extends HttpServlet{
    Tramitador tramitador = new Tramitador();    
    Transaccion transacciones[];   
    Registrador registrador = new Registrador();
    Buscador buscador = new Buscador();
    GestorEnvioEmail gestorEnvioEmail = new GestorEnvioEmail();
    CuerpoEmail cuerpo = new CuerpoEmail();
    Cliente remitente;
    Cliente receptor;
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try {   
            if(tramitador.transferir(request.getParameter("origen"), request.getParameter("destino"), request.getParameter("monto"))){//no se porque lo habrí anombrado opDeposito xd... le queda mejor monto xD
                Cajero cajero = buscador.buscarUsuarioBancaVirtual();
                transacciones = registrador.registrarTransferencia(cajero.getCodigo(),
                  request.getParameter("origen"), request.getParameter("destino"), request.getParameter("monto"));         
         
                if(transacciones!=null){                                                   
                    request.setAttribute("saldoAntiguoSaliente", request.getParameter("saldo"));
                    //deplano que lo que haré es lo que había pensado, es decir mostrar el reporte JR como html
                    //puesto que aquí no tengo el saldo anterior de la cuentaDEstino si en dado caso la transferencia fuera
                    //De tipo propia y por eso tendría que hacer una query en la que recuperara solo el saldo y no me conviene así que así será xD
                    //porque si lo tuviera, entonces entre los atributos debería mandar ambos saldos antiguos, el obj transacciones... aunque los request se siguen mandteniendo, así que solo sería necesario enviar las transsacciones porque los saldos  [si tuviera ambos] y el monto ya van en los request antiguos...
                    request.setAttribute("transaccion", transacciones);   
                    
                    if(request.getParameter("tipoTransferencia").equals("terceros")){
                        remitente = (Cliente) buscador.buscarUsuario("Cliente", "codigo", (String) request.getSession().getAttribute("codigo"));
                        receptor = buscador.buscarDuenoDeCuenta(request.getParameter("destino"));
                    
                        if(remitente!=null && receptor!=null){                        
                            request.setAttribute("datosEnvio", "transferenciaEfectuada_"+receptor.getCodigo()+"_Cliente");
                            request.getSession().setAttribute("cuerpo", cuerpo.darCuerpoPorTransferencia(remitente.getNombre(), request.getParameter("destino"), request.getParameter("monto")));                        
                            gestorEnvioEmail.doPost(request, response);
                        }                    
                    }                    
                }else{
                    while(!tramitador.deshacerTransferencia(request.getParameter("origen"), request.getParameter("destino"), request.getParameter("monto"))){                
                    }//puesto que si falla debo dejar todo como antes de intentar exe la axn...
                    request.setAttribute("mostrarError", "mostrar");//Mmm pero si la página que se mostrará será la del JR en html, no se si se pueda add esto... si la pág se genera como el jrxml para los pdf yo diria que sí se podría agregar... pero aún no existe, sino lo que se podría hacer es redirigir a otra página para indicar esto... pero depende xD O PIENSALO XD          
                }                                                     
            }     
                 
            request.getRequestDispatcher("Cliente/EstadoDeCuenta.jsp").forward(request, response);             
         } catch (ServletException | IOException e) {
                System.out.println("Error al mostrar los resutados del DEPÓSITO: " + e.getMessage());
         }
        
        
    }
    //En donde si debes hacer el método que dependiendo de los que se busque se envía un valor de una u otra variable es al buscar las solicitudes, puesto que el codigo del cliente en custión lo tienes como atributo y el otro en un obj... entonces sería de colocar los dos en un componente para que se pueda escoger... auqne, daría igual xD, piensalo xD
    
}
