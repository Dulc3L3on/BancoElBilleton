<%-- 
    Document   : Asociacion
    Created on : 21/11/2020, 09:50:38
    Author     : phily
--%>

<%@page import="Modelo.Manejadores.ManejadorDeNavegacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/cssCliente.css">
        <title>Association</title>
        <%!ManejadorDeNavegacion navegador = new ManejadorDeNavegacion();//<!--al prinicpio [es decir después de loguearse], se llegará aquí por medio del doGet del servlet Perfil o LOgin xD [mejor login xD] luego de crear a la entidad correspondiente [por medio de un parmetro que indicará el tipo al métoodo correspondiente, que por el hecho de no tener que devolver nada puede crearse a los usuarios sin tener que quitarles su mero nombre xD p.ej-> CLIENTE a USUARIO y de instanciar sus valores con el setAttribute[auque por usar singleton, creo que no seá muy útil...creo xD]... de tal forma que al ya estar logueado,s e exe con normalidad este proceso...-->
        String pagina;%> 
    </head>
    <body>
        <%pagina = navegador.darPaginasAsociacion(request.getParameter("opcion"));%><!--igaul aquó podrías tener como default una página != a la de enviar o recibidas xd que mostrara los pasos para hacer la solicitud...-->       
        
        <div class="submenuAcciones asociacion">
            <form method="POST" action="Asociacion.jsp">
                <button type="submit" class="button" id="submit" name="opcion" value="ENVIAR"><center><img src="../img/Icono_EnviarSoliAso.png" height="90px" width="90px" alt="enviar"></center></button><br/><br/>
                <button type="submit" class="button" id="submit" name="opcion" value="RECIBIDAS"><center><img src="../img/Icono_SoliAsociacionesReci.png" height="90px" width="90px" alt="recibidas"></center></button><br/><br/>
                <button type="submit" class="button" id="submit" name="opcion" value="ENVIADAS"><center><img src="../img/Icono_SolicitudesAsoEnviadas.png" height="90px" width="90px" alt="enviadas"></center></button>                
            </form><!--recuerda que con un mismo nombre formas un grupo de componentes, de tal forma que se pueda obtener el valor solo de aquel con el que interactuó el cliente, de la forma que se esperaba-->            
        </div>                    
            <iframe src="<%=pagina%>" id="frameAsociacion"></iframe>
            
    </body>
</html>