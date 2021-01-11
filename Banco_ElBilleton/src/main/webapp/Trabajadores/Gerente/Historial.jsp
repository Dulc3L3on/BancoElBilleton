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
        }else if(request.getSession().getAttribute("sinDatos")!=null){%><!--creo que este se colocó de más, porque aquí solo se llega desde los gestores de modificacion de cliente, cajero y gerente y en ninguno de ellos se estableció este atrib...-->
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
                        }%><!--//fin del for para el listado de listados xD-->

                        <form method="POST" action="gestorParametrosGerente"><!--pendiente-->                      
                       <%if(request.getParameter("tipoUsuario")!=null){%>
                            <input type="text" name="tipoUsuario" value="<%=request.getParameter("tipoUsuario")%>" hidden>
                            <input type="text" name="datosUsuario" value="<%=request.getParameter("datosUsuario")%>" hidden>                          
                        <%}%>
                            <input type="text" name="desdeElHistorial" value="true" hidden>                                                
                            <button type ="submit" id="submit" name="reporte" value="<%=((request.getParameter("tipoUsuario")!=null)?"Usuario":"Gerente")+"_HistorialCambios"+((request.getParameter("tipoUsuario")!=null)?"Usuarios":"Propios")%>" style="width: 355px; height: 65px;"><img  src="img/flechitaDescarga.png" style="width: 25px; height: 25px;"> DESCARGAR HISTORIAL COMPLETO</button>    
                        </form>     
                  <%}else{%>                                    
                         <h3>Información intacta, sin cambios que mostrar</h3>
                  <%}%>                              
                <%}%><!--iba a agregarle un sweet, pero es imposible que el registrador le mande un listado nulo al gerente al momento de darle los listados porque ya están inicializados, es imposible que sea null sin haber enviado el msje indicandp que salió algo mal en la actualización y por lo tanto haberse saltado el método del gerente y el establecimiento del atriubto...-->
                  
                <%if(request.getAttribute("mostrarMsjeEnvio")!=null){                    
                        if((boolean)request.getAttribute("mostrarMsjeEnvio") == false){%><!--tienes que revisar esto por los datos que se miestran, es decir por si acaso ya no aparecen, lo cual no debeŕia suceder y si es así entonces se debería a la "remoción" del atributo del usuario correspondiente... pero hay que ver de primero xD-->
                            <input type="text" id="tipoMsje" value="errorEnvioMail" hidden>
                            <script src="js/sweetError.js"></script>   
                      <%}else{%>
                            <input type="text" id="tipoMsje" value="exitoEnvioMail" hidden>
                            <script src="js/sweetInformativo.js"></script>
                      <%}%>
                <%}%>
                
            <%}%> 
            </center>                          
       <%}%>      
    </body>
</html>
