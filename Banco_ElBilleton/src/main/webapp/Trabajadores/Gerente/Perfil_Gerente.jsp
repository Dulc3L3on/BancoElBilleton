<%-- 
    Document   : Perfil_Gerente
    Created on : 21/11/2020, 21:02:46
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.Kit"%>
<%@page import="Modelo.Entidades.Usuarios.Gerente"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssPerfiles.css">        
        <title>ManagerProfile</title>
        <%!Buscador buscador = new Buscador();
           Gerente gerente;
           Kit herramienta = new Kit();%>
    </head>
    <body>
        <%gerente = (Gerente)buscador.buscarUsuario("Gerente", "codigo",(String) request.getSession().getAttribute("codigo"));%>
        <center>
            <div>                
                <div id="divDatosGerente">
                    <form method="POST" action="../../gestorModificacionGerente">
                        <label style="margin-top: 15px;"><h4>CÓDIGO: <%=(gerente!=null)?gerente.getCodigo():""%></h4></label>
                        <img src="../../img/gerente_<%=(gerente!=null)?gerente.getSexo():"femenino"%>.jpg" alt="gerente"height="270px" width="270px"> 
                        <input type="text" name="datosActualizar" value="<%=(gerente!=null)?gerente.getCodigo():""%>" hidden>
                        <table id="datosGerente">                                                                                                
                            <tr>
                                <th>
                                    <h5>+ Nombre</h5>
                                </th>
                                <th>
                                    <h5>+ Contraseña</h5>
                                </th>                                
                            </tr>
                            <tr>
                                <th>
                                    <input type="text" id="datosUsuario"  name="datosActualizar" placeholder="Nombre" value="<%=(gerente!=null)?gerente.getNombre():""%>"  required>
                                </th>
                                <th>
                                    <input type="text" id="datosUsuario" name="datosActualizar" placeholder="Contraseña" value="<%=(gerente!=null)?herramienta.desencriptarContrasenia(gerente.getPassword()):""%>"><!--por ser gerente se le dará el privilegio de poder crear una contraseña personalizada para él xD-->
                                </th>                                  
                            </tr>
                                <tr> 
                                    <th>
                                        <h5>+ DPI</h5>
                                    </th>                                
                                    <th>
                                        <h5>+ Dirección</h5>
                                    </th>                                    
                                </tr>
                                <tr>   
                                    <th>
                                        <input type="number" id="DPI"  name="datosActualizar" placeholder="DPI" maxlength="13" value="<%=(gerente!=null)?gerente.getDPI():""%>" style="width: 275px; height: 35px;" readonly>
                                    </th>                                
                                    <th>
                                        <input type="text" id="datosUsuario"  name="datosActualizar" placeholder="Direccion" value="<%=(gerente!=null)?gerente.getDireccion():""%>" required>
                                    </th>                                   
                                </tr>
                                <tr>
                                    <th>
                                        <h5>Género</h5>
                                    </th>
                                    <th>
                                        <h5>+ Turno</h5>
                                    </th>
                                </tr>
                                <tr>
                                    <th>
                                        <input type="text" id="datosUsuario" name="datosActualizar" placeholder="Genero" value="<%=(gerente!=null)?gerente.getSexo():""%>" readonly> 
                                    </th>                             
                                    <th>
                                        <select name="datosActualizar" id="turno" style="width: 293px; height: 40px;">
                                            <option value="matutino" <%=(gerente!=null)?(gerente.getTurno().equals("matutino")?"selected":""):""%>>Matutino</option><!--no creo que sea necesario poner un vacío en el valor... creo que con no declararlo basta...-->                                    
                                            <option value="vespertino" <%=(gerente!=null)?(gerente.getTurno().equals("vespertino")?"selected":""):""%>>Vespertino</option>
                                        </select><!--el turno que tiene asignado en ese momento lo mostrarás por medio del dato almacenado en la DB, de tal forma que si es == a matutino [p.ej] entonces que ese adquiera el valor selected si no pues nada es decir ""... simi a lo que hiciste para dejar marcada la opción del menú seleccionada de las páginas principales... [es decir con un ternario...]-->
                                    </th>
                                </tr>     
                                <tr>
                                    <th colspan="2">
                                        <h6>+ no es necesaria su modificación, pero si se requiere que al presionar modificar posea contenido</h6>
                                    </th>
                                </tr>
                                <tr>
                                    <th colspan="2">
                                        <%if(gerente!=null){%>
                                            <input type="submit" id="acciones" name="accion"  value="MODIFICAR">          
                                        <%}%>
                                    </th>
                                </tr>
                            </table>  
                                
                        <%if(request.getAttribute("mostrarMsje")!=null){%>                            
                            <!--se llama al msje de bienvenida...-->
                        <%}%>
                    </form>                        
                </div>
            </div>
        </center>        
    </body>
</html>
