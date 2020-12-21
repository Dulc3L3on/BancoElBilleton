<%-- 
    Document   : Resultado_Transaccion
    Created on : 29/11/2020, 18:17:19
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/cssCajero.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>  
        <link rel="icon" href="img/Logos/Favicon_Banco_ElBilleton.ico"><!--se que no se mostrará puesto que no se mostrará por el hecho de ser una página interna, pero mejor se lo agrego xD-->                
        <title>JSP Page</title>
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           String codigoDueno;
           String numeroCuenta;
           String nombre;
           double saldoAntiguo;
           double saldoActual;
           String signo;%>
    </head>
    <body>
       <center>
         <%if(!guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Cajero") || !guardia.estaEnHorario("Cajero", (String) request.getSession().getAttribute("codigo"))){%>
            <input type="text" id="tipoMsje" value="fueraDeHorario" hidden>
            <script src="js/sweetInformativo.js"></script>
            <%response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
         <%if(request.getAttribute("mostrarError")== null){%>
             <%codigoDueno = request.getParameter("codigoDueno");          
                nombre = request.getParameter("nombre");
                saldoAntiguo = Double.parseDouble(request.getAttribute("saldoAntiguo").toString());
                if(request.getAttribute("tipo").equals("credito")){
                    saldoActual = saldoAntiguo + Double.parseDouble(request.getParameter("monto"));
                    signo="+";
                }else{
                    saldoActual = saldoAntiguo - Double.parseDouble(request.getParameter("monto"));
                    signo="-";
                }%><!--no le coloco si es distinto de null, pues solo lo sería si surgió algo malo en cualquiera de los subprocesos para tramitar, si no, NO xd-->                                     
                <table style="margin-top: 125px;">  
                    <tr>
                        <th colspan="4">
                            <center>
                                <h2>RESUMEN DE TRANSACCIÓN</h2>
                            </center>                            
                        </th>
                    </tr>
                    <tr>
                        <th colspan="4">
                             <h3 style="float: right;">Fecha: <%=java.time.LocalDate.now()%></h3>
                        </th>
                    </tr>
                    <tr>
                        <th colspan="2">
                           <h4 id="subencabezado">>>Datos del cliente</h4>
                        </th>
                        <th colspan="2">
                            <h4 id="subencabezado">>>Datos del depósito</h4>
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
                            <h5>${transaccion.getMonto()}</h5>
                        </th>
                    </tr>
                    <tr>                        
                        <th>
                            <h4><strong>No. Cuenta</strong></h4>
                        </th>
                        <th>
                            <h5>${transaccion.getNumeroCuentaAfectada()}</h5>
                        </th>   
                         <th>
                            <h4><strong>Saldo Actual Q.</strong></h4>
                        </th>
                        <th>
                            <h5><%=saldoActual%></h5>
                        </th>
                    </tr>                                                               
                </table><!--esto debería ser reemplazado por el html del JR...-->                                    
                <form method="POST" action="gestorParametrosCliente">
                    <input type="text" name="reporte" value="" hidden>                        
                    <input type="text" name="codigoDueno" value="<%=codigoDueno%>" hidden>                        
                    <input type="text" name="numeroCuenta" value="<%=request.getParameter("numeroCuenta")%>" hidden>                        
                    <input type="text" name="fechaFinal" value="<%=java.time.LocalDate.now()%>" hidden>                        
                    <input type="submit" name="reporte" value="PersonalesFechasCuentaDesdeCajero_EstadoDeCuenta" hidden>
                    <input type="submit" name="submit" id="submit" value="DESCARGAR ESTADO DE CUENTA" style="width: 300px; height: 65px;">
                </form><!--recuerda que toma el valor como si etsuviera vacío, pero no es así... puede que se haya trabajdo de tanto compilar xD o que sea un error en la dirección de la redirección xD, pero si fuera así, no podría llegar al gestor de Paráms del Cliente...-->
       <%}else{%>             
            <input type="text" id="tipoMsje" value="errorTransaccion" hidden>
            <script src="js/sweetError.js"></script> 
            <!--los atributos que no son de sesión, no los elimino, por el hecho de que se debe presionar un btn que redirecciona por meido de un response y no de un dispatcher por lo cual no se mantienen los valores de estor atributos xD-->
       <%}%>    
        </center><!--recuerda que le debes cambiar la apariencia para que se vea más profesional... yo pensaba hacerlo con 2 col... así como el formulario para la creación del cliente...-->
    </body>
</html>
