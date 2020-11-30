<%-- 
    Document   : Modificacion_Cliente
    Created on : 22/11/2020, 18:03:21
    Author     : phily
--%>

<%@page import="Controladores.GestorCargaDPI"%>
<%@page import="Modelo.Herramientas.Kit"%>
<%@page import="Modelo.Entidades.Usuarios.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssGerente.css">
        <title>ClientModify</title>
        <%!Cliente cliente;
           String desencriptada;
           Kit herramienta = new Kit();%>
    </head>
    <body>
        <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
            <%cliente = (Cliente)request.getSession().getAttribute("usuarioBuscado_Cliente");
              if(cliente!=null){
                  desencriptada = herramienta.desencriptarContrasenia(cliente.getPassword());
              }else{
                  desencriptada = null;
              }%> 
            <form method="POST" enctype="multipart/form-data" action="../../recargaDPI">
                <center>
                    <%if(cliente!=null){%>
                        <iframe src="<%=GestorCargaDPI.BASE_PATH%>/<%=(cliente!=null)?cliente.getPathDPI():""%>" style="width:750px; height:270px; border: solid;"></iframe>                                                                              
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
                                <input type="text" name="datosActualizar" id="contrasenia" value ="<%=(desencriptada!=null)?desencriptada:""%>" hidden><!--esta será la enviada a la DB si es que no se seleccionó el checkBox... xD-->
                               <label><input type="checkbox" name="nuevaContrasenia" id="checkBox" value="true">Generar contraseña aleatoria</label>
                            </th>
                        </tr>
                        <tr>
                            <th colspan="2">
                                <h6><i>+ no es necesario modificarlo, pero no debe estar vacío al momento de presionar "MODIFICAR"</i></h6>
                            </th>
                        </tr>
                    </table>
                    <%if(request.getSession().getAttribute("usuarioBuscado_Cliente")!=null){%>  
                        <input type="submit" id="submit" name="modificar" value="MODIFICAR CLIENTE">
                    <%}%>
                </div>
            </form>            
        </center>                 
    </body>
</html>
