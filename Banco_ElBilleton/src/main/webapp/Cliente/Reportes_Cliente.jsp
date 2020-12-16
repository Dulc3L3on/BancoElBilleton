<%-- 
    Document   : Reportes_Cliente
    Created on : 2/12/2020, 17:31:48
    Author     : phily
--%>

<%@page import="Modelo.Manejadores.DB.BuscadorExistencia"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page import="Modelo.Entidades.Objetos.Cuenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/cssReportes.css">
        <link rel="stylesheet" href="css/cssReportes.css"><!--creo que no es necesario pues al hacer el "regresón" xD el favicón se seguía mostrando, sin importar que el regreso fuese por JS o por el response del servlet por falta de datos...-->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>  
        <link rel="icon" href="../img/Logos/Favicon_Banco_ElBilleton.ico">
        <%!Cuenta[] cuentas = null;
           Buscador buscador = new Buscador();
           BuscadorExistencia buscadorExistencia = new BuscadorExistencia();%>
        <title>ClientReports</title>
    </head>
    <body>
        
            <div id="ContenedorReportes"><!--le colocaremos un layout para que se organicen de forma "automática"...-->        
                <form action="Reportes_Cliente.jsp" method="POST">
                    <center><!--asumo que por medio del nombre podré mandar a la clase que se encarga de traer los reportes [o clases, puesto que algunos requieren de tiepo s de obj que no tienen relación, si es así necesitaría 1 por cada grupo general... bueno, ya veremos xD] qué tipo de reporte es el que quiero..-->
                        <button type ="submit" name="reporte" value="Personales_GrandesTransaccionesdelAnio" <%=(buscadorExistencia.poseeCuentas((String) request.getSession().getAttribute("codigo")))?"":"disabled"%> ><img  src="../img/iconos_Billeton/Transaccion.png"><br/>Últimas 15<br/>Transacciones Mayores</button><!--Es Historial y Transaccion, pero ya hay muchos íconos de historial xD-->                     
                        <button type ="submit" name="reporte" value="PersonalesFechasCuenta_EstadoDeCuenta" <%=(buscadorExistencia.poseeCuentas((String) request.getSession().getAttribute("codigo")))?"":"disabled"%>><img  src="../img/iconos_Billeton/Historial.png"><br/>Estado de<br/>Cuenta<br/></button>               
                        <button type ="submit" name="reporte" value="PersonalesCuenta_CuentaMasDinero" <%=(buscadorExistencia.poseeCuentas((String) request.getSession().getAttribute("codigo")))?"":"disabled"%>><img  src="../img/iconos_Billeton/Dinero.png"><br/>Cuenta con<br/>Más Dinero y<br/>sus Transacciones</button></br>
                        <button type ="submit" name="reporte" value="Personales_HistorialSolicitudesRecibidas" <%=(buscadorExistencia.existenSolicitudes("recibidas","codigoSolicitado",(String) request.getSession().getAttribute("codigo")))?"":"disabled"%>><img  src="../img/iconos_Billeton/Historial.png"><br/>Historial<br/>Solicitudes Recibidas</button>
                        <button type ="submit" name="reporte" value="Personales_HistorialSolicitudesEnviadas" <%=(buscadorExistencia.existenSolicitudes("enviadas","codigoSolicitante",(String) request.getSession().getAttribute("codigo")))?"":"disabled"%>><img  src="../img/iconos_Billeton/Historial.png"><br/>Historial<br/>Solicitudes Enviadas</button>
                    </center>           
                </form>
            </div>                
        
            <%if(request.getParameter("reporte")!=null){%>                  
                <div id="back" onclick="cancelarEnvio()">
                    <%if(request.getParameter("reporte").contains("EstadoDeCuenta")){%>
                        <%cuentas = buscador.buscarCuentasDeDueno(Integer.parseInt((String) request.getSession().getAttribute("codigo")));%>
                        <center>   
                        <div id="form">
                            <form id="formulario" action="../gestorParametrosCliente" method="POST">
                                <input type="text" name="reporte" value="<%=request.getParameter("reporte")%>" hidden>
                                
                                    <h4>* Número de Cuenta</h4>
                                    <select name="numeroCuenta" required>
                                        <option value="-Seleccione-" disabled selected>-Seleccione Número Cuenta-</option>                                                                                                                                         
                                        <%for(int cuentaActual=0; cuentaActual < cuentas.length; cuentaActual++){%>
                                           <option value="<%=cuentas[cuentaActual].getNumeroCuenta()%>" ><%=cuentas[cuentaActual].getNumeroCuenta()%></option>                                                                                              
                                        <%}%>
                                    </select>
                                    <h4>* Fecha Inicial</h4>
                                    <input type="date" name="fechaInicial" max="<%=java.time.LocalDate.now()%>" required>      
                                    <h4>* Fecha Final</h4>
                                    <input type="date" name="fechaFinal" max="<%=java.time.LocalDate.now()%>" required><br/><br/>                                
                                    <input id="boton" type="submit" value="ACEPTAR">                                     
                            </form>                                                      
                        </div>
                        </center><!--creo que ya no es necesario por el hecho de tener el justify-content en center... bueno aunque este es para alinear los items de los item del back xD que vendrían a ser los form directamente, no sé si se propaga el atributo...-->
                    <%}else if(request.getParameter("reporte").contains("CuentaMasDinero")){%>            
                        <center>                
                            <div id="form">
                                <form id="formulario" method="POST" action="../gestorParametrosCliente"><!--debido que al regresar por sí misma a ella xD [la página JSP] regresa con una dir directa xD-->
                                    <input type="text" name="reporte" value="<%=request.getParameter("reporte")%>" hidden>
                                    
                                    <h4>* Fecha Inicial</h4>
                                    <input type="date" name="fechaInicial" max="<%=java.time.LocalDate.now()%>"required>      
                                    <h4>Fecha Final</h4>
                                    <input type="date" name="fechaFinal" value="<%=java.time.LocalDate.now()%>" readonly><br/><br/>    
                                    <input id="boton" type="submit" value="ACEPTAR">                                                    
                                </form>                    
                            </div>                                                                    
                        </center>                    
                </div>                    
                  <%}else{
                        request.getRequestDispatcher("../gestorParametrosCliente").forward(request, response);
                    }            
            }%>        
            
            <script>
                function cancelarEnvio(){               
                   var elementoClickeado = document.onclick;
                             
                   if(event.srcElement.id === "back"){                    
                        window.location.href = "Reportes_Cliente.jsp";                                          
                   }                                                     
               }//debes revisar esta función... según vi debería funcionar xD, pero revisa lo del hidden [es decri si así se asigna el valor y tb si es necesario colocarlo...]    DESPUÉS recuerda deshabilitar los botnes si es que no hay datos que mostrar,para evitar confusiones para el usaruio...
            </script>
        <%if(request.getSession().getAttribute("sinDatos")==null){%>        
            <input type="text" name="tipoMsje" value="sinDatos" hidden>
            <!--<h2>SIN DATOS</h2>
            <h4>No hay Registros de Transacciones realizadas
                Para el intervalo de tiempo especificado</h4>-->
            <script src="../js/sweetInformativo.js"></script>
            <%request.getSession().removeAttribute("sinDatos");
         }%><!--queda mejor así xD, solo hay que solucionar el problema de los sweet de no mostrarse cuando no es la primer opción...-->
    </body>
</html>


<!--REPORTES COMPLETADOS
        1. ultimas15Grandes xD
        2. estado de cta (= todas las transacciones de una cuenta en cuestión xD)
        3. cuenta con más dinero
        4. historial solicitudes recibidas
        5. historial solicitudes enviadas xD... es decir todos xD
-->