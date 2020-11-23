<%-- 
    Document   : Modificacion
    Created on : 21/11/2020, 06:19:34
    Author     : phily
--%>

<%@page import="Manejadores.ManejadorDeNavegacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssPaginasAcciones.css">
        <title>Modify</title>
         <%!ManejadorDeNavegacion navegador = new ManejadorDeNavegacion();//<!--al prinicpio [es decir después de loguearse], se llegará aquí por medio del doGet del servlet Perfil o LOgin xD [mejor login xD] luego de crear a la entidad correspondiente [por medio de un parmetro que indicará el tipo al métoodo correspondiente, que por el hecho de no tener que devolver nada puede crearse a los usuarios sin tener que quitarles su mero nombre xD p.ej-> CLIENTE a USUARIO y de instanciar sus valores con el setAttribute[auque por usar singleton, creo que no seá muy útil...creo xD]... de tal forma que al ya estar logueado,s e exe con normalidad este proceso...-->
        String pagina;%> 
    </head>
    <body>
          <%pagina = navegador.darPaginasModificacion(request.getParameter("opcion"));%>
        <div class="submenuAcciones modificacion">
            <form method="POST" action="Modificacion.jsp">
                <input type="submit" class="button <%=(pagina.contains("Cliente")?"marcadoAcciones":"")%>" id="submit" name="opcion" value="Cliente">
                <input type="submit" class="button <%=(pagina.contains("Cajero")?"marcadoAcciones":"")%>" id="submit" name="opcion" value="Cajero"><!--si llegar a haber problema con el hecho de tener los mismo nombre para los input, entonces solo camiale el nombre a cada grupo xd, aunque lo dudo por el hecho de ser realizados los procesos por el correspondiente servlet general de cada "agrupación"-->
            </form>            
        </div>
        
        <!--igual que con la página principal, debería tener un métood para dar la ágina que corresponde...-->
        <iframe src="<%=pagina%>" title="Modificacion" id="frameAccion"></iframe>
    </body>
</html>
