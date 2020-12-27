<%-- 
    Document   : Modificacion_Cliente
    Created on : 22/11/2020, 18:03:21
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page import="Controladores.GestorCargaDPI"%>
<%@page import="Modelo.Herramientas.Kit"%>
<%@page import="Modelo.Entidades.Usuarios.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssGerente.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>         
        <link rel="icon" href="../../img/Logos/Favicon_Banco_ElBilleton.ico"><!--se que no se mostrará puesto que no se mostrará por el hecho de ser una página interna, pero mejor se lo agrego xD-->        
        
        <title>ClientModify</title>
        <%!GuardiaSeguridad guardia= new GuardiaSeguridad();
           Cliente cliente;           
           Kit herramienta = new Kit();%>
    </head>
    <body>
        <%if(guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Gerente")==false){%>            
            <%response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        <%if(guardia.estaEnHorario("Gerente", (String) request.getSession().getAttribute("codigo"))==false){%>
            <input type="text" id="tipoMsje" value="fueraDeHorario" hidden>
            <script src="../../js/sweetInformativo.js"></script><!--recuerda que veremos cómo está la apariencia de la página cuando se redireccione ella misma hacia aquí para add o no el sweet con una dir menos profunda o no xD-->
        <%}else{%>
        
            <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
                <%cliente = (Cliente)request.getSession().getAttribute("usuarioBuscado_Cliente");
                  if(cliente!=null){%><!--es que no tiene chiste mostrar los campos si no se encontró al usaurio...-->                      
                      
                    <form method="POST" enctype="multipart/form-data" action="../../recargaDPI">
                        <center>
                            <%if(cliente!=null){%>
                                <iframe src="<%=(cliente!=null)?"/Banco_ElBilleton/Trabajadores/Gerente/DPIsClientes/"+cliente.getPathDPI():""%>" style="width:750px; height:270px; border: solid;"></iframe><!--pendiente-->
                            <%}%>
                        </center>  
                        <div id="contenedorGeneral">
                            <table style="margin-top: -25px;">                                
                                <tr>
                                    <th colspan="2">
                                        <h3>>>DATOS INMUTABLES</h3>
                                    </th>
                                </tr>
                                <tr id="nombresDatos">
                                    <th>
                                        <h5 id="subtitulo">Código</h5>
                                    </th>
                                    <th>
                                        <input type="number" name="datosActualizar" value ="<%=(cliente!=null)?cliente.getCodigo():""%>" id="codigoCliente" readonly>                                
                                    </th>                            
                                </tr>
                                <tr>                            
                                    <th>
                                        <h5 id="subtitulo">CUI</h5>
                                    </th>
                                    <th>
                                        <input type="number" name="datosActualizar" value ="<%=(cliente!=null)?cliente.getDPI():""%>" id="CUI" readonly>
                                    </th>  
                                </tr>                       
                                <tr id="nombresDatos">
                                    <th>
                                        <h5 id="subtitulo">Género</h5>
                                    </th>
                                    <th>
                                        <input type="text" name="datosActualizar" id="genero" value ="<%=(cliente!=null)?cliente.getSexo():""%>" readonly>                                
                                    </th>                            
                                </tr>
                                <tr>                            
                                    <th>
                                        <h5 id="subtitulo">Fecha Nacimiento</h5>
                                    </th>
                                    <th>
                                        <input type="date" name="datosActualizar" id="birth" value ="<%=(cliente!=null)?cliente.getBirth():""%>" style="width: 150px;" readonly>
                                    </th>  
                                </tr>   
                                <tr>                            
                                    <th colspan="2">
                                        <h3>>>DATOS MODIFICABLES</h3>
                                    </th>
                                </tr>    
                                <tr id="nombresDatos">                            
                                    <th>
                                        <h5 id="subtitulo">+ Nombre</h5>
                                   </th>
                                   <th>
                                       <input type="text" name="datosActualizar" id="nombre" placeholde="Nombre" value ="<%=(cliente!=null)?cliente.getNombre():""%>" required>
                                   </th>                            
                                </tr>
                                <tr>                            
                                    <th>
                                        <h5 id="subtitulo">PDF DPI</h5>                                
                                    </th>                          
                                    <th>
                                        <input type="text" name="datosActualizar" value="<%=(cliente!=null)?cliente.getPathDPI():""%>" id="pathDPI" hidden>
                                        <input type="file" name="archDPI" accept=".pdf" id="dpi">
                                    </th>
                                </tr>
                                <tr id="nombresDatos">                            
                                    <th>
                                        <h5 id="subtitulo">+ Dirección</h5>
                                    </th>
                                   <th>
                                        <input type="text" name="datosActualizar" id="direccion" value ="<%=(cliente!=null)?cliente.getDireccion():""%>" required>
                                   </th>                            
                                </tr>
                                <tr>                            
                                    <th>
                                        <h5 id="subtitulo">+ Contraseña</h5>                                
                                    </th>                          
                                    <th>
                                        <input type="text" name="datosActualizar" id="contrasenia" value ="<%=(cliente!=null)?cliente.getPassword():""%>" hidden><!--esta será la enviada a la DB si es que no se seleccionó el checkBox... xD-->
                                       <label><input type="checkbox" name="nuevaContrasenia" id="checkBox" value="true">Generar contraseña aleatoria</label>
                                    </th>
                                </tr>
                                <tr id="nombresDatos">                            
                                    <th>
                                        <h5 id="subtitulo">Correo electrónico</h5>
                                    </th>
                                   <th>
                                       <input type="email" name="datosActualizar" id="correo" value ="<%=(cliente!=null)?(cliente.getCorreo().equals("???")?"":cliente.getCorreo()):""%>">
                                   </th>                            
                                </tr>
                                <tr>
                                    <th colspan="2">
                                        <h6><i>+ no es necesario modificarlo, pero no debe estar vacío al momento de presionar "MODIFICAR"</i></h6>
                                    </th>
                                </tr>
                            </table>
                            <%if(request.getSession().getAttribute("usuarioBuscado_Cliente")!=null){%>  
                                <input type="text" name="tipoUsuario" value="Cliente" hidden>
                                <input type="text" name="datosUsuario" value="<%=cliente.getCodigo()%>" hidden>
                                <input type="submit" id="submit" name="modificar" value="MODIFICAR CLIENTE">
                            <%}%>
                        </div>
                    </form>              
                  
                <%}else if(request.getSession().getAttribute("usuarioBuscado_Cliente")!=null && cliente==null){%><!--así que el operador ternario para cuando el cliente sea null, está de más puesto que no se mostrarán los comp... xD-->
                        <input type="text" id="tipoMsje" value="errorBusquedaUsuario" hidden>
                        <script src="../../js/sweetError.js"></script> 
                  <%}%>             
            </center>                 
      <%}%>
    </body>
</html>
