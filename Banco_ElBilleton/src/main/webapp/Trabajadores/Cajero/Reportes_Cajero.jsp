<%-- 
    Document   : Reportes_Cajero
    Created on : 2/12/2020, 17:28:57
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssReportes.css">
        <link rel="icon" href="../../img/Logos/Favicon_Banco_ElBilleton.ico">
        <title>CashierReports</title>
    </head>
    <body>      
        <div id="ContenedorReportes"><!--le colocaremos un layout para que se organicen de forma "automática"...-->        
            <center><!--asumo que por medio del nombre podré mandar a la clase que se encarga de traer los reportes [o clases, puesto que algunos requieren de tiepo s de obj que no tienen relación, si es así necesitaría 1 por cada grupo general... bueno, ya veremos xD] qué tipo de reporte es el que quiero..-->
                <button type ="submit" name="reporte" value="HistorialCambios"><img  src="../../img/iconos_Billeton/Transaccion.png"><br/>Transacciones<br/>del Día</button><!--luego tendría que recuperar el tipo de usuario del sweet... solo que áun no se como xD-->                     
                <button type ="submit" name="reporte" value="MayoresTransacciones"><img  src="../../img/iconos_Billeton/Historial.png"><br/>Transacciones<br/>Antiguas</button>               
            </center>           
        </div>
    </body>
</html>
