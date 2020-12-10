/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Cuenta;
import Modelo.Manejadores.DB.Creador;
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
@WebServlet("/gestorCreacionCuenta")
public class GestorCreacionCuenta extends HttpServlet{
    private Creador creador = new Creador();
    private Cuenta cuenta;    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
      cuenta=null;            
        
        try {
            //aquI verificas si está en sesión...    NOP,debe se antes xD, es decir al presionar el botón crear... peor si quieres tb aquí por si las moscas xD
            
            cuenta = creador.crearCuenta(request.getParameterValues("datosCuenta"));
            if(cuenta!=null){
                request.setAttribute("mostrarMsje", "correcto");
            }else{
                request.setAttribute("mostrarMsje", "erroneo");
            }
            
            request.getSession().getAttribute("usuarioBuscado");//tienes que invalidarlo... igual el tipo de usuario qu ese usará para que no halla un classCastException...
            //aunque, este problema lo solucioné colocandole un nombre extra para que no surgiera la excepción, puesto que este "nuevo" corresp al tipo de usuario buscado... y además al final de cuentas, se termina perdiendo por el hecho de que cuando cb de opción general [p.ej modificar o crear ] se emplea un response para redirigir así que... xD lo cual no se hizo al redirigir desde el listado por el hecho de tener que mantener los datos del usuario de interés xD
            request.getRequestDispatcher("Trabajadores/Gerente/Creacion_Cuenta.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            System.out.println("Error al crear la CUENTA "+ ex.getMessage());
        }   
        
    }
    
    
    
}
