/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Usuarios.Trabajador;
import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Herramientas.GuardiaSeguridad;
import Modelo.Manejadores.DB.Buscador;
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
@WebServlet("/gestorCreacionTrabajadores")
public class GestorCreacionTrabajadores extends HttpServlet{
    private Creador creador = new Creador();
    private Trabajador trabajador;//iba a acrear la clase par atransportar los datos, de tal forma que fuera universoal, pero cliente, cajero y gerente, solo serían empleados para la sesión... y yo creo que eso no es correcto xD... además creo que tendría que codificar más xD...        
    private Usuario usuario;
    private GuardiaSeguridad guardia = new GuardiaSeguridad();
    private boolean esDPIunico;    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){        
    
        String[] datosIngresados = request.getParameterValues("datosUsuario");
        esDPIunico = guardia.esDPIunico(datosIngresados[1]);//para el cajero y gerente, este valor se obtendrá por medio del parámetro de la query string qe se mandó para escoger guardar los datos y colocar el título al JSP que corresponde xD
        
        if(esDPIunico){
            usuario=null;                          
            
            usuario = creador.crearTrabajador((request.getParameter("trabajador").equals("CAJERO"))?"Cajero":"Gerente" ,datosIngresados);
            if(usuario!=null){
                trabajador = (Trabajador) usuario;
                request.setAttribute("turno", trabajador.getTurno());//como te pudiste dar cuenta no es nec, porque se convierte el usuario [que seteaste como atrib] automaticamente en cliente en el R//...
            }//es un hecho pero por si las moscas xD                       
        }                             
        redireccionarPaginaResultados(request, response);//también hubiera podido ser el doGet xd :v
    }        
    
    private void redireccionarPaginaResultados(HttpServletRequest request, HttpServletResponse response){
        try {
            if(!esDPIunico){
                request.setAttribute("mostrarMsje", "repetido");
            }  
            if(usuario==null){
                request.setAttribute("mostrarMsje", "error");//solo para ser más expllícita xD            
            }else{
           //RECUERDA!!!, cuando es setAttribute, se DEBE usar la nitación con {} sino no se podrán tomar como objetos... el getParamete todo lo toma como objetos y se emplea [o al  menos yo lo he usado xD cuando se obtiene los valorse de los componentes, como de un botón...
                request.setAttribute("usuario", usuario);//solo para ser más expllícita xD                
            }    
            request.getRequestDispatcher("Trabajadores/Gerente/Resultado_Creacion.jsp").forward(request, response);//para no redireccionar a la otra pag solo par mostra eso xD, sino se puede, deplano que se tendrá que hacer así xD        
        } catch (ServletException | IOException e) {
            System.out.println("Error al mostrar resultado de creación: "+ e.getMessage());
        }           
    }       
    
}
/*NOTA:
    Si el form tiene el enctype "multipart/form-data, dará error [es decir hará los demás 
    parámetros null] si es que el servlet al que se redirigió no está especializado 
    [es capaz de analizar el tipo multiParts...] en el tratamiento de partes [flujos de bites...]
        -> esto lo digo porque lo mismo me sucedió en la vrs ant del proy, poer no habí anotado que 
    era porque estaba redirigiendo a un mismo servlet desde diferentes JSP's [lo cual   
    NO era el problema, porque esto si es posible] pero los JSP de unos no tenían el enctype
    y el servlet si, y esta vez el JSp tenía el multipart [porque olvidé borrarlo xD] y el servlet
    no estaa especializado para eso, puesto que no se requiería en este proceso..

    ASÍ QUE: no redirigias a un servlet que analice partes porque los demás parámetros se
    harán null ó viceversa... por ello auqnque los procesos se parezcan bastante, tendrás que
    hacer servlets para los JSP con/sin enctype...*/