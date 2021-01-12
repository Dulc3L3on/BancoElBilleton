<%-- 
    Document   : EstadoDeCuenta
    Created on : 30/11/2020, 15:56:46
    Author     : phily
--%>

<%@page import="Modelo.Manejadores.DB.BuscadorPersonaEncargada"%>
<%@page import="Modelo.Manejadores.DB.BuscadorParaReportesCliente"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Entidades.Objetos.Transaccion"%>
<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script> 
        <link rel="stylesheet" href="css/cssCliente.css">
        <title>JSP Page</title>
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           List<Transaccion> listadoTransacciones;
           BuscadorParaReportesCliente buscadorParaReportes = new BuscadorParaReportesCliente();
           BuscadorPersonaEncargada buscadorPersonaEncargada = new BuscadorPersonaEncargada();%>
    </head>
    <body>
        <%if(!guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Cliente")){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        
        <%if(request.getAttribute("mostrarError")==null){%>
            <center>
                <h1>Estado de Cuenta</h1>
                <br/>
                <table style="width: 1000px;">
                <tr>
                    <th id="encabezado">Fecha</th>
                    <th id="encabezado">Hora</th>
                    <th id="encabezado">Código Transacción</th>
                    <th id="encabezado">Nombre Cajero a Cargo</th>
                    <th id="encabezado">Depósito (+)</th>
                    <th id="encabezado">Débito (-)</th>
                    <th id="encabezado">Saldo</th>
                </tr>
                <%listadoTransacciones = buscadorParaReportes.buscarTodasLasTransacciones(request.getParameter("origen"), java.time.LocalDate.now().toString(), java.time.LocalDate.now().toString());
                  if(buscadorParaReportes.darTipoSituacion()==1){
                    listadoTransacciones = buscadorPersonaEncargada.buscarNombreCajeroACargo(listadoTransacciones);                      
                  
                     for (int transaccionActual = 0; transaccionActual < listadoTransacciones.size(); transaccionActual++) {%>
                     <tr>
                        <th><%=listadoTransacciones.get(transaccionActual).getFecha()%></th>
                        <th><%=listadoTransacciones.get(transaccionActual).getHora()%></th>
                        <th><%=listadoTransacciones.get(transaccionActual).getCodigo()%></th>
                        <th><%=listadoTransacciones.get(transaccionActual).getNombreCajeroACargo()%></th>
                        <th><%=listadoTransacciones.get(transaccionActual).getDeposito()%></th>
                        <th><%=listadoTransacciones.get(transaccionActual).getDebito()%></th>
                        <th><%=listadoTransacciones.get(transaccionActual).getSaldo()%></th>                         
                     </tr>
                    <%}
                  }else{%>                    
                        <input type="text" value="errorBusquedaTransacciones" hidden>
                        <script src="js/sweetError.js"></script>
                <%}%>        
            </center>
            
            <%if(request.getAttribute("mostrarMsjeEnvio")!=null){
                if((boolean)request.getAttribute("mostrarMsjeEnvio")==true){%>
                    <input type="text" value="avisoTransferenciaExitoso" hidden><!--quizá se perdieron los atributos en el camino... aunque lo dudo xD-->
                    <script src="js/sweetInformativo.js"></script>
              <%}else{%>
                    <input type="text" value="errorAvisoTransferencia" hidden>
                    <script src="js/sweetError.js"></script>
              <%}
              request.removeAttribute("mostrarMsjeEnvio");
            }//recuerda qu elos atributos se pierde luego de usar un response...
        }else{%>            
            <input type="text" value="errorTransferencia" hidden>
            <script src="js/sweetError.js"></script>
        <%}%>
        </table>                
    </body>
</html>
