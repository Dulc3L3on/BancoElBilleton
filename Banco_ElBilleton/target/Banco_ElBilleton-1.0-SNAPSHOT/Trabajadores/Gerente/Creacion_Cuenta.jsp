<%-- 
    Document   : Creacion_Cuenta
    Created on : 22/11/2020, 12:36:44
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" href="../../css/cssGerente.css">
        <title>CreateAccount</title>
    </head>
    <body>
        <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
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
                                <h5 id="subtitulo">Código:</h5>
                            </th>
                            <th>
                               <input type="text" name="datosCuenta" id="nombre" readonly>
                            </th>
                            
                        </tr>
                        <tr>                            
                            <th>
                                <h5 id="subtitulo">Nombre:</h5>
                            </th>

                            <th>
                                <input type="number" name="datosCuenta" id="CUI"readonly>
                            </th>
                        </tr>                       
                        <tr>                            
                            <th colspan="2">
                                <h3 id="subencabezado">>>DATOS NUEVA CUENTA</h3>
                            </th>
                        </tr>    
                        <tr id="nombresDatos">                            
                            <th>
                                <h5 id="subtitulo">Número:</h5>
                            </th>
                            <th>
                               <input type="number" name="datosCuenta" id="numeroCuenta" min="0" readonly value="" >
                            </th>
                            
                        </tr>
                        <tr>                            
                            <th>
                                <h5 id="subtitulo">Tipo:</h5>
                            </th>                          
                            <th>
                                <select name="tipoDeCuenta"  id="tipoCuenta" style="width: 500px; height: 35px;" required>
                                    <option value="xD">Monetaria</option><!--no creo que sea necesario poner un vacío en el valor... creo que con no declararlo basta...-->
                                </select>
                            </th>
                        </tr>
                       <tr id="nombresDatos">                            
                            <th>
                                <h5 id="subtitulo">Monto Inicial:</h5>
                            </th>                                                                        
                            <th>
                                <input type="number" name="datosCuenta" id="monto" min="100" required>                           
                            </th>                            
                        </tr>
                    </table>
                     <input type="submit" id="submit" name="crearCuenta" value="CREAR CUENTA">
                </div>
            </form>            
        </center>                 
    </body>
</html>
