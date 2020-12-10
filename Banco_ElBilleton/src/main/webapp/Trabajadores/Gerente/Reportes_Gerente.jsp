<%-- 
    Document   : Reportes_Gerente
    Created on : 2/12/2020, 15:10:45
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssReportes.css">
        <link rel="icon" href="../../img/Logos/Favicon_Banco_ElBilleton.ico"><!--se que no se mostrará puesto que no se mostrará por el hecho de ser una página interna, pero mejor se lo agrego xD-->        
        <title>ManagerReports</title>
    </head>
    <body>
        <div id="ContenedorReportes"><!--le colocaremos un layout para que se organicen de forma "automática"...-->        
            <center><!--asumo que por medio del nombre podré mandar a la clase que se encarga de traer los reportes [o clases, puesto que algunos requieren de tiepo s de obj que no tienen relación, si es así necesitaría 1 por cada grupo general... bueno, ya veremos xD] qué tipo de reporte es el que quiero..-->
                <button type ="submit" name="reporte" value="HistorialCambios"><img  src="../../img/iconos_Billeton/Historial.png"><br/>Historial <br/>de cambios</button><!--luego tendría que recuperar el tipo de usuario del sweet... solo que áun no se como xD-->                     
                <button type ="submit" name="reporte" value="MayoresTransacciones"><img  src="../../img/iconos_Billeton/Transaccion.png"><br/>Clientes <br/>con Transacciones<br/>Mayores</button>
                <button type ="submit" name="reporte" value="MayoresSumas"><img  src="../../img/iconos_Billeton/Transaccion.png"><br/>Clientes <br/>con Mayores<br/>Transacciones Sumadas</button>
                <button type ="submit" name="reporte" value="MasDinero"><img  src="../../img/iconos_Billeton/Dinero.png"><br/>Clientes <br/>con Más<br/>Dinero</button><br/><br/>
                <button type ="submit" name="reporte" value="CuentasAbandonadas"><img  src="../../img/iconos_Billeton/Dinero.png"><br/>Clientes <br/>con Cuentas<br/>Abandonadas</button>
                <button type ="submit" name="reporte" value="HistorialTransacciones"><img  src="../../img/iconos_Billeton/Historial.png"><br/>Historial de<br/>Transacciones</button>
                <button type ="submit" name="reporte" value="CajeroEficiente"><img  src="../../img/iconos_Billeton/Eficiencia.png"><br/>Cajero<br/>Más Eficiente</button>                
            </center>           
        </div>
    </body><!--No se si colocar en el nombre el tipo de usuario al que le pertenecen los reportes... eso dependerá de si use 1 sola clase para todos  o si cree una por cada agrupación general [si es lo 2do no dará problemas que tengan nombres iguales y si es lo primero tampoco da problema jajajja xD creo xD :v-->
</html>
