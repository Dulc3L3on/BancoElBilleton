<%-- 
    Document   : Reportes_Cajero
    Created on : 2/12/2020, 17:28:57
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page import="Modelo.Herramientas.Kit"%>
<%@page import="Modelo.Entidades.Usuarios.Cajero"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page import="Modelo.Manejadores.DB.BuscadorExistencia"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssReportes.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>  
        <link rel="icon" href="../../img/Logos/Favicon_Banco_ElBilleton.ico">
        
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           BuscadorExistencia buscadorExistencia = new BuscadorExistencia();
           Buscador buscador = new Buscador();
           Kit herramientas = new Kit();%>
        <title>CashierReports</title>
    </head>
    <body>                  
        <%if(guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Cajero")==false){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
       <%if(request.getSession().getAttribute("sinDatos")!=null){%>        
            <input type="text" name="tipoMsje" value="sinDatos" hidden>         
            <script src="../../js/sweetInformativo.js"></script><!--No se si al redireccionar con un dispatcher desde la carpeta menos profunda hasta la que se encuentra el arch JSP se estará a poca profundidad o a mucha... pero por el hecho de que no cambia la apariencia luego de haber presionado el btn odría ser que al redireccionar a partir de esa carpeta, se quede en una profundidad grande ó que por el hecho de ser una "cargaDatos" se quede en la profunidad en la que estaba la pág al redireccionar al gestor que se enciarga de cargar los datos...-->
            <script src="js/sweetInformativo.js"></script>
            <%request.getSession().removeAttribute("sinDatos");
        }%><!--queda mejor así xD, solo hay que solucionar el problema de los sweet de no mostrarse cuando no es la primer opción...-->
        
        <div id="ContenedorReportes"><!--le colocaremos un layout para que se organicen de forma "automática"...-->        
            <form method="POST" action="Reportes_Cajero.jsp">
                <center><!--asumo que por medio del nombre podré mandar a la clase que se encarga de traer los reportes [o clases, puesto que algunos requieren de tiepo s de obj que no tienen relación, si es así necesitaría 1 por cada grupo general... bueno, ya veremos xD] qué tipo de reporte es el que quiero..-->
                    <button type ="submit" name="reporte" value="Personales_TransaccionesDelDia" <%=(buscadorExistencia.aAtendidoTransacciones((String) request.getSession().getAttribute("codigo"), herramientas.darFechaActualString()))?"":"disabled"%> ><img  src="../../img/iconos_Billeton/Transaccion.png"><br/>Transacciones<br/>del Día</button><!--luego tendría que recuperar el tipo de usuario del sweet... solo que áun no se como xD-->                     
                    <button type ="submit" name="reporte" value="PersonalesFechas_IntervaloTransacciones" <%=(buscadorExistencia.aAtendidoTransacciones((String) request.getSession().getAttribute("codigo"), null))?"":"disabled"%> ><img  src="../../img/iconos_Billeton/Historial.png"><br/>Transacciones<br/>Antiguas</button>               
                </center>    
                
                <input type="date" name="fechaInicial" value="<%=java.time.LocalDate.now()%>" hidden>      
                <input type="date" name="fechaFinal" value="<%=java.time.LocalDate.now()%>" hidden><!--esto no dará problema, puesto que si el cajero llegara a presionar el otro reporte, al tener el mismo nombre, sería reemplazado su valor... es que tengo que colocar este input porque sino tendría que hacer un método obteniendo estos valores de un parámetro y otro para obtenerlo de un atributo... sino lo que podrías hacer es buscar como enviar los input colocándolos en el else, como parte de los parámetros del formulario...-->
            </form>            
        </div>
        
        <%if(request.getParameter("reporte")!=null){%>
            <%if(request.getParameter("reporte").contains("Intervalo")){%>
                <center>
                <div id="back" onclick="cancelarEnvio()">
                    <div id="form">
                        <form id="formulario" method="POST" action="../../gestorParametrosCajero">
                            <input type="text" name="reporte" value="<%=request.getParameter("reporte")%>" hidden>
                            <h4>* Fecha Inicial</h4>
                            <input type="date" name="fechaInicial" max="<%=java.time.LocalDate.now()%>" required>      
                            <h4>* Fecha Final</h4>
                            <input type="date" name="fechaFinal" max="<%=java.time.LocalDate.now()%>" required><br/><br/>                                
                            <input id="boton" type="submit" value="ACEPTAR">                        
                        </form>
                    </div>                
                </div>                
                </center>                
            <%}else{%>                
                <%request.getRequestDispatcher("../../gestorParametrosCajero").forward(request, response);
              }%>
        <%}%>    
    
     <script>
            function cancelarEnvio(){               
                var elementoClickeado = document.onclick;
                             
                if(event.srcElement.id === "back"){                    
                   window.location.href = "Reportes_Cajero.jsp";                                          
                }                                                     
            }//FUNCIONA super NICE XD GRACIAS DIOSS!!! XD
        </script>    
    </body>
</html>
