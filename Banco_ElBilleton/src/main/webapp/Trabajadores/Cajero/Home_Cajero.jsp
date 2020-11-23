<%-- 
    Document   : Home
    Created on : 19/11/2020, 21:41:31
    Author     : phily
--%>

<%@page import="Manejadores.ManejadorDeNavegacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssPaginasPrincipales.css">
        <title>CashierHome</title>
        <%!ManejadorDeNavegacion navegador = new ManejadorDeNavegacion();//<!--al prinicpio [es decir después de loguearse], se llegará aquí por medio del doGet del servlet Perfil o LOgin xD [mejor login xD] luego de crear a la entidad correspondiente [por medio de un parmetro que indicará el tipo al métoodo correspondiente, que por el hecho de no tener que devolver nada puede crearse a los usuarios sin tener que quitarles su mero nombre xD p.ej-> CLIENTE a USUARIO y de instanciar sus valores con el setAttribute[auque por usar singleton, creo que no seá muy útil...creo xD]... de tal forma que al ya estar logueado,s e exe con normalidad este proceso...-->
        String pagina;%> 
    </head>
    <body>
        <%pagina = navegador.darPaginasAlCajero(request.getParameter("opcion"));%>
        <div id="menuCajero">                          
            <form method="POST" accion="Home_Cajero">
                <center>        
                    <br/>
                    <a href="../../gestorLogout"><img src="../../img/off.png" width="50" height="50" id="off"></a>
                    <a href="Home_Cajero.jsp"><img src="../../img/user.png" width="50" height="50" id="perfil"></a>                                       
                    <input type="submit" class="button cajero <%=(pagina.startsWith("Reportes")?"marcadoCajero":"")%>" id="opciones" name="opcion" value="REPORTES" >                    
                    <input type="submit" class="button cajero <%=(pagina.startsWith("Retiro")?"marcadoCajero":"")%>" id="opciones" name="opcion" value="RETIRO" >
                    <input type="submit" class="button cajero <%=(pagina.startsWith("Deposito")?"marcadoCajero":"")%>" id="opciones" name="opcion" value="DEPOSITO" >
                </center>                   
            </form>                                            
        </div>                                        
        <iframe src="<%=pagina%>" id="frameCajero"></iframe>
    </body>
</html>
