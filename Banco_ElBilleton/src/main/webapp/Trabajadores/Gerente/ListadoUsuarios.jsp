<%-- 
    Document   : ListadoUsuarios
    Created on : 22/11/2020, 14:35:34
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssGerente.css">
        <title>UserList</title>        
    </head>
    <body>                   
        <iframe src="<%=(request.getParameter("pagina"))!=null?request.getParameter("pagina"):""%>" id="accionConListado"></iframe>            
        <%if(request.getParameter("elemento")!=null){%><!--si te da error es por el hecho de estar revisando al botón y no a un input... [pero eso sería lo que deberías hacer teneiendo un listado...[a menos que usaras JS para llenar un input hidden...-->
            <!--haces el valor del arreglo de usarios que corresponde al valor del REQUEST un atributo de sesión...-->            
        <%}%>               
        <div id="FrameListado">   
            <div id="bannerBusqueda">
                <form method="POST" action="ListadoUsuarios.jsp"><!--si hay un fallo será porque lo de abajo no redirecciona bien a esta misma página, pero no debería suceder porque recuerda que depósito del billetón anterior, redirigía a la misma pág de depósito y tambien estaba refundida xD...-->
                    <table style="width: 325px;">
                        <tr>
                            <th>
                                <input type="text" name="unUsuario" placeholder="Código usuario" style="width: 190px; height: 35px;">                    
                            </th>
                            <th>
                               <input type="submit" name="submit" id="submit" value="BUSCAR" style="width: 100px; height: 35px;">
                            </th>
                        </tr>
                    </table>                                           
                </form>                        
           </div>
           <center>
                <br/><br/>
                <form method="POST" action="ListadoUsuarios.jsp">
                    <div id="ListadoUsuarios">
                       <%if(request.getParameter("unUsuario")==null){%>
                            <!--aquí se utilizaría el método de búsqueda, según lo que contenga el parámetro página, para formar el arreglo que será útil para hacer el listado de inputs corresp con valor = al número de ubicación en el arreglo y para establecer el atributo de sesión... que luego deberás eliminar para evitar problemas xD-->
                        
                            <%for(int numeroUsuario=0; numeroUsuario< 15; numeroUsuario++){%>
                                 <button type="submit" name="elemento" id="elemento" value="<%=numeroUsuario%>">usuarioBuscado[numeroUsuario].darNombre() <%=numeroUsuario%></button><br/><br/>
                            <%}%>
                       <%}else{%>
                            <!--aquí se hace la búsqueda de un solo usuario que tb depende del lo que contenga la query string, solo que devolvería un arreglo de un solo elemento... para que se pueda trabajar con arreglos y no se tengan que colocar más condiciones......-->
                            <button type="submit" name="elemento" id="elemento" value="0">usuarioBuscado[0].darNombre() usuarioBuscado[0].darCodigo()</button>
                       <%}%>
                    </div>
                 </form>                                   
            </center>            
        </div>        
    </body>
</html>
