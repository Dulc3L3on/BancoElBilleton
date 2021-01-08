/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Usuarios.Cliente;
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
@WebServlet("/gestorCreacionCliente")
public class GestorCreacionCliente extends HttpServlet{     
    private Creador creador = new Creador();
    private Cliente cliente;//iba a acrear la clase par atransportar los datos, de tal forma que fuera universoal, pero cliente, cajero y gerente, solo serían empleados para la sesión... y yo creo que eso no es correcto xD... además creo que tendría que codificar más xD...    
    private Buscador buscador = new Buscador();
    private Usuario usuario;
    private GuardiaSeguridad guardia = new GuardiaSeguridad();
    private boolean esDPIunico;
 
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){                                                                           
        //aqupi verificas si está en sesión...        
        usuario = null;
        String[] datosIngresados = request.getParameterValues("datosUsuario");
        esDPIunico = guardia.esDPIunico(datosIngresados[1]);//para el cajero y gerente, este valor se obtendrá por medio del parámetro de la query string qe se mandó para escoger guardar los datos y colocar el título al JSP que corresponde xD... pero por el hecho de que no debe repetirse el DPI en ningun tipo de Usuario no será necesario obtenerlo xD
        
        if(esDPIunico){        
            String nombreArchDPI = (String) request.getAttribute("nombreArchDPI");
               
            if(nombreArchDPI!= null){
               usuario = creador.crearCliente(request.getParameterValues("datosUsuario"), nombreArchDPI);
               if(usuario!=null){
                    cliente = (Cliente) usuario;
                    request.setAttribute("birth", cliente.getBirth());//como te pudiste dar cuenta no es nec, porque se convierte el usuario [que seteaste como atrib] automaticamente en cliente en el R//...
                }//es un hecho pero por si las moscas xD           
            }
        }                             
        redireccionarPaginaResultados(request, response);//también hubiera podido ser el doGet xd :v
    }        
    
    private void redireccionarPaginaResultados(HttpServletRequest request, HttpServletResponse response){
        try {
            if(!esDPIunico){
                request.setAttribute("mostrarMsje", "repetido");
                System.out.println("DPI repetido");
            }  
            if(usuario==null){
                request.setAttribute("mostrarMsje", "error");//solo para ser más expllícita xD            
                System.out.println("Usuario nulo");
            }else{
           //RECUERDA!!!, cuando es setAttribute, se DEBE usar la nitación con {} sino no se podrán tomar como objetos... el getParamete todo lo toma como objetos y se emplea [o al  menos yo lo he usado xD cuando se obtiene los valorse de los componentes, como de un botón...
                request.setAttribute("usuario", usuario);//solo para ser más expllícita xD                
                request.setAttribute("correo", usuario.getCorreo());
            }    
            request.getRequestDispatcher("Trabajadores/Gerente/Resultado_Creacion.jsp").forward(request, response);//para no redireccionar a la otra pag solo par mostra eso xD, sino se puede, deplano que se tendrá que hacer así xD        
        } catch (ServletException | IOException e) {
            System.out.println("Error al mostrar resultado de creación: "+ e.getMessage());
        }           
    }       
}//ya SOLO FALTA CREAR EL JSP para el resultado, que será igual que el anterior... solo que le add el allert que indique la repitencia del DPI [y te recuerdas de fijarte de cómo deberían leerse los datos en R// por el hecho de haber cambiado un poquis l creación del gerente y cajero... aunque dudo que cb ahí, lo que variará un poquis [para crear al usser que corresponde... en donde creo que enviarás como parámetro el tipo de usuario que recogerás del getParameter de la queryString, para llevar la creación a cabo...] es el servlet...
