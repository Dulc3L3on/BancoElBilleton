<%-- 
    Document   : Deposito
    Created on : 21/11/2020, 07:44:09
    Author     : phily
--%>

<%@page import="Modelo.Entidades.Objetos.Cuenta"%>
<%@page import="Modelo.Entidades.Usuarios.Cliente"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssCajero.css">
        <title>Deposit</title>
        <%!Buscador buscador = new Buscador();
           Cliente cliente;
           Cuenta cuenta;%>        
    </head>
    <body>           
        <%if(request.getParameter("cuentaBuscada")!=null){
           cuenta = buscador.buscarCuenta(request.getParameter("cuentaBuscada").trim());           
           if(cuenta!=null){
               cliente = (Cliente) buscador.buscarUsuario("Cliente", "codigo", String.valueOf(cuenta.getCodigoDueno()));                         
           }           
        }%>
        <br/>
        <br/>
        <br/>
        <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
            <form method="POST" action="Deposito.jsp">                
                <table>
                    <tr>
                        <th> 
                            <h4>>Buscar Dueño</h4>
                        </th>                    
                        <th>
                            <input type="number" name="cuentaBuscada" placeholder="#Cuenta Receptora" id="buscada" required>
                        </th>
                        <th>
                            <center>
                                <input type="submit" name="submit" id="busqueda" value="ACEPTAR"> 
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
                
            <%if(cliente!=null){%>
                <form method="POST" action="../../gestorDeposito">
                    <div id="contenedorGeneral">
                        <input type="text" name="codigoDueno" value="<%=cliente.getCodigo()%>" hidden>
                        <table>                        
                            <tr>
                                <th colspan="2">
                                    <h3 id="subencabezado">>>DATOS DUEÑO DE CUENTA</h3>
                                </th>
                            </tr>
                            <tr id="nombresDatos">
                                <th>
                                    <h5 id="subtitulo">Nombre</h5>
                                </th>
                                <th>
                                   <input type="text" name="nombre" id="nombre" value="<%=cliente.getNombre()%>"readonly>
                                </th>                            
                            </tr>
                            <tr>                            
                                <th>
                                    <h5 id="subtitulo">Número de Cuenta</h5>
                                </th>
                                
                                <th>
                                    <input type="number" name="numeroCuenta" id="numeroCuenta" min="0" value="<%=request.getParameter("cuentaBuscada").trim()%>" readonly>
                                </th>
                            </tr>                       
                            <tr>                            
                                <th colspan="2">
                                    <h3 id="subencabezado">>>DATOS DEPÓSITO</h3>
                                </th>
                            </tr>    
                            <tr id="nombresDatos">                            
                                <th>
                                    <h5 id="subtitulo">Saldo Actual</h5>
                                </th>
                                <th>
                                   <input type="number" name="saldo" value="<%=cuenta.getMonto()%>" id="saldo" readonly>
                                </th>                            
                            </tr>    
                            <tr id="nombresDatos">                            
                                <th>
                                    <h5 id="subtitulo">* Monto</h5>
                                </th>
                                <th>
                                   <input type="number" name="monto" value="1" id="monto" min="1" required>
                                </th>                            
                            </tr>                        
                        </table>
                         <input type="submit" id="submit" name="depositar" value="DEPOSITAR">
                    </div>
                </form>            
            <%}else if(request.getParameter("cuentaBuscada")!=null && cuenta==null){%>
                <!--se muestra el sweet de error, que indicará que: no exite una cuenta con el número ingresado [suponiendo que todo salió bien en la búsqueda de la cuenta xD]-->
                <!--eso si, se mostrará cuando el input search no sea nulo y la variable de hallado o algo por el estilo tenga el valor no esperado xD-->
            <%}else if(cuenta!=null && cliente==null){%>
                <!--eso quiere decir que el dueño de la cuenta ya no es cliente del banco... xD-->
            <%}%>
        </center>                 
    </body>
</html>
