<%-- 
    Document   : GestorModificacionCajero
    Created on : 28/11/2020, 15:23:09
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page import="Modelo.Nodo"%>
<%@page import="Modelo.Entidades.Objetos.Cambio"%>
<%@page import="Modelo.ListaEnlazada"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/cssGerente.css"><!--por el hecho de ser solo accedido por la pág del frame dentro del frame xD... simi a R//-->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>     
        <link rel="icon" href="img/Logos/Favicon_Banco_ElBilleton.ico"><!--se que no se mostrará puesto que no se mostrará por el hecho de ser una página interna, pero mejor se lo agrego xD-->        
        
        <title>RECORD</title>
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           ListaEnlazada<ListaEnlazada<Cambio>> listadoCambiosGeneral;
           Nodo<ListaEnlazada<Cambio>> nodoDeListados;
           ListaEnlazada<Cambio> listado;
           Nodo<Cambio> nodoAuxiliar;%>
    </head>
    <body>
        <%if(guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Gerente")==false){%>            
            <%response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        <%if(guardia.estaEnHorario("Gerente", (String) request.getSession().getAttribute("codigo"))==false){%>
            <input type="text" id="tipoMsje" value="fueraDeHorario" hidden>
            <script src="js/sweetInformativo.js"></script><!--recuerda que veremos cómo está la apariencia de la página cuando se redireccione ella misma hacia aquí para add o no el sweet con una dir menos profunda o no xD-->
        <%}else if(request.getSession().getAttribute("sinDatos")!=null){%>        
                <input type="text" name="tipoMsje" value="sinDatos" hidden>         
                <script src="../../js/sweetInformativo.js"></script>                
               <%request.getSession().removeAttribute("sinDatos");
          }else{%>     
        
            <center>                       
                <%if(request.getAttribute("mostrarError")!= null){%> 
                    <input type="text" id="tipoMsje" value="errorActualizacionUsuario" hidden>
                    <script src="js/sweetError.js"></script>
                    
                <%}else{%><!--quiere decir que no hubo un error "fatal"-->                
                    <h1>HISTORIAL RECIENTE</h1>                                                    
                    <h3 style="margin-left: 300px;">Fecha: <%=java.time.LocalDate.now()%></h3><br/>                

                    <%if(request.getAttribute("cambios")!=null){//<!--me parece absurdo colocarlo puesto que -->
                        listadoCambiosGeneral = (ListaEnlazada<ListaEnlazada<Cambio>>)request.getAttribute("cambios");
                        
                        if(!listadoCambiosGeneral.estaVacia()){                    
                            nodoDeListados = listadoCambiosGeneral.obtnerPrimerNodo();                    
                            
                            for(int numeroListado=1; numeroListado <= listadoCambiosGeneral.darTamanio(); numeroListado++){
                                listado = nodoDeListados.contenido;%>
                                
                                <table cellspacing="10px">
                                    <%if(listado.obtenerNombre().equals("exitosos")){%>                                                                                      
                                        <tr>
                                            <th colspan="4">
                                                <h3 id="subencabezado">>> Cambios Exitosos</h3>
                                            </th>                                  
                                        </tr>                                    
                                    <%}%>
                                    <%if(listado.obtenerNombre().equals("fallidos")){%>                                                                                      
                                        <tr>
                                            <th>
                                                <h3 id="subencabezado">>> Cambios Fallidos</h3>
                                            </th>
                                        </tr>
                                    <%}%>                            
                                        <tr>
                                            <th style="width: 150px;">
                                                <h4>Hora</h4>
                                            </th>
                                            <th style="width:  150px;">
                                                <h4>Tipo</h4>
                                            </th>
                                            <th style="width: 250px;">
                                                <h4>Dato Antiguo</h4>
                                            </th>
                                            <th style="width: 250px;">
                                                <h4>Reemplazo</h4>
                                            </th>
                                        </tr>                                                    
                            <%nodoAuxiliar = listado.obtnerPrimerNodo();
                             for(int cambio =1; cambio <= listado.darTamanio(); cambio++){%>
                                 <tr>
                                    <th>
                                        <h5><%=nodoAuxiliar.contenido.getHora()%></h5>
                                    </th>
                                    <th>
                                        <h5><%=nodoAuxiliar.contenido.getTipo()%></h5>
                                    </th>
                                    <th>
                                        <h5><%=nodoAuxiliar.contenido.getDatoAntiguo()%></h5>
                                    </th>
                                    <th>
                                        <h5><%=nodoAuxiliar.contenido.getDatoNuevo()%></h5>
                                    </th>
                                </tr>                            
                              <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente; 
                             }%><!--se leen los cambios bien almacenados...-->                                               
                            </table>                               
                            <%nodoDeListados = nodoDeListados.nodoSiguiente;
                        }//fin del for para el listado de listados xD
                    }else{%>                                    
                         <h3>Información intacta, sin cambios que mostrar</h3>
                  <%}%>
                  <form method="POST" action="gestorParametrosGerente"><!--pendiente-->                      
                      <%if(request.getParameter("tipoUsuario")!=null){%>
                          <input type="text" name="tipoUsuario" value="<%=request.getParameter("tipoUsuario")%>" hidden>
                          <input type="text" name="datosUsuario" value="<%=request.getParameter("datosUsuario")%>" hidden>                          
                      <%}%>
                      <input type="text" name="desdeElHistorial" value="true" hidden>                                                
                      <button type ="submit" id="submit" name="reporte" value="<%=((request.getParameter("tipoUsuario")!=null)?"Usuario":"Gerente")+"_HistorialCambios"+((request.getParameter("tipoUsuario")!=null)?"Usuarios":"Propios")%>" style="width: 355px; height: 65px;"><img  src="img/flechitaDescarga.png" style="width: 25px; height: 25px;"> DESCARGAR HISTORIAL COMPLETO</button>    
                  </form>                 
                <%}%>                                               
            <%}%> 
            </center>                          
       <%}%>      
    </body>
</html>
