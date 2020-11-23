<%-- 
    Document   : Solicitudes_Recibidas
    Created on : 22/11/2020, 23:15:58
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
            <form method="POST" action="Solicitudes_Recibidas.jsp">                
                <table>
                    <tr>
                        <th colspan="3">
                            <h3 id="subencabezado">>>SOLICITUDES</h3>
                        </th>
                    </tr>
                    <%for(int solicitudActual=0; solicitudActual<10; solicitudActual++){%>
                        <tr>
                            <th style="width: 400px;"> 
                                <h5><%=solicitudActual%>. Nombre de la persona [DPI]</h5>
                            </th>                    
                            <th>
                                <center>
                                    <input type="submit" name="reaccion" id="reaccion aceptada" value="ACEPTAR"> 
                                </center>                                
                            </th>
                            <th>
                                <center>
                                    <input type="submit" name="reaccion" id="reaccion denegada" value="RECHAZAR"> 
                                </center>                                
                            </th>
                        </tr>
                    <%}%>
                </table>                
            </form>             
        </center>
    </body>
</html>
