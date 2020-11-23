<%-- 
    Document   : Perfil_Gerente
    Created on : 21/11/2020, 21:02:46
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssPerfiles.css">
        <title>ManagerProfile</title>
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
                                <img src="../../img/gerente_femenino.jpg" alt="gerente"height="370px" width="370px"> 
                            </th>                            
                        </tr>
                        <tr>
                            <th>
                                 <input type="text" id="datosUsuario"  name="datosActualizar" placeholder="Nombre" value=" "  required>
                            </th>
                            <th>
                                <input type="text" id="datosUsuario" name="datosActualizar" placeholder="Contraseña" value=" " required>                                 
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <input type="number" id="DPI"  name="DPI" placeholder="DPI" maxlength="13" value=" " style="width: 275px; height: 35px;" readonly>
                            </th>
                            <th>
                                <input type="text" id="datosUsuario"  name="datosActualizar" placeholder="Direccion" value=" " required>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                <input type="text" id="datosUsuario" name="genero" placeholder="Genero" value=" " readonly> 
                            </th>
                            <th>
                                <select name="datosActualizar" id="turno" style="width: 293px; height: 40px;">
                                    <option value="matutino" selected>Matutino</option><!--no creo que sea necesario poner un vacío en el valor... creo que con no declararlo basta...-->                                    
                                    <option value="vespertino">Vespertino</option>
                                </select><!--el turno que tiene asignado en ese momento lo mostrarás por medio del dato almacenado en la DB, de tal forma que si es == a matutino [p.ej] entonces que ese adquiera el valor selected si no pues nada es decir ""... simi a lo que hiciste para dejar marcada la opción del menú seleccionada de las páginas principales... [es decir con un ternario...]-->
                            </th>
                        </tr>                    
                        <tr>
                            <th colspan="2">
                                <input type="submit" id="acciones" name="accion"  value="MODIFICAR">          
                            </th>
                        </tr>
                    </table>                    
                </div>
            </div>
        </center>        
    </body>
</html>
