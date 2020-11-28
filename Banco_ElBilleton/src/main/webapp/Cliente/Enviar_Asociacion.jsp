<%-- 
    Document   : Enviar_Asociacion
    Created on : 22/11/2020, 22:55:04
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/cssCliente.css">
        <title>SendAssociation</title>
    </head>
    <body>                   
        <div style="margin-top: 105px;">
            <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
                <form method="POST" action="Enviar_Asociacion.jsp">                
                    <table>
                        <tr>
                            <th> 
                                <h4>>Cuenta A Asociar</h4>
                            </th>                    
                            <th>
                                <center>
                                    <input type="number" name="cuentaBuscada" id="buscada" required>
                                </center>                                
                            </th>
                            <th>
                                <center>
                                    <input type="submit" name="submit" id="busqueda" value="BUSCAR">
                                </center>                                
                            </th>
                        </tr>
                        <tr>
                            <th colspan="3">
                                <h5>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>o<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<</h5>
                            </th>                        
                        </tr>
                    </table>                
                </form>                                   
                
                <form method="POST" action="../../gestorEnvioAsociacion">
                    <div id="contenedorGeneral">
                        <table>                        
                            <tr>
                                <th colspan="2">
                                    <h3 id="subencabezado">>>DATOS A ASOCIAR</h3>
                                </th>
                            </tr>
                            <tr id="nombresDatos">
                                <th>
                                    <h5 id="subtitulo">Nombre</h5>
                                </th>
                                <th>
                                   <input type="text" name="nombreDueno" id="nombre" value=" " readonly>
                                </th>                            
                            </tr>                                          
                            <tr>
                                <th>
                                    <h5 id="subtitulo">DPI</h5>
                                </th>                                
                                <th>
                                    <input type="number" name="DPI" id="DPI" value=" " readonly>
                                </th>
                            </tr>
                             <tr id="nombresDatos">                            
                                <th>
                                    <h5 id="subtitulo">* Número Cuenta</h5>
                                </th>
                                <th>
                                   <input type="number" name="numeroCuenta" id="numeroCuenta" min="0" value=" " readonly>
                                </th>                            
                            </tr>                            
                        </table>
                         <input type="submit" id="enviar" name="solicitar" value="ENVIAR SOLICITUD">
                    </div>
                </form>            
            </center>       
        </div>                  
    </body>
</html>
