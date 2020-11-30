/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Herramientas.GuardiaSeguridad;
import Modelo.Manejadores.ManejadorDeNavegacion;
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
@WebServlet("/gestorLogin")
public class GestorLogin extends HttpServlet{   
   Usuario usuario;
   GuardiaSeguridad guardia = new GuardiaSeguridad();
   ManejadorDeNavegacion navegador = new ManejadorDeNavegacion();//Se dará según el tipo de usuario
    
        
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)//UNa pregunta, cada vez que se redirecciona al servlet sucede lo mismo que cuando se llama a un método, es decir todo regresa a la normalidad?... sería de probar xD
    throws ServletException, IOException {                     
        String tipoUsuario = request.getParameter("tipoUsuario");
        
        System.out.println(tipoUsuario);
        System.out.println("codigo-> "+ request.getParameter("username"));
        
        if(!guardia.esUsuarioAutentico(request.getParameter("username").trim(), request.getParameter("password").trim(), tipoUsuario)){
           System.out.println("NO auténtico");
           request.getSession().setAttribute("mostrarErrorLog", true);//si se entra aquí es porque hubo un error y por ello se redirigirá a la página de LOGIN...                       
           tipoUsuario = null;//para que se muestre el error...
        }else{
           System.out.println("Auténtico");
           request.getSession().setAttribute("codigo", request.getParameter("username"));//la contraseña no porque el gerente puede modificar la suya en la sesión en cuestión...            
        }
        System.out.println("sesión login-> "+request.getSession());
        System.out.println("codigo-> "+ request.getSession().getAttribute("codigo"));
        String pagina = navegador.darPaginasPrincipales(tipoUsuario);        
        response.sendRedirect(pagina); 
    }    
    
}//ahí preguntas como obtener la parte del puerto... y cómo es que se llama
//con el serverName obtenienes lo equivalente al localhost, con el serverPort obtienes los numeritos que van después de los : [es decie el # de puerto en el que se exe la pág...] y con el contextPath obtienes la raíz del proyecto hasta la carpeta en la que se encuentra la pág de la cual se partió con la redirección...
//request.getRequestDispatcher(request.getServerName()+":"+request.getServerPort()+request.getContextPath() +"/"+pagina)
//con lo de arriba más o menos se forma la dirección... y esto por el hecho de que agrega un / antes de localhost...
