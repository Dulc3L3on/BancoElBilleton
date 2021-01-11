<%-- 
    Document   : Resultado_Creacion.jsp
    Created on : 27/11/2020, 11:33:20
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.CuerpoEmail"%>
<%@page import="Modelo.Entidades.Usuarios.Usuario"%>
<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/cssGerente.css">
        <link rel="stylesheet" href="../../css/cssGerente.css"><!--esto por la redirección del envío del correo-->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script> 
        <link rel="icon" href="img/Logos/Favicon_Banco_ElBilleton.ico"><!--se que no se mostrará puesto que no se mostrará por el hecho de ser una página interna, pero mejor se lo agrego xD-->        
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           Usuario usuario;
           CuerpoEmail cuerpo = new CuerpoEmail();%>
        
        <title>ResultCreation</title>
    </head>
    <body>
        <%if(guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Gerente")==false){%>            
            <%response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }else if(request.getAttribute("mostrarMsjeEnvio")!= null){
            if((boolean)request.getAttribute("mostrarMsjeEnvio") == true){%>
                <input type="text" id="tipoMsje" value="exitoEnvioMail" hidden>
                <script src="js/sweetInformativo.js"></script><!--puesto que se envía desde el gestor de reportes al cual se llegó de manera "directa" por lo cual redirecciona de manera profunda...ammm creo que este comentario no corresponde a l alínea asignada xD-->   
          <%}%>
        <%}else{
            if(request.getSession().getAttribute("sinDatos")!=null){%><!--en qué gestor [servlet] se establece este msje ?? :v no me acuerdo y no lo envuentro...-->
              <input type="text" name="tipoMsje" value="sinDatos" hidden>         
                <script src="../../js/sweetInformativo.js"></script><!--debería funcionar xD-->            
               <%request.getSession().removeAttribute("sinDatos");
            }else{%>                   
                <center>
                    <%if(request.getAttribute("mostrarMsje")== null){%>    
                
                        <table id="resultados" border="solid"> 
                            <tr id="subencabezado">
                                <th colspan="4">
                                    <center>
                                        <h2>>>>DATOS DEL USUARIO<<<</h2>
                                    </center>                
                                </th>
                           </tr>
                           <tr>
                                <th>
                                    <h4><strong>Código:</strong></h4>
                               </th>
                               <th>                               
                                   <h5 id="datos">${usuario.getCodigo()}</h5>
                               </th>
                               <th>
                                   <h4><strong>Nombre:</strong></h4>
                               </th>
                               <th>
                                    <h5 id="datos">${usuario.getNombre()}</h5>
                               </th>
                           </tr>
                           <tr>
                               <th>
                                   <h4><strong>Password:</strong></h4>
                               </th>
                               <th>
                                   <h5 id="datos">${usuario.getPassword()}</h5>
                               </th>
                               <th>
                                    <h4><strong>DPI:</strong></h4>
                               </th>
                               <th>
                                   <h5 id="datos">${usuario.getDPI()}</h5>
                              </th>                    
                          </tr>
                          <tr>
                               <th>
                                   <h4><strong>Dirección:</strong></h4>
                               </th>
                               <th>
                                   <h5 id="datos">${usuario.getDireccion()}</h5>
                               </th>
                               <th>
                                   <h4><strong>Sexo:</strong></h4>
                               </th>
                               <th>
                                   <h5 id="datos">${usuario.getSexo()}</h5>
                               </th>
                           </tr>
                           <tr>
                                <th>
                                    <h4><strong>Correo:</strong></h4>
                                </th>
                                <th>
                                    <h5 id="datos">${usuario.getCorreo()}</h5>
                                </th>
                                
                                <%if(request.getAttribute("birth")!=null){%>
                                    <th>
                                        <h4><strong>Cumpleaños:</strong></h4>
                                    </th>
                                    <th>
                                       <h5 id="datos">${usuario.getBirth()}</h5><!--o bien podrías usar birth, para colocar el valor de na vez xD...-->
                                   </th>
                                <%}%>
                                <%if(request.getAttribute("turno")!=null){%>
                                   <th>
                                        <h4><strong>Turno:</strong></h4>
                                   </th>
                                   <th>
                                        <h5 id="datos">${turno.toString()}</h5><!--o bien podrías usar birth, para colocar el valor de na vez xD...-->
                                   </th>
                                <%}%>                        
                           </tr>                                           
                        </table><!--esto debería ser reemplazado por el html del JR...-->                                                                                                  
                        
                        <form method="POST" action="gestorParametrosGerente" ><!--ahí decides si el form solo rodeará a estos btn o rodeará a todo lo que se muestra por haberlos pasado a inputs y así ahorrase espacio...-->                        
                            <%usuario = (Usuario)request.getAttribute("usuario");%>
                            <input type="text" name="trabajador" value="<%=request.getParameter("trabajador")%>" hidden>
                            <input type="text" name="codigoUsuario" value="<%=usuario.getCodigo()%>" hidden>                                            
                        
                            <button type ="submit" id="submit" name="reporte" value="<%="encargado_ResumenCreacion"+((request.getAttribute("turno")!=null)?"Trabajador":"Cliente")%>"><img  src="img/flechitaDescarga.png" style="width: 25px; height: 25px;"> DESCARGAR</button>    
                            <%if(!request.getAttribute("correo").equals("???")){//<!--puesto que será null cuando nunca se haya establecido este valor o el valor sea nulo xD-->                            
                                request.getSession().setAttribute("redireccionPorEnvioMail", "Trabajadores/Gerente/Resultado_Creacion.jsp");
                                request.getSession().setAttribute("cuerpo", cuerpo.darCuerpoPorCreacion(((request.getAttribute("birth")!=null)?"Cliente":"Cajero"), usuario));%>
                                <button type ="submit" id="submit" name="envio" value="resultadoCreacion_<%=usuario.getCodigo()+"_"+((request.getAttribute("birth")!=null)?"Cliente":"Cajero")%>" formaction="gestorEnvioEmail"><img  src="img/avionNegro.png" style="width: 25px; height: 25px;"> ENVIAR POR CORREO</button>    
                            <%}%>
                        </form>                         
                    <%}else if(request.getAttribute("mostrarMsje").equals("error")){%> 
                        <input type="text" id="tipoMsje" value="erroCreacionUsuario" hidden>
                        <script src="js/sweetError.js"></script>                                      
                    <%}else if(request.getAttribute("mostrarMsjeEnvio")!=null){                    
                        if((boolean)request.getAttribute("mostrarMsjeEnvio") == false){%><!--tienes que revisar esto por los datos que se miestran, es decir por si acaso ya no aparecen, lo cual no debeŕia suceder y si es así entonces se debería a la "remoción" del atributo del usuario correspondiente... pero hay que ver de primero xD-->
                            <input type="text" id="tipoMsje" value="errorEnvioMail" hidden>
                            <script src="js/sweetError.js"></script>   
                      <%}%>
                    <%}else{%>    
                        <input type="text" id="tipoMsje" value="CUIrepetido" hidden>
                        <script src="js/sweetError.js"></script>   
                    <%}%>                              
                </center>           
            <%}%>
        <%}%>
    </body>
</html>