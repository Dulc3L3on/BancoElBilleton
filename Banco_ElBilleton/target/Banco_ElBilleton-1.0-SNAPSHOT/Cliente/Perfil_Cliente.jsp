<%-- 
    Document   : Perfil_Cliente
    Created on : 21/11/2020, 21:01:23
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.Kit"%>
<%@page import="Modelo.Entidades.Usuarios.Cliente"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/cssPerfiles.css">
        <title>ClientProfile</title>
        <%!Buscador buscador = new Buscador();
           Cliente cliente;
           Kit herramienta = new Kit();%>
    </head>
    <body>
        <%cliente =(Cliente) buscador.buscarUsuario("Cliente", "codigo",(String) request.getSession().getAttribute("codigo"));%>
         <center>
            <div>                
                <div id="divDatosGerente">
                    <label><h4>CÓDIGO: <%=(cliente!=null)?cliente.getCodigo():""%></h4></label>
                    <img src="../img/cliente_<%=(cliente!=null)?cliente.getSexo():""%>.png" alt="cliente"height="370px" width="370px"> 
                    <table id="datosGerente">                                                                                                                                                                             
                        <tr>
                            <th>
                                <h4>Nombre</h4>
                            </th>
                            <th>
                                <h4>Contraseña</h4>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                 <input type="text" id="datosUsuario"  name="nombre" placeholder="Nombre" value="<%=(cliente!=null)?cliente.getNombre():""%>"  readonly>
                            </th>
                            <th>
                                <input type="text" id="datosUsuario" name="contrasenia" placeholder="Contraseña" value="<%=(cliente!=null)?herramienta.desencriptarContrasenia(cliente.getPassword()):""%>" readonly>                                 
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <h4>CUI</h4>
                            </th>
                            <th>
                                <h4>Dirección</h4>
                            </th>
                        </tr>                            
                        <tr>
                            <th>
                                <input type="number" id="DPI"  name="DPI" placeholder="DPI" maxlength="13" value="<%=(cliente!=null)?cliente.getDPI():""%>" style="width: 275px; height: 35px;" readonly>
                            </th>
                            <th>
                                <input type="text" id="datosUsuario"  name="direccion" placeholder="Direccion" value="<%=(cliente!=null)?cliente.getDireccion():""%>" readonly>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <h4>Género</h4>
                            </th>
                            <th>
                                <h4>Cumpleaños</h4>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario" name="genero" placeholder="Genero" value="<%=(cliente!=null)?cliente.getSexo():""%>" readonly> 
                            </th>
                            <th>
                                <input type="date" id="datosUsuario" name="birth" placeholder="Birth" value="<%=(cliente!=null)?cliente.getBirth():""%>" readonly> 
                            </th>
                        </tr>                                          
                    </table>                    
                </div>
            </div>
        </center>
    </body>
</html>
