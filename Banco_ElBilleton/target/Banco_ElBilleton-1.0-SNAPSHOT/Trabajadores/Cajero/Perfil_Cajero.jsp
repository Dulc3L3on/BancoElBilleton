<%-- 
    Document   : Perfil_Cajero
    Created on : 21/11/2020, 21:02:32
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page import="Modelo.Herramientas.Kit"%>
<%@page import="Modelo.Entidades.Usuarios.Cajero"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssPerfiles.css">
        <link rel="icon" href="../../img/Logos/Favicon_Banco_ElBilleton.ico"><!--se que no se mostrará puesto que no se mostrará por el hecho de ser una página interna, pero mejor se lo agrego xD-->                
        <title>CashierProfile</title>
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           Buscador buscador = new Buscador();
           Cajero cajero;
           Kit herramienta = new Kit();%>
    </head>
    <body>
        <%if(!guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Cajero")){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        <%cajero =(Cajero) buscador.buscarUsuario("Cajero", "codigo",(String) request.getSession().getAttribute("codigo"));%>
         <center>
            <div>                
                <div id="divDatosGerente">
                    <label><h4>CÓDIGO: <%=(cajero!=null)?cajero.getCodigo():""%></h4></label>
                    <img src="../../img/cajero_<%=(cajero!=null)?cajero.getSexo():""%>.jpg" alt="cajero"height="270px" width="270px">
                    <table id="datosGerente">                                                                                                
                        <tr>
                            <th>
                                <h5>Nombre</h5>
                            </th>
                            <th>
                                <h5>Contraseña</h5>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario"  name="nombre" placeholder="Nombre" value="<%=(cajero!=null)?cajero.getNombre():""%>" readonly>
                            </th>
                            <th>
                                <input type="text" id="datosUsuario" name="contrasenia" placeholder="Contraseña" value="<%=(cajero!=null)?herramienta.desencriptarContrasenia(cajero.getPassword()):""%> " readonly>                                 
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <h5>DPI</h5>
                            </th>
                            <th>
                                <h5>Dirección</h5>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <input type="number" id="DPI"  name="DPI" placeholder="DPI" maxlength="13" value="<%=(cajero!=null)?cajero.getDPI():""%>" style="width: 275px; height: 35px;" readonly>
                            </th>
                            <th>
                                <input type="text" id="datosUsuario"  name="direccion" placeholder="Direccion" value="<%=(cajero!=null)?cajero.getDireccion():""%>" readonly>
                            </th>
                        </tr>
                        <tr>
                            <tr>
                                <th>
                                    <h5>Género</h5>
                                </th>
                                <th>
                                    <h5>Turno</h5>
                                </th>
                            </tr>   
                            <th>
                                <input type="text" id="datosUsuario" name="genero" placeholder="Genero" value="<%=(cajero!=null)?cajero.getSexo():""%>" readonly> 
                            </th>
                            <th>
                                <input type="text" id="datosUsuario" name="turno" placeholder="Turno" value="<%=(cajero!=null)?cajero.getTurno():""%>" readonly> 
                                <!--el turno que tiene asignado en ese momento lo mostrarás por medio del dato almacenado en la DB, de tal forma que si es == a matutino [p.ej] entonces que ese adquiera el valor selected si no pues nada es decir ""... simi a lo que hiciste para dejar marcada la opción del menú seleccionada de las páginas principales... [es decir con un ternario...]--><!--pero esto es para el gerente xD, porque sólo él puede modificar sus datos... xD-->
                            </th>
                        </tr>                                          
                    </table>                    
                </div>
            </div>
        </center>
    </body>
</html>
