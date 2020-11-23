<%-- 
    Document   : Perfil_Cliente
    Created on : 21/11/2020, 21:01:23
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/cssPerfiles.css">
        <title>ClientProfile</title>
    </head>
    <body>
         <center>
            <div>                
                <div id="divDatosGerente">
                    <label><h4>CÓDIGO: </h4></label>
                    <table id="datosGerente">                                                                        
                        <tr>
                            <th colspan="2">
                                <!--RECUERDA que cuando ya estes con las variables que extraen la info de la base de datos, cambiarás la img para que corresponda con el género, por medio del valor que está almacenado en la variable género...-->
                                <img src="../img/cliente_masculino.png" alt="cliente"height="370px" width="370px"> 
                            </th>                            
                        </tr>
                        <tr>
                            <th>
                                 <input type="text" id="datosUsuario"  name="nombre" placeholder="Nombre" value=" "  readonly>
                            </th>
                            <th>
                                <input type="text" id="datosUsuario" name="contrasenia" placeholder="Contraseña" value=" " readonly>                                 
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <input type="number" id="DPI"  name="DPI" placeholder="DPI" maxlength="13" value=" " style="width: 275px; height: 35px;" readonly>
                            </th>
                            <th>
                                <input type="text" id="datosUsuario"  name="direccion" placeholder="Direccion" value=" " readonly>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario" name="genero" placeholder="Genero" value=" " readonly> 
                            </th>
                            <th>
                                <input type="date" id="datosUsuario" name="birth" placeholder="Birth" value=" " readonly> 
                            </th>
                        </tr>                                          
                    </table>                    
                </div>
            </div>
        </center>
    </body>
</html>
