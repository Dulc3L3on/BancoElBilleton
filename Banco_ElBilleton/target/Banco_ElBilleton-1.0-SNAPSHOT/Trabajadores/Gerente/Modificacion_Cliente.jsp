<%-- 
    Document   : Modificacion_Cliente
    Created on : 22/11/2020, 18:03:21
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssGerente.css">
        <title>ClientModify</title>
    </head>
    <body>
        <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
            <form method="POST" enctype="multipart/form-data" action="../../cargaDPI">
                <div id="contenedorGeneral">
                    <table style="margin-top: -25px;">                        
                        <tr>
                            <th colspan="2">
                                <h3>>>DATOS INMUTABLES</h3>
                            </th>
                        </tr>
                        <tr id="nombresDatos">
                            <th>
                                <h5 id="subtitulo">Código</h5>
                            </th>
                            <th>
                                <input type="number" name="codigo" value ="" id="codigoCliente" readonly>                                
                            </th>                            
                        </tr>
                        <tr>                            
                            <th>
                                <h5 id="subtitulo">CUI</h5>
                            </th>
                            <th>
                                <input type="number" name="CUI" value ="" id="CUI" readonly>
                            </th>  
                        </tr>                       
                        <tr id="nombresDatos">
                            <th>
                                <h5 id="subtitulo">Género</h5>
                            </th>
                            <th>
                                <input type="text" name="genero" id="genero" value ="" readonly>                                
                            </th>                            
                        </tr>
                        <tr>                            
                            <th>
                                <h5 id="subtitulo">Fecha Nacimiento</h5>
                            </th>
                            <th>
                                <input type="date" name="birth" id="birth" value ="" style="width: 150px;" readonly>
                            </th>  
                        </tr>   
                        <tr>                            
                            <th colspan="2">
                                <h3>>>DATOS MODIFICABLES</h3>
                            </th>
                        </tr>    
                        <tr id="nombresDatos">                            
                            <th>
                                <h5 id="subtitulo">+ Nombre</h5>
                            </th>
                           <th>
                               <input type="text" name="datosActualizar" id="nombre" placeholde="Nombre" value ="" required>
                           </th>                            
                        </tr>
                        <tr>                            
                            <th>
                                <h5 id="subtitulo">+ PDF DPI</h5>                                
                            </th>                          
                            <th>
                                <input type="file" name="archDPI" accept=".pdf" id="dpi">
                            </th>
                        </tr>
                        <tr id="nombresDatos">                            
                            <th>
                                <h5 id="subtitulo">+ Dirección</h5>
                            </th>
                           <th>
                                <input type="text" name="datosActualizar" id="direccion" value ="" required>
                           </th>                            
                        </tr>
                        <tr>                            
                            <th>
                                <h5 id="subtitulo">+ Contraseña</h5>                                
                            </th>                          
                            <th>
                                <input type="text" name="datosActualizar" id="contrasenia" value ="" required>
                            </th>
                        </tr>
                        <tr>
                            <th colspan="2">
                                <h6><i>+ no es necesario modificarlo pero si que no esté vacío al momento de preionar "modificar"</i></h6>
                            </th>
                        </tr>
                    </table>
                     <input type="submit" id="submit" name="crearCuenta" value="CREAR CUENTA">
                </div>
            </form>            
        </center>                 
    </body>
</html>
