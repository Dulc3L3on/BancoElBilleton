<%-- 
    Document   : Modificacion_Cajero
    Created on : 22/11/2020, 18:24:21
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.Kit"%>
<%@page import="Modelo.Entidades.Usuarios.Cajero"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssGerente.css">
        <title>CashierModify</title>
        <%!Cajero cajero;
           String desencriptada;
           Kit herramienta = new Kit();%>
    </head>
     <body>
        <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
            <%cajero = (Cajero)request.getSession().getAttribute("usuarioBuscado_Cajero");
              if(cajero!=null){
                  desencriptada = herramienta.desencriptarContrasenia(cajero.getPassword());
              }else{
                  desencriptada = null;
              }%>
            <form method="POST" action="../../gestorModificacionCajero"><!--o gestorModificación xD, esto es si logras generalizar xD-->
                <div id="contenedorGeneral">
                    <input type="text" name="tipoTrabajador" value="Cajero" hidden><!--creo que eso lo había hecho pensando que el gerente y el cajero compartirían el gestor de modificación...porque ahora que revisaba... no tiene razón de ser xD-->
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
                                <input type="number" name="datosActualizar" value ="<%=(cajero!=null)?cajero.getCodigo():""%>" id="codigoCajero" readonly>                                
                            </th>                            
                        </tr>
                        <tr>                            
                            <th>
                                <h5 id="subtitulo">CUI</h5>
                            </th>
                            <th>
                                <input type="number" name="datosActualizar" value ="<%=(cajero!=null)?cajero.getDPI():""%>" id="CUI" readonly>
                            </th>  
                        </tr>                       
                        <tr id="nombresDatos"><!--no creo que de problemas este ID xD-->
                            <th>
                                <h5 id="subtitulo">Género</h5>
                            </th>                                                                                                       
                            <th>
                                <input type="text" name="datosActualizar" id="genero" value ="<%=(cajero!=null)?cajero.getSexo():""%>" readonly>
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
                                <input type="text" name="datosActualizar" id="nombre" placeholde="Nombre" value ="<%=(cajero!=null)?cajero.getNombre():""%>" required>
                           </th>                            
                        </tr>
                        <tr>                            
                            <th>
                                <h5 id="subtitulo">+ Dirección</h5>                                 
                            </th>                          
                            <th>
                                <input type="text" name="datosActualizar" value ="<%=(cajero!=null)?cajero.getDireccion():""%>" id="direccion" required>
                            </th>
                        </tr>
                        <tr id="nombresDatos">                            
                            <th>
                               <h5 id="subtitulo">+ Contraseña</h5> 
                            </th>
                           <th>                                
                               <input type="text" name="datosActualizar" id="contrasenia" value ="<%=(desencriptada!=null)?desencriptada:""%>" hidden><!--esta será la enviada a la DB si es que no se seleccionó el checkBox... xD-->
                               <label><input type="checkbox" name="nuevaContrasenia" id="checkBox" value="true">Generar contraseña aleatoria</label>
                           </th>                            
                        </tr>
                        <tr>                            
                            <th>
                                <h5 id="subtitulo">+ Turno</h5>
                            </th>                          
                            <th>
                                <select name="datosActualizar" id="turno" style="width: 250px" readonly><!--de igual forma seleccionarás el tipo de turno, por medio de un operador ternario y la info que tenga almacenada el cajero para ese atriButo xD-->
                                    <option value="matutino" <%=(cajero!=null)?((cajero.getTurno().equals("matutino"))?"selected":""):""%>>Matutino</option>
                                    <option value="vespertino" <%=(cajero!=null)?((cajero.getTurno().equals("vespertino"))?"selected":""):""%>>Vespertino</option>
                                </select>
                            </th>
                        </tr>
                        <tr>
                            <th colspan="2">
                                <h6><i>+ no es necesario modificarlo, pero no debe estar vacío al momento de presionar "MODIFICAR"</i></h6>
                            </th>
                        </tr>
                    </table>
                                
                    <%if(request.getSession().getAttribute("usuarioBuscado_Cajero")!=null)%>  
                        <input type="submit" id="submit" name="modificar" value="MODIFICAR CAJERO">
                    <%%>
                </div>
            </form>            
        </center>                 
    </body>
</html><!--recuerda que sería bueno que se mandara al correo que el usuario brindara de tal forma que tuviera la copia de la contraseña.... esto si es que solicit+o una nueva... eso sí sería con JS... creo xD-->
