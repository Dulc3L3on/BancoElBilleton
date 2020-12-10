<%-- 
    Document   : Home
    Created on : 19/11/2020, 21:41:39
    Author     : phily
--%>

<%@page import="Modelo.Manejadores.ManejadorDeNavegacion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssPaginasPrincipales.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>  
        <link rel="icon" href="../../img/Logos/Favicon_Banco_ElBilleton.ico">
        
        <title>ManagerHome</title>
        <%!ManejadorDeNavegacion navegador = new ManejadorDeNavegacion();//<!--al prinicpio [es decir después de loguearse], se llegará aquí por medio del doGet del servlet Perfil o LOgin xD [mejor login xD] luego de crear a la entidad correspondiente [por medio de un parmetro que indicará el tipo al métoodo correspondiente, que por el hecho de no tener que devolver nada puede crearse a los usuarios sin tener que quitarles su mero nombre xD p.ej-> CLIENTE a USUARIO y de instanciar sus valores con el setAttribute[auque por usar singleton, creo que no seá muy útil...creo xD]... de tal forma que al ya estar logueado,s e exe con normalidad este proceso...-->
        String pagina;%> 
    </head>
    <body> 
        <!--te recuerdas de revisar aquí si la sesión aún sigue activa, es decir si el atributo código aún no ha sido invalidado...-->
        
        
        <input type="text" id="tipoMsje" value="bienvenida" hidden>
        <%pagina = navegador.darPaginasAlGerente(request.getParameter("opcion"));%>
        <div id="menuGerente">                           
            <form method="POST" action="Home_Gerente.jsp">
                <center>        
                    <br/>
                    <a href="../../gestorLogout"><img src="../../img/off.png" width="50" height="50" id="off"></a>
                    <a href="Home_Gerente.jsp"><img src="../../img/user.png" width="50" height="50" id="perfil"></a>
                    <input type="submit" class="button gerente" id="opciones" name="opcion" value="REPORTES" >
                    <input type="submit" class="button gerente" id="opciones" name="opcion" value="MODIFICACION" >
                    <input type="submit" class="button gerente" id="opciones" name="opcion" value="CREACION" >                                                                              
                </center>                   
            </form>                                            
        </div>                                           
        <iframe src="<%=pagina%>" id="frameGerente"></iframe>            
        
        <%if(request.getSession().getAttribute("msjeBienvenida")!=null){%>
             <script src="../../js/sweetInformativo.js"></script>                         
            <% request.getSession().removeAttribute("msjeBienvenida");//para que no se muestre cada vez... sino solo al nada más llegar xD
        }%>
    </body>
</html>
