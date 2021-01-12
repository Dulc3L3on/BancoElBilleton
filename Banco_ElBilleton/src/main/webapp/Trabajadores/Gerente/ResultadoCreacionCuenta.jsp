<%-- 
    Document   : ResultadoCreacionCuenta
    Created on : 11/01/2021, 11:58:11
    Author     : phily
--%>

<%@page import="Modelo.Entidades.Objetos.Cuenta"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/cssGerente.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script> 
        <link rel="icon" href="img/Logos/Favicon_Banco_ElBilleton.ico">
        <title>CreationAccount</title>
        <%!Buscador buscador = new Buscador();
           Cuenta[] cuentas;%>
    </head>
    <body>
       <%if(request.getAttribute("errorReporte")!=null){%>
                <input type="text" id="tipoMsje" value="errorReporte" hidden>
                <script src="../../js/sweetError.js"></script>                     
       <%}else{%>
            <center>
                <h1>Resumen Creación de Cuentas</h1>
                <table>
                    <tr>
                        <th id="subencabezado">Fecha de Creación</th>
                        <th id="subencabezado">No. Cuenta</th>
                        <th id="subencabezado">Tipo</th>
                        <th id="subencabezado">Estado</th>
                    </tr>
                    
                    <%cuentas = buscador.buscarCuentasDeDueno(Integer.parseInt(request.getParameterValues("datosCuenta")[0]));
                    for(int cuentaActual=0; cuentaActual<cuentas.length; cuentaActual++){%>
                         <tr>
                            <th><%=cuentas[cuentaActual].getFechaCreacion()%></th>
                            <th><%=cuentas[cuentaActual].getNumeroCuenta()%></th>
                            <th><%=cuentas[cuentaActual].getTipo()%></th>
                            <th><%=cuentas[cuentaActual].getEstado()%></th>
                        </tr>
                    <%}%>
                </table>
                <form method="POST" action="gestorParametrosCliente">
                    <input type="text" name="codigoDueno" value="<%=request.getParameterValues("datosCuenta")[0]%>" hidden>
                    <button type="submit" id="submit" name="reporte" value="Personales_ResumenCreacionCuenta">DESCARGAR</button>
                </form>            
            </center>    
            
                <%if(request.getAttribute("mostrarMsje")!=null){%>
                    <input type="text" id="tipoMsje" value="errorCreacionCuenta" hidden>
                    <script src="js/sweetError.js"></script>
                <%}                                 
                if(request.getAttribute("mostrarMsjeEnvio")!=null){
                   if((boolean)request.getAttribute("mostrarMsjeEnvio")==true){%>
                        <input type="text" id="tipoMsje" value="exitoEnvioMail" hidden>
                        <script src="js/sweetInformativo.js"></script>

                    <%}else{%>
                        <input type="text" id="tipoMsje" value="errorEnvioMail" hidden>
                        <script src="js/sweetError.js"></script>
                    <%}%>
                <%}%>
       <%}%>          
    </body>
</html>
