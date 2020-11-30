<%-- 
    Document   : Login
    Created on : 19/11/2020, 21:42:47
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/cssUniversal.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>  
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();%>
        <title>Login</title>
        
    </head>
     <body class="cuerpo">
        <%if(!guardia.estanTodasLlenas()){
            response.sendRedirect("CargaRegistrosAntiguos.jsp");
        }%>
         
        <center>
          
            <div id="divLogin">      
                <form method="POST" action="gestorLogin">             
                  <table cellspacing="25">
                      <tr>
                          <th>                                       
                            <img src="img/Logos/Logo_BancoElBilleton.png" id="img" height="250" width="250">                                                       
                          </th>
                      </tr>                      
                      <tr>
                          <th>
                              <input type="text" id="userData" name="username" placeholder="Username" required>
                          </th><!--si quieres recibir la info en un arreglo entonces nombra a estos comp IGUAL xd-->
                      </tr>
                      <tr>
                          <th>
                            <input type="password" id="userData" name="password" placeholder="Password" required>
                          </th>
                      </tr> 
                      <tr>
                           <th>
                               <input type="radio" name="tipoUsuario" value ="Cliente" id="user" checked>
                                <label for="cliente">Cliente</label>                            
                            
                               <input type="radio" name="tipoUsuario" value ="Cajero" id="user">
                               <label for="cajero">Cajero</label>

                               <input type="radio" name="tipoUsuario" value ="Gerente" id="user">
                               <label for="gerente">Gerente</label>
                            </th>
                      </tr>                          
                      <tr>
                          <th>
                              <br/>
                              <input type ="submit" id="submit" name="login" value="LOGIN">
                          </th>
                      </tr>                                                
                  </table>                                       
                </form>
                <%if(request.getSession().getAttribute("mostrarErrorLog")!=null){%><!--pero debes revisar que no provoque problemas por el hecho de no borrarlo, o de colocar la línea para borrarlo pero no se elimina...-->
                    <script src="js/sweet.js"></script>                    
                    <% request.getSession().removeAttribute("mostrarErrorLog");
                }%>                
            </div>                     
        </center>               
    </body>
</html>
