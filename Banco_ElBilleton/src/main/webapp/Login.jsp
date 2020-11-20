<%-- 
    Document   : Login
    Created on : 19/11/2020, 21:42:47
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/cssUniversal.css">
        <title>Login</title>
        
    </head>
     <body class="cuerpo">
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
                               <input type="radio" name="tipoUsuario" value ="cliente" id="user" checked>
                                <label for="cliente">Cliente</label>                            
                            
                               <input type="radio" name="tipoUsuario" value ="cajero" id="user">
                               <label for="cajero">Cajero</label>

                               <input type="radio" name="tipoUsuario" value ="gerente" id="user">
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
            </div>                     
        </center>               
    </body>
</html>
