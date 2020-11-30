/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Usuarios.Cajero;
import Modelo.Entidades.Usuarios.Gerente;
import Modelo.Herramientas.Kit;
import Modelo.Manejadores.DB.Buscador;
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
@WebServlet("/gestorModificacionGerente")
public class GestorModificacionGerente extends HttpServlet{
    Modificador modificador = new Modificador();        
    Buscador buscador = new Buscador();       
    Gerente gerente;//esto por medio de las sesiones, porque creo que daría problemas con singleton...o no.. quizá no, por el hecho de tener un método para cuando se quiera preservar y otro para cuando no... aśi que NO PROBLEM!!! XD
    boolean fueActualizado=false;      
    String[] datos;        
    Kit herramienta = new Kit();
    
     @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){                                                                           
        //aqupi verificas si está en sesión...        
                        
        datos = (String[]) request.getParameterValues("datosActualizar");//son los que se establecieron en el form...
        System.out.println(datos[0]);        
        
        gerente = (Gerente) buscador.buscarUsuario("Gerente", "codigo", (String)request.getSession().getAttribute("codigo"));//de tal forma que pueda hacerse la comparación entre el gerente antiguo y el actual...
        fueActualizado =modificador.modificarGerente(datos);
        redireccionarPaginaHistorial(request, response);//también hubiera podido ser el doGet xd :v        
    }        
    
    private void redireccionarPaginaHistorial(HttpServletRequest request, HttpServletResponse response){
        try {
            if(fueActualizado){                                
                gerente.hallarCambiosPropios(datos);//y así por medio del usuario vuelto atributo de sesión, se obtienen lso datos del usuario antiguo... en este caso los del cajero...            
                request.setAttribute("cambios", gerente.darListaListados());  
            }else{                                                                                                                                  
                request.setAttribute("mostrarError", true);//puesto que cuando no se agregue correctamente un cambio, será agregado uno por uno a la página...                
            }            
            request.getRequestDispatcher("Trabajadores/Gerente/Historial.jsp").forward(request, response);//para no redireccionar a la otra pag solo par mostra eso xD, sino se puede, deplano que se tendrá que hacer así xD        
        } catch (ServletException | IOException e) {
            System.out.println("Error al redireccionar al Historial C.G: "+ e.getMessage());
        }           
    }
    
}
