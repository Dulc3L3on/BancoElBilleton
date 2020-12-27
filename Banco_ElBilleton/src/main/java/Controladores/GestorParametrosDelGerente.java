/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Objetos.Cambio;
import Modelo.Entidades.Objetos.Transaccion;
import Modelo.Entidades.Usuarios.Cajero;
import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Entidades.Usuarios.Trabajador;
import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Herramientas.Kit;
import Modelo.Manejadores.DB.Buscador;
import Modelo.Manejadores.DB.BuscadorParaReportesGerente;
import Modelo.Manejadores.DB.BuscadorPersonaEncargada;
import Modelo.Manejadores.DB.Modificador;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phily
 */
@WebServlet("/gestorParametrosGerente")
public class GestorParametrosDelGerente extends HttpServlet{
  private Buscador buscador = new Buscador();
    private BuscadorParaReportesGerente buscadorParaReportesGerente = new BuscadorParaReportesGerente();
    private BuscadorPersonaEncargada buscadorPersonaEncargada = new BuscadorPersonaEncargada();
    private Kit herramientas = new Kit();
    private Modificador modificador = new Modificador();
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] datosBoton = request.getParameter("reporte").split("_");
        request.getSession().setAttribute("nombreArchivo", datosBoton[1]);
        
        establecerParametros(datosBoton[0], request);            
        establecerListados(datosBoton[1], request, response);        
    }
    
    private void establecerParametros(String tipoParametros, HttpServletRequest request){
        Map<String, Object> mapa = new HashMap<>();
        
        if(tipoParametros.contains("Cliente") || tipoParametros.contains("Usuario") || tipoParametros.equals("Gerente")){
            mapa =  darParametrosPersonales(request, tipoParametros);
        }
        else if(tipoParametros.contains("Menor")){
            mapa =  darParametrosLimiteMenor(request, tipoParametros);
        }else if(tipoParametros.contains("Fecha")){
            mapa = darParametrosFechas(request);
        }else if(tipoParametros.contains("encargado")){
            mapa = darParametrosGerenteEncargado(request);
        }
        mapa.put("horaActual", herramientas.darHoraActual());
        request.getSession().setAttribute("parametros", mapa);//esto será así si es que cuando no se quieren parámetros s edbee enviar un mapa null... sino, entonces deberás investigar xD
    }    
    
     private Map<String, Object> darParametrosPersonales(HttpServletRequest request, String tipoUsuario){//Este tipo se obtendrá del encabezado
        Map<String, Object> parametros = new HashMap<>();
        Usuario usuario = buscador.buscarUsuario((tipoUsuario.equals("Usuario")?request.getParameter("tipoUsuario"):tipoUsuario), "codigo", (tipoUsuario.equals("Gerente"))?(String)request.getSession().getAttribute("codigo"):request.getParameter("datosUsuario").split(" ")[0]);
        
        if(usuario!=null){
            parametros.put("nombre", usuario.getNombre());
            parametros.put("codigo"+tipoUsuario, usuario.getCodigo());
            parametros.put("DPI", usuario.getDPI());            
        }   
        if(usuario!=null && request.getParameter("reporte").contains("Incorporacion")){
            parametros.put("fechaIncorporacion", usuario.getFechaIncorporacion());            
        }                
        return parametros;
    }        
     
    private Map<String, Object> darParametrosFechas(HttpServletRequest request){
        Map<String, Object> parametros = new HashMap<>();        
        
        parametros.put("fechaInicial", request.getParameter("fechaInicial"));
        parametros.put("fechaFinal", request.getParameter("fechaFinal"));                            
        return parametros;
    }
    
    private Map<String, Object> darParametrosLimiteMenor(HttpServletRequest request, String tipoMenorLimite){
        Map<String, Object> mapa = new HashMap<>();
        
        if(tipoMenorLimite.contains("Monto")){
            mapa.put("menorTransaccion", request.getParameter("minimoMonto"));
            modificador.modificarLimiteMenor("limiteMayorMonto", request.getParameter("minimoMonto"));//se llama así porque es el límite para el reporte del "MayorMOnto" porque en realidad lo que almacena es el mínimo monto posible que se tomará en cuenta para mostrar el registro :v xD
        }else{//puesto que solo hay dos tipos con la palabra Menor :v xD
            mapa.put("sumaMenorTransacciones", request.getParameter("minimaSuma"));
            modificador.modificarLimiteMenor("limiteMayorSumaMonto", request.getParameter("minimaSuma"));//lo mismo para este límite xD
       }
       return mapa;
    }
    
    private Map<String, Object> darParametrosGerenteEncargado(HttpServletRequest request){
        Map<String, Object> mapa = new HashMap<>();
        
        Usuario gerente = buscador.buscarUsuario("Gerente", "codigo", (String)request.getSession().getAttribute("codigo"));
        mapa.put("nombreEncargado", gerente.getNombre());
        mapa.put("codigoEncargado", gerente.getCodigo());
        return mapa;
    }
    
     private void establecerListados(String tipoListado, HttpServletRequest request, HttpServletResponse response){//aquí se hace la búsqueda de los datos que se mostrarán en las tablas, según el tipo de reporte, y así se establezcan como atributos...                 
        if(tipoListado.contains("Cambios")){
            establecerListadoCambios(tipoListado, request, response);
        }else if(tipoListado.contains("Clientes")){
            establecerListadosDeClientes(tipoListado, request, response);
        }else if(tipoListado.contains("Historial")){//puedo colocar eso por el hecho de que media vez se entre a una condición, ya no se entra a los demás :v... niña eso es lo básico :v xD
            establecerListadoHistorialTransacciones(request, response);
        }else if(tipoListado.contains("ResumenCreacion")){
            establecerListadoResumenCreacion(tipoListado, request, response);
        }else{
            establecerListadoCajeroMuyEficiente(request, response);
        }        
    }
    
     private void establecerListadoCambios(String tipoListado, HttpServletRequest request, HttpServletResponse response){
         try{              
            List<Cambio> listadoCambios = buscadorParaReportesGerente.buscarCambios((tipoListado.contains("Propios"))?"Gerente":request.getParameter("tipoUsuario"),
                    (request.getParameter("datosUsuario")!=null?request.getParameter("datosUsuario").split(" ")[0]: (String)request.getSession().getAttribute("codigo")));
            
            if(listadoCambios!=null && !listadoCambios.isEmpty()){
                if(request.getParameter("datosUsuario")!=null){
                    buscadorPersonaEncargada.buscarGerenteACargo(listadoCambios);//NO es necesario que este método devuelva algo por el hecho de ser un cb por referencia y NO por valor... xD
                }                
            
                request.getSession().setAttribute("listado", listadoCambios);//recuerda que todos los atrib del listado deben llamarse igual porque solo habrá 1 gestor para enviar los datos al JR... xD        
                response.sendRedirect(((request.getParameter("desdeElHistorial")!=null || tipoListado.contains("Usuarios"))?"gestorReportesTransaccionesYCambios":"../../gestorReportesTransaccionesYCambios"));//esto será para probar si funciona lo que se hizo con el instanceof, para reducir el número de clases para establecer el listado...
            }else{
                request.getSession().setAttribute("sinDatos",true);
                response.sendRedirect((request.getParameter("desdeElHistorial")!=null)?"Trabajadores/Gerente/Historial.jsp":((tipoListado.contains("Propios"))?"Reportes_Gerente.jsp":"Trabajadores/Gerente/Reportes_Gerente.jsp"));//esta dir está bien puesto que no tiene subform como para que le hayn reducido la profundidad
            }                                           
         }catch(IOException e) {
            System.out.println("Error al establecer el listado de Cambios "+ request.getParameter("datosUsuario")!=null?request.getParameter("tipoUsuario"):"Gerente" +" -> "+ e.getMessage());
         }                 
     }
    
     private void establecerListadosDeClientes(String tipoListado, HttpServletRequest request, HttpServletResponse response){
         List<Cliente> listadoClientes;
         
         try{
            if(tipoListado.contains("Sumas")){                
                listadoClientes = buscadorParaReportesGerente.buscarClientesConMayoresSumasTransaccionales(request.getParameter("minimaSuma"));
            }else if(tipoListado.contains("Transacciones")){
                listadoClientes = buscadorParaReportesGerente.buscarClientesConMayoresTransacciones(request.getParameter("minimoMonto"));
            }else if(tipoListado.contains("Dinero")){
                listadoClientes = buscadorParaReportesGerente.buscarClientesConMasDinero();
            }else{
                listadoClientes = buscadorParaReportesGerente.clientesConCuentasAbandonadas(request.getParameter("fechaInicial"), request.getParameter("fechaFinal"));
            }                
            
            if(!listadoClientes.isEmpty()){
              request.getSession().setAttribute("listado", listadoClientes);//recuerda que todos los atrib del listado deben llamarse igual porque solo habrá 1 gestor para enviar los datos al JR... xD        
                response.sendRedirect((tipoListado.contains("Dinero"))?"../../gestorReportesUsuarios":"gestorReportesUsuarios");//esto será para probar si funciona lo que se hizo con el instanceof, para reducir el número de clases para establecer el listado...
            }else{//creo que tendré que ocolocar un operador ternario para establecer la dirección con la profundidad que requiere, puesto que los clientes con más dinero no requieren de un subform...
                request.getSession().setAttribute("sinDatos",true);
                response.sendRedirect((tipoListado.contains("Dinero"))?"Reportes_Gerente.jsp":"Trabajadores/Gerente/Reportes_Gerente.jsp");
            }//lo mismo digo par este xD, porque puede que funcione para todos los que usan subfiorm pero para el que no [clientes + Dinero] no xD                        
         }catch(IOException e){
             System.out.println("Error al buscar "+ tipoListado +" -> "+e.getMessage());
         }         
     }
     
     private void establecerListadoHistorialTransacciones(HttpServletRequest request, HttpServletResponse response){                  
         try{//REcuerda que esos 2 parámetros aún no existen en los Rep de Gerente...             
             List<Transaccion> listadoTransaccionesRealizadas = buscadorParaReportesGerente.buscarTransaccionesDeCliente(request.getParameter("numeroCuenta"));
             if(!listadoTransaccionesRealizadas.isEmpty()){
                listadoTransaccionesRealizadas = buscadorPersonaEncargada.buscarNombreCajeroACargo(listadoTransaccionesRealizadas);
                request.getSession().setAttribute("listado", listadoTransaccionesRealizadas);
                response.sendRedirect("gestorReportesTransaccionesYCambios");
             }else{
                request.getSession().setAttribute("sinDatos",true);
                response.sendRedirect("Trabajadores/Gerente/Reportes_Gerente.jsp");
            }                          
         }catch(IOException e){
             System.out.println("Error al buscar el HISTORIAL de TRANSACCIONES de un Cliente -> "+e.getMessage());
         }
     }         
    
     private void establecerListadoCajeroMuyEficiente(HttpServletRequest request, HttpServletResponse response){
         try{
            List<Cajero> cajeroUnico = buscadorParaReportesGerente.buscarCajeroMasEficiente(request.getParameter("fechaInicial"), request.getParameter("fechaFinal"));
            if(!cajeroUnico.isEmpty()){
                request.getSession().setAttribute("listado", cajeroUnico);
                response.sendRedirect("gestorReportesUsuarios");
             }else{
                request.getSession().setAttribute("sinDatos",true);
                response.sendRedirect("Trabajadores/Gerente/Reportes_Gerente.jsp");
            }                         
         }catch(IOException e){
             System.out.println("Error al buscar al CAJERO MÁS EFICIENTE -> "+e.getMessage());
         }                          
     }   
     
     private void establecerListadoResumenCreacion(String tipoListado, HttpServletRequest request, HttpServletResponse response){
         try{
             List<Cliente> listadoDeCliente = new LinkedList<>();
             List<Trabajador> listadoDeTrabajador = new LinkedList<>();
            
             if(tipoListado.contains("Cliente")){
                 Cliente cliente = (Cliente) buscador.buscarUsuario("Cliente", "codigo", request.getParameter("codigoUsuario"));
                 if(cliente!=null){
                     listadoDeCliente.add(cliente);
                 }
             }else{
                 Trabajador trabajador = (Trabajador) buscador.buscarUsuario((request.getParameter("trabajador").contains("GERENTE")?"Gerente":"Cajero"), "codigo", request.getParameter("codigoUsuario"));
                 if(trabajador!=null){
                    listadoDeTrabajador.add(trabajador);
                 }                 
             }
             
             if(!listadoDeCliente.isEmpty()){
                request.getSession().setAttribute("listado", listadoDeCliente);
                response.sendRedirect("gestorReportesUsuarios");
             }else if(!listadoDeTrabajador.isEmpty()){
                request.getSession().setAttribute("listado", listadoDeTrabajador);
                response.sendRedirect("gestorReportesUsuarios");                
             }else{
                request.getSession().setAttribute("sinDatos",true);
                response.sendRedirect("Trabajadores/Gerente/Resultado_Creacion.jsp");
             }                          
         }catch(IOException e){
             System.out.println("Error al establecer el listado del RESUMEN de creación -> "+ e.getMessage());
         }
     }
}//RECUERDA todos los reportes que tienen el subformulario terminan teniendo una dirección "directa" de tal forma que no debe retrocederse al redirigir al gestor que se encarga de los reportes xD
 //A diferencia de los otros... xD [ESTO SUCEDE por el hecho de REGRESAR a la página desde por medio de la cual se mostró el subform en este caso...
/*Reporte con la profundidad de redirección al gestor del reporete correp arregalo
    a. "Clientes con mayores Transacciones"
    b. Clientes > sumas Transaccionales
    c. Clientes con + dinero
*/

//NOTA: si no tiene subform la profundidad está hasta Trabajadores/Gerente... es decir en el contexto normal, sino, entonces está fuera de estas carpetas...
