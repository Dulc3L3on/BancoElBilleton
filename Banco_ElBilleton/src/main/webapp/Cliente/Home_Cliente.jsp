<%-- 
    Document   : Home
    Created on : 19/11/2020, 21:41:14
    Author     : phily
--%>

<%@page import="Manejadores.ManejadorDeNavegacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ClientHome</title>        
        <link rel="stylesheet" href="../css/cssPaginasPrincipales.css">
        <link rel="icon" href="../img/Logos/FaviBilleton.ico">
        <%!ManejadorDeNavegacion navegador = new ManejadorDeNavegacion();//<!--al prinicpio [es decir después de loguearse], se llegará aquí por medio del doGet del servlet Perfil o LOgin xD [mejor login xD] luego de crear a la entidad correspondiente [por medio de un parmetro que indicará el tipo al métoodo correspondiente, que por el hecho de no tener que devolver nada puede crearse a los usuarios sin tener que quitarles su mero nombre xD p.ej-> CLIENTE a USUARIO y de instanciar sus valores con el setAttribute[auque por usar singleton, creo que no seá muy útil...creo xD]... de tal forma que al ya estar logueado,s e exe con normalidad este proceso...-->
        String pagina;%> 
    </head>
   <body>
        <%pagina = navegador.darPaginasAlCliente(request.getParameter("opcion"));%>
         <div id="menuCliente">                  
           
            <form method="POST" action="Home_Cliente.jsp">                    
                <br/>                    
                <a href="../gestorLogout"><img src="../img/off.png" width="50" height="50" id="off"></a>
                <a href="Home_Cliente.jsp"><img src="../img/user.png" width="50" height="50" id="perfil"></a>
                <input type="submit" class="button cliente" id="opciones" name="opcion" value="REPORTES" >
                <input type="submit" class="button cliente" id="opciones" name="opcion" value="ASOCIACION" >
                <input type="submit" class="button cliente" id="opciones" name="opcion" value="TRANSFERENCIA" >                                                                               
            </form>                                            
        </div>                        
        <hr>              
        <iframe src="<%=pagina%>" id="frameCliente"></iframe>
    </body>
</html>
