<%-- 
    Document   : Resultado_Transaccion
    Created on : 29/11/2020, 18:17:19
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%!String codigoDueno;
           String numeroCuenta;
           String nombre;
           int saldoAntiguo;
           int saldoActual;
           String signo;%>
    </head>
    <body>
       <center>
         <%if(request.getAttribute("mostrarError")== null){%>
             <%codigoDueno = request.getParameter("codigoDueno");          
                nombre = request.getParameter("nombre");
                saldoAntiguo = Integer.parseInt(request.getAttribute("saldoAntiguo").toString());
                if(request.getAttribute("tipo").equals("credito")){
                    saldoActual = saldoAntiguo + Integer.parseInt(request.getParameter("monto"));
                    signo="+";
                }else{
                    saldoActual = saldoAntiguo - Integer.parseInt(request.getParameter("monto"));
                    signo="-";
                }%><!--no le coloco si es distinto de null, pues solo lo sería si surgió algo malo en cualquiera de los subprocesos para tramitar, si no, NO xd-->                     
         
                <table>                     
                    <tr>
                        <th colspan="4">
                            <h2>>>>>RESUMEN DE TRANSACCIÓN<<<<</h2>
                        </th>
                    </tr>
                    <tr>
                        <th colspan="4">
                             <h3 style="float: right;">Fecha: <%=java.time.LocalDate.now()%></h3>
                        </th>
                    </tr>
                    <tr>
                        <th colspan="2">
                           <h4>-------->>Datos del cliente<<--------</h4>
                        </th>
                        <th colspan="2">
                            <h4>------->>Datos del depósito<<------</h4>
                        </th>    
                    </tr>                    
                    <tr>
                        <th>
                            <h4><strong>Código:</strong></h4>
                        </th>
                        <th>
                            <h5><%=codigoDueno%></h5>
                        </th>
                         <th>
                            <h4><strong>Saldo Antiguo Q.</strong></h4>
                        </th>
                        <th>
                            <h5><%=saldoAntiguo%></h5>
                        </th>                                                
                    </tr>               
                    <tr>
                       <th>
                            <h4><strong>Nombre:</strong></h4>
                        </th>
                        <th>
                            <h5><%=nombre%></h5>
                        </th>
                         <th>
                            <h4><strong><%=signo%> monto:</strong></h4>
                        </th>
                        <th>
                            <h5>${transaccion.darMonto()}</h5>
                        </th>
                    </tr>
                    <tr>                        
                        <th>
                            <h4><strong>No. Cuenta</strong></h4>
                        </th>
                        <th>
                            <h5>${transaccion.darNumeroCuenta()}</h5>
                        </th>   
                         <th>
                            <h4><strong>Saldo Actual Q.</strong></h4>
                        </th>
                        <th>
                            <h5><%=saldoActual%></h5>
                        </th>
                    </tr>                                                               
                </table><!--esto debería ser reemplazado por el html del JR...-->                                    
                <input type="submit" name="sumbit" value="DESCARGAR ESTADO DE CUENTA">
            <%}else{%>             
                <!--se muestra le suweet de error de transaccion... digo esto porque seguramente para tener más orden, se creará un arch de sweet por entidad o agrupación mayor xD-->      
            <%}%>    
        </center><!--recuerda que le debes cambiar la apariencia para que se vea más profesional... yo pensaba hacerlo con 2 col... así como el formulario para la creación del cliente...-->
    </body>
</html>
