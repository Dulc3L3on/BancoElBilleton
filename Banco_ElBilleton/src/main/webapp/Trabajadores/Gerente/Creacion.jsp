<%-- 
    Document   : Creacion
    Created on : 20/11/2020, 21:11:32
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page import="Modelo.Manejadores.ManejadorDeNavegacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Creation</title>
        <link rel="stylesheet" href="../../css/cssPaginasAcciones.css">
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
        ManejadorDeNavegacion navegador = new ManejadorDeNavegacion();//<!--al prinicpio [es decir después de loguearse], se llegará aquí por medio del doGet del servlet Perfil o LOgin xD [mejor login xD] luego de crear a la entidad correspondiente [por medio de un parmetro que indicará el tipo al métoodo correspondiente, que por el hecho de no tener que devolver nada puede crearse a los usuarios sin tener que quitarles su mero nombre xD p.ej-> CLIENTE a USUARIO y de instanciar sus valores con el setAttribute[auque por usar singleton, creo que no seá muy útil...creo xD]... de tal forma que al ya estar logueado,s e exe con normalidad este proceso...-->
        String pagina;%> 
    </head>
    <body>
        <%if(!guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Gerente") && !guardia.estaEnHorario("Gerente", (String) request.getSession().getAttribute("codigo"))){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        
        <%pagina = navegador.darPaginasCreacion(request.getParameter("opcion"));%>        
        <div class="submenuAcciones creacion">
            <form method="POST" action="Creacion.jsp">
                <input type="submit" class="button <%=(pagina.contains("Cuenta")?"marcadoAcciones":"")%>" id="submit" name="opcion" value="Cuenta">
                <input type="submit" class="button <%=(pagina.contains("CAJERO")?"marcadoAcciones":"")%>" id="submit" name="opcion" value="Cajero">
                <input type="submit" class="button <%=(pagina.contains("Cliente")?"marcadoAcciones":"")%>" id="submit" name="opcion" value="Cliente">
                <input type="submit" class="button <%=(pagina.contains("GERENTE")?"marcadoAcciones":"")%>" id="submit" name="opcion" value="Gerente">
            </form>            
        </div>        
        <!--igual que con la página principal, debería tener un métood para dar la ágina que corresponde...-->
        <iframe src="<%=pagina%>" title="Creacion" id="frameAccion"></iframe><!--no es necesario el título xd-->
    </body>
</html>
<!--debes ver si queda bien colocando la franja horizontal, con el div que contiene los btn para ir a la página que corresponde [eso quiere decir que deberías tener 1 pág por cada una de las modif [bueno eso ya lo tienes, pero por el hecho del listado, sería mejor hacer la excogenvia de cual mostrar con el gestor correspondiente [para que así solo sea una pág... pero al final de cuentas la pág en donde se encuentran los form para ingresar la info, son diferentes, así que sí! podría tenerse 1 pag para cada tipo...-->
<!--si no se llegara a ver bien ll horizontal [o provocara dificultades mayores en la progra, lo cual se daría a notar talvez hasa que estes codificando la parte que representa  o a la que redirecciona...[que haría dedicaras más tiepo a ver esto antes de codigicar lo que corresponde, entonces harás el menu vert... simi al de antes, solo que con colores sóligos y con el hover de ahora [colorear o bordear xD]-->

<!--ahí te recuerdas de pensar si vas a dar a notar cual seleccionó del sumenucito, así como el menu grande xD-->