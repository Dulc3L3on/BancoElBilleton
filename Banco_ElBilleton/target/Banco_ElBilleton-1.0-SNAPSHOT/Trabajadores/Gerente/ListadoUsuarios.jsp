<%-- 
    Document   : ListadoUsuarios
    Created on : 22/11/2020, 14:35:34
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.Kit"%>
<%@page import="Modelo.Entidades.Usuarios.Usuario"%>
<%@page import="Modelo.Entidades.Usuarios.Cliente"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssGerente.css">
        <title>UserList</title>  
        <%!Buscador buscador = new Buscador();
           Usuario[] usuarios;
           Kit herramienta = new Kit();
           int ubicacionUsuarioBuscado;
           String[] datosUsuarioBuscado;//<!--a partir del valor que se tome del input search...-->
           String laPagina;%>
    </head>
    <body>           
        <%laPagina = (request.getParameter("pagina")!=null)?request.getParameter("pagina"):laPagina;%>
               
        <%usuarios = buscador.buscarUsuario((laPagina.contains("Cuenta") || laPagina.contains("Cliente"))?"Cliente":"Cajero", (request.getParameter("tipoOrden")==null)?"codigo":request.getParameter("tipoOrden"));%>        
        <%if(request.getParameter("elemento")!=null){%><!--si no hay elemento es porque no hay usuarios xd, así que no hay problema de un nullPointer, por la lógica de abajito xD--><!--si te da error es por el hecho de estar revisando al botón y no a un input... [pero eso sería lo que deberías hacer teneiendo un listado...[a menos que usaras JS para llenar un input hidden...-->
            <!--haces el valor del arreglo de usarios que corresponde al valor de la búsqueda por medio delcódigo... un atributo de sesión...-->                        
            <%request.getSession().setAttribute("usuarioBuscado_"+((laPagina.contains("Cuenta") || laPagina.contains("Cliente"))?"Cliente":"Cajero"), usuarios[Integer.parseInt(request.getParameter("elemento"))]);%><!--si te da error puede ser esto, pues recuerda que en unas ocasiones es parseando, en otrar conviertirendo pero usando toString en el parámetro... [revisa la vrs anteriror del proyecto xD-->
       <%}%>  
       <iframe src="<%=(laPagina)!=null?laPagina:""%>" id="frameConListado"></iframe>
       
        <div id="FrameListado">   
            <div id="bannerBusqueda">
                <form method="POST" action="ListadoUsuarios.jsp"><!--si hay un fallo será porque lo de abajo no redirecciona bien a esta misma página, pero no debería suceder porque recuerda que depósito del billetón anterior, redirigía a la misma pág de depósito y tambien estaba refundida xD...-->                                                                        
                    <table style="width: 325px;">
                        <tr>
                            <th>
                                <input type="search" name="unUsuario" placeholder="Código usuario" style="width: 225px; height: 35px;"><!--No asocie la lista porque en google se mira feo... pues siempre muestra el listado completo y eso no es lo que quiero, sino que sea como en firefox, muestra el listado de las coincidencias cuando se ha escrito algo, de lo contrario no...-->                                
                                
                                <%if(usuarios!=null){%><!--no se colocará pues sería muy redundante [y aún más en Chrome, puesto que muestra a todos los elementos y no  a los coincidentes...-->
                                    <datalist id="listaUsuarios">
                                        <%for(int usuarioActual=0; usuarioActual<usuarios.length; usuarioActual++){%>                                    
                                            <option value="<%=usuarios[usuarioActual].getCodigo()%> <%=usuarios[usuarioActual].getNombre()%>"> </option> 
                                        <%}%>
                                    </datalist>
                                <%}%>
                            </th>
                            <th>
                               <input type="submit" name="submit" id="submit" value="BUSCAR" style="width: 100px; height: 35px;">
                            </th>
                        </tr>
                        <tr>
                            <th colspan="2">                                
                                <center>
                                    <input type="radio" name="tipoOrden" value ="Codigo" id="radio" checked>
                                    <label for="Codigo">Codigo</label> 
                            
                                    <input type="radio" name="tipoOrden" value ="Nombre" id="radio">
                                    <label for="Nombre">Nombre</label>                                                                
                                </center>                                
                            </th>
                        </tr>                            
                    </table>                                           
                </form>                        
           </div>           
             
           <center>
                <br/><br/>
                <form method="POST" action="ListadoUsuarios.jsp">
                    <div id="ListadoUsuarios">
                       <%if(usuarios!=null){%>
                            <%if(request.getParameter("unUsuario")==null || request.getParameter("unUsuario").isBlank()){%><!--es un hecho que habrán usuarios... pero xD por si las moscas xd-->
                                <!--aquí se utilizaría el método de búsqueda, según lo que contenga el parámetro página, para formar el arreglo que será útil para hacer el listado de inputs corresp con valor = al número de ubicación en el arreglo y para establecer el atributo de sesión... que luego deberás eliminar para evitar problemas xD-->
                            
                                <%for(int numeroUsuario=0; numeroUsuario< usuarios.length; numeroUsuario++){%>
                                     <button type="submit" name="elemento" id="elemento" value="<%=numeroUsuario%>"><%=usuarios[numeroUsuario].getCodigo()%> <%=usuarios[numeroUsuario].getNombre()%></button><br/><br/>
                                <%}%>
                           <%}else{
                                //se hace la búsqueda en el listado de usuarios hallado a partir del código que se encuentra en el input search...

                                datosUsuarioBuscado = request.getParameter("unUsuario").split(" ");
                                ubicacionUsuarioBuscado = herramienta.buscarUbicacionUsuarioBuscado(datosUsuarioBuscado[0], usuarios);
                                
                                if(ubicacionUsuarioBuscado!=-1){%>
                                    <button type="submit" name="elemento" id="elemento" value="<%=ubicacionUsuarioBuscado%>"><%=usuarios[ubicacionUsuarioBuscado].getCodigo()%> <%=usuarios[ubicacionUsuarioBuscado].getNombre()%></button>
                                <%}else{%>
                                    <center><p>-Sin coincidencias-</p></center>
                                <%}%>
                           <%}%>
                        <%}else{%>
                            <center><p>-Sin existencia de clientes-</p></center>
                        <%}%>
                    </div>
                 </form>                                   
            </center>            
        </div>        
    </body>
</html><!--todo esto lo hice suponiendo que "usuarios" no se hace nulo al volver... lo cual dobi no puede suceder,por el hecho de que al volver se hace otra vez la búsuqeda... y aunque se eli o add usuarios al istema mientras se regresaba al JSP [y por ello el arreglo de users cambió], no habrá problemas puesto que el usuario se busca por código xD-->
<!--TE RECUERDAS de eliminar [o invalidar xD] el atrib de sesión del usuario, luego de que las cosas salieran bien [o mal, creo xD] porque si no seguirá mostrando los datos la o las páginas [puesto que tendrá el mismo nombre para los dif JSP's...] y eso si estaría mal...-->