<%-- 
    Document   : Transferencia
    Created on : 22/11/2020, 22:29:10
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/cssCliente.css">
        <title>JSP Page</title>
    </head>
    <body>                   
        <br/>
        <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
            <form method="POST" action="Deposito.jsp">                
                <table>
                    <tr>
                        <th> 
                            <h4>>Cuenta Destino</h4>
                        </th>                    
                        <th>
                            <center>
                                <input type="submit" name="tipoCuentaDestino" id="busqueda" value="Propia"> 
                            </center>                                
                        </th>
                        <th>
                            <center>
                                <input type="submit" name="tipoCuentaDestino" id="busqueda" value="Terceros"> 
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
                                <h3 id="subencabezado">>>DATOS TRANSFERENCIA</h3>
                            </th>
                        </tr>
                        <tr id="nombresDatos">
                            <th>
                                <h5 id="subtitulo">*Cuenta Origen</h5>
                            </th>
                            <th>
                               <select name="origen" id="opTransferencia"> 
                                    <option value="0" selected>0</option>
                                   
                                    <%for(int cuentaActual=0; cuentaActual < 5; cuentaActual++){%>
                                        <option value="<%=cuentaActual%>" ><%=cuentaActual%></option><!--no creo que sea necesario poner un vacío en el valor... creo que con no declararlo basta...-->                                                                                                          
                                    <%}%><!--Se seleccionará la primer cta por defecto [y para el combo de la cta emisora se desactivará el 1ro por default, para después cb conforme al cb de selección realizado...] para cumplir con la función JS, que se activa al haber cb en este combo...-->
                                </select>
                            </th>                            
                        </tr>                                          
                        <tr>
                            <th>
                                <h5 id="subtitulo">* Cuenta Destino</h5>
                            </th>                                
                            <th>
                                 <select name="destino" id="opTransferencia">                                       
                                    <option value="ini" disabled>ini</option>                                                                                              
                                    
                                    <%for(int cuentaActual=0; cuentaActual < 10; cuentaActual++){%>
                                         <option value="<%=cuentaActual%>" ><%=cuentaActual%></option>                                                                                              
                                    <%}%>
                                </select>
                            </th>
                        </tr>
                         <tr id="nombresDatos">                            
                            <th>
                                <h5 id="subtitulo">* Monto</h5>
                            </th>
                            <th>
                               <input type="number" name="monto" value="1" id="opTransferencia" min="1" required>
                            </th>                            
                        </tr>                            
                    </table>
                     <input type="submit" id="enviar" name="transferir" value="TRANSFERIR">
                </div>
            </form>            
        </center>                 
    </body>
</html>
