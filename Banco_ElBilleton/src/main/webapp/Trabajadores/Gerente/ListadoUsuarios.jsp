<%-- 
    Document   : ListadoUsuarios
    Created on : 22/11/2020, 14:35:34
    Author     : phily
--%>

<%@page import="java.util.List"%>
<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
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
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           Buscador buscador = new Buscador();
           Usuario[] usuarios;
           Kit herramienta = new Kit();
           List<Integer> ubicacionUsuarioBuscado;
           String datosUsuarioBuscado;//<!--a partir del valor que se tome del input search...-->
           String laPagina;%>
    </head>
    <body>    
        <%if(guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Gerente")==false){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        
        <%laPagina = (request.getParameter("pagina")!=null)?request.getParameter("pagina"):laPagina;%>               
        
        <%if(request.getParameter("elemento")!=null){%><!--si no hay elemento es porque no hay usuarios xd, así que no hay problema de un nullPointer, por la lógica de abajito xD--><!--si te da error es por el hecho de estar revisando al botón y no a un input... [pero eso sería lo que deberías hacer teneiendo un listado...[a menos que usaras JS para llenar un input hidden...-->
            <!--haces el valor del arreglo de usarios que corresponde al valor de la búsqueda por medio delcódigo... un atributo de sesión...-->                        
            <%request.getSession().setAttribute("usuarioBuscado_"+((laPagina.contains("Cuenta") || laPagina.contains("Cliente"))?"Cliente":"Cajero"), usuarios[Integer.parseInt(request.getParameter("elemento"))]);%><!--si te da error puede ser esto, pues recuerda que en unas ocasiones es parseando, en otrar conviertirendo pero usando toString en el parámetro... [revisa la vrs anteriror del proyecto xD-->
       <%}%>  
       <%usuarios = buscador.buscarUsuarios((laPagina.contains("Cuenta") || laPagina.contains("Cliente"))?"Cliente":"Cajero", (request.getParameter("tipoOrden")==null)?"codigo":request.getParameter("tipoOrden"));%>        
       <iframe src="<%=(laPagina)!=null?laPagina:""%>" id="frameConListado"></iframe>
       
        <div id="frameListado">   
            <div id="bannerBusqueda">
                <form method="POST" action="ListadoUsuarios.jsp"><!--si hay un fallo será porque lo de abajo no redirecciona bien a esta misma página, pero no debería suceder porque recuerda que depósito del billetón anterior, redirigía a la misma pág de depósito y tambien estaba refundida xD...-->                                                                        
                    <table style="width: 325px;">
                        <tr>
                            <th>
                                <input type="search" name="unUsuario" placeholder="Código ó Nombre usuario" style="width: 225px; height: 35px;"><!--No asocie la lista porque en google se mira feo... pues siempre muestra el listado completo y eso no es lo que quiero, sino que sea como en firefox, muestra el listado de las coincidencias cuando se ha escrito algo, de lo contrario no...-->                                
                                
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
                                    <input type="radio" name="tipoOrden" value ="Codigo" id="radio1" class="radio" checked>
                                    <label for="radio1">Codigo</label> 
                            
                                    <input type="radio" name="tipoOrden" value ="Nombre" id="radio2" class="radio">
                                    <label for="radio2">Nombre</label>                                                                
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

                                datosUsuarioBuscado = request.getParameter("unUsuario").trim();//el trim solo quita los espacios de los extremos, así que seguriá funcionando con normalidad xD
                                if(request.getParameter("tipoOrden").equals("Nombre")){
                                   usuarios = buscador.buscarUsuarios((laPagina.contains("Cuenta") || laPagina.contains("Cliente"))?"Cliente":"Cajero", "nombre");
                                }
                                ubicacionUsuarioBuscado = herramienta.buscarUbicacionUsuarioBuscado((request.getParameter("tipoOrden")==null?"codigo":request.getParameter("tipoOrden")),datosUsuarioBuscado, usuarios);//no creo que sea necesario colocar el split puesto que no se vinculó el dataList, por el hecho de que no se veía bien xD
                                
                                if(!ubicacionUsuarioBuscado.isEmpty()){%><!--no provoca problemas el hecho de que esté vacía pero al estar así sabré si hubieron coincidencias o no xD-->
                                    <%for (int usuarioActual = 0; usuarioActual <ubicacionUsuarioBuscado.size(); usuarioActual++) {%>
                                        <button type="submit" name="elemento" id="elemento" value="<%=ubicacionUsuarioBuscado.get(usuarioActual)%>"><%=usuarios[ubicacionUsuarioBuscado.get(usuarioActual)].getCodigo()%> <%=usuarios[ubicacionUsuarioBuscado.get(usuarioActual)].getNombre()%></button>
                                    <%}%>                                                                   
                                <%}else{%>
                                    <center><p>-Sin coincidencias-</p></center><!--ya sea porque de verdad no existe o porque surgió un error xD-->
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