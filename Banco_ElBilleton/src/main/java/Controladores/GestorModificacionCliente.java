/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Usuarios.Cliente;
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
@WebServlet("/gestorModificacionCliente")
public class GestorModificacionCliente extends HttpServlet{
    Modificador modificador = new Modificador();        
    Buscador buscador = new Buscador();       
    Gerente gerente;//esto por medio de las sesiones, porque creo que daría problemas con singleton...o no.. quizá no, por el hecho de tener un método para cuando se quiera preservar y otro para cuando no... aśi que NO PROBLEM!!! XD
    boolean fueActualizado=false;      
    String[] datos;        
    Kit herramienta = new Kit();
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){                                                                           
        //aqupi verificas si está en sesión...        
                        
        datos = (String[]) request.getAttribute("datosClienteNuevo");//son los que se establecieron en el form...
        System.out.println(datos[0]);
        
        datos[7] =(request.getParameter("nuevaContrasenia")!=null)?herramienta.generarContraseniaAleatoria():datos[7];//es decir que si no se selccionó el cuadrito, seguriá manteniendose la contraseña anteriror...
        fueActualizado =modificador.modificarCliente(datos);//si da un null pointer es porque no se mantuvieron el request y response que se mandó desde el JSP hacia el gestor de recarga...
        redireccionarPaginaHistorial(request, response);//también hubiera podido ser el doGet xd :v        
    }        
    
    private void redireccionarPaginaHistorial(HttpServletRequest request, HttpServletResponse response){
        try {
            if(fueActualizado){
                //Aquí tendría que ir el if para revisar si el valor del checkbox !=null para llamar al método de creación aleatoria y de esa forma la posición en datos que contenía la antigua, actualice su valor para hacer bien la comparación...               
                gerente = (Gerente) buscador.buscarUsuario("Gerente", "codigo", (String)request.getSession().getAttribute("codigo"));//el id que devuelve es el id de la sesión... pero aquí se requiere el de la DB... [quizá el otro sea útil para evitar que abra seasión más de una vez...]                
                
                gerente.hallarCambiosCliente((Cliente) request.getSession().getAttribute("usuarioBuscado_Cliente"), datos);//y así por medio del usuario vuelto atributo de sesión, se obtienen lso datos del usuario antiguo... en este caso los del cajero...            
                request.setAttribute("cambios", gerente.darListaListados());                
                request.getSession().removeAttribute("usuarioBuscado_Cliente");//esto lo había colocardo para que no se mostran los datos del recien modificado cuando todo saliera bien...
            }else{                                                                                                                                  
                request.setAttribute("mostrarError", "true");//puesto que cuando no se agregue correctamente un cambio, será agregado uno por uno a la página...                
            }            
            request.getRequestDispatcher("Trabajadores/Gerente/Historial.jsp").forward(request, response);//para no redireccionar a la otra pag solo par mostra eso xD, sino se puede, deplano que se tendrá que hacer así xD        
        } catch (ServletException | IOException e) {
            System.out.println("Error al redireccionar al Historial: "+ e.getMessage());
        }           
    }
    
    //NOTA: el Gerente, nunca debería ser nulo, por eso no lo encierro en un if... además si llegara a serlo
    //es porque no inició sesión... y eso no es posible xD porque se le add la revisión xD...
    //lo que podrías hacer es mostrar un msje de sweet diciendo qu eno inicio sesión xD    
}
