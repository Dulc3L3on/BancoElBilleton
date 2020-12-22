<%-- 
    Document   : Home
    Created on : 19/11/2020, 21:41:31
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
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
        <title>CashierHome</title>
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           ManejadorDeNavegacion navegador = new ManejadorDeNavegacion();//<!--al prinicpio [es decir después de loguearse], se llegará aquí por medio del doGet del servlet Perfil o LOgin xD [mejor login xD] luego de crear a la entidad correspondiente [por medio de un parmetro que indicará el tipo al métoodo correspondiente, que por el hecho de no tener que devolver nada puede crearse a los usuarios sin tener que quitarles su mero nombre xD p.ej-> CLIENTE a USUARIO y de instanciar sus valores con el setAttribute[auque por usar singleton, creo que no seá muy útil...creo xD]... de tal forma que al ya estar logueado,s e exe con normalidad este proceso...-->
           String pagina;%> 
    </head>
    <body>
        <!--te recuerdas de revisar aquí si la sesión aún sigue activa, es decir si el atributo código aún no ha sido invalidado...-->
        <%if(guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Cajero")==false){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        
        <input type="text" id="tipoMsje" value="bienvenida" hidden>
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
        
        <%if(request.getSession().getAttribute("msjeBienvenida")!=null){%>
             <script src="../../js/sweetInformativo.js"></script>                         
            <% request.getSession().removeAttribute("msjeBienvenida");//para que no se muestre cada vez... sino solo al nada más llegar xD
        }%>      
    </body>
</html>
