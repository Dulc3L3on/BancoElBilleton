<%-- 
    Document   : Deposito
    Created on : 21/11/2020, 07:44:09
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssCajero.css">
        <title>Deposit</title>
    </head>
    <body>   
        <br/>
        <br/>
        <br/>
        <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
            <form method="POST" action="Deposito.jsp">                
                <table>
                    <tr>
                        <th> 
                            <h4>>Buscar Dueño</h4>
                        </th>                    
                        <th>
                            <input type="text" name="cuentaBuscada" placeholder="#Cuenta Receptora" id="buscada" required>  
                        </th>
                        <th>
                            <center>
                                <input type="submit" name="submit" id="busqueda" value="BUSCAR"> 
                            </center>                                
                        </th>
                    </tr>
                    <tr>
                        <th colspan="3">
                            <h5>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>o<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<</h5>
                        </th>                        
                    </tr>
                </table>                
            </form>                                   
            
            <form method="POST" enctype="multipart/form-data" action="../../cargaDPI">
                <div id="contenedorGeneral">
                    <table>                        
                        <tr>
                            <th colspan="2">
                                <h3 id="subencabezado">>>DATOS DUEÑO DE CUENTA</h3>
                            </th>
                        </tr>
                        <tr id="nombresDatos">
                            <th>
                                <h5 id="subtitulo">Nombre</h5>
                            </th>
                            <th>
                               <input type="text" name="nombre" id="nombre" value=""readonly>
                            </th>                            
                        </tr>
                        <tr>                            
                            <th>
                                <h5 id="subtitulo">Número de Cuenta</h5>
                            </th>

                            <th>
                                <input type="number" name="numeroCuenta" id="numeroCuenta" min="0" value="" readonly>
                            </th>
                        </tr>                       
                        <tr>                            
                            <th colspan="2">
                                <h3 id="subencabezado">>>DATOS DEPÓSITO</h3>
                            </th>
                        </tr>    
                        <tr id="nombresDatos">                            
                            <th>
                                <h5 id="subtitulo">* Monto</h5>
                            </th>
                            <th>
                               <input type="number" name="monto" value="1" id="monto" min="1" required>
                            </th>                            
                        </tr>                        
                    </table>
                     <input type="submit" id="submit" name="depositar" value="DEPOSITAR">
                </div>
            </form>            
        </center>                 
    </body>
</html>
