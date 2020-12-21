<%-- 
    Document   : Deposito
    Created on : 21/11/2020, 07:44:09
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page import="Modelo.Entidades.Objetos.Cuenta"%>
<%@page import="Modelo.Entidades.Usuarios.Cliente"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssCajero.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script> 
        <link rel="icon" href="../../img/Logos/Favicon_Banco_ElBilleton.ico"><!--se que no se mostrará puesto que no se mostrará por el hecho de ser una página interna, pero mejor se lo agrego xD-->                       
        <title>Deposit</title>
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           Buscador buscador = new Buscador();
           Cliente cliente = null;
           Cuenta cuenta= null;%>        
    </head>
    <body>  
        <%if(!guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Cajero") || !guardia.estaEnHorario("Cajero", (String) request.getSession().getAttribute("codigo"))){%>
            <input type="text" id="tipoMsje" value="fueraDeHorario" hidden>
            <script src="../../js/sweetInformativo.js"></script><!--recuerda que veremos cómo está la apariencia de la página cuando se redireccione ella misma hacia aquí para add o no el sweet con una dir menos profunda o no xD-->
            <%response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        <%if(request.getParameter("cuentaBuscada")!=null){
           cuenta = null;
           cliente = null;
           cuenta = buscador.buscarCuenta(request.getParameter("cuentaBuscada").trim());           
           if(cuenta!=null){
               cliente = (Cliente) buscador.buscarUsuario("Cliente", "codigo", String.valueOf(cuenta.getCodigoDuenoCuenta()));                         
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
                
            <%if(cliente!=null && request.getParameter("cuentaBuscada")!=null){%>
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
                                   <input type="number" name="monto" value="1" id="monto" min="1" required><!--Este no tiene un límite máx xd y si fuera así... sería una cantidad general... xD-->
                                </th>                            
                            </tr>                        
                        </table>
                         <input type="submit" id="submit" name="depositar" value="DEPOSITAR">
                    </div>
                </form>            
          <%}else if(request.getParameter("cuentaBuscada")!=null && cuenta==null){%><!--Creo que este tipo de msjes, deberían mostrarse como texto... [con este tipo me refiero a los que se muestran en la misma página en la que se están ingresando los datos... pero mira su apariencia, si te parece entonces déjalo con el sweet xD-->
                <!--se muestra el sweet de error, que indicará que: no exite una cuenta con el número ingresado [suponiendo que todo salió bien en la búsqueda de la cuenta xD]-->
                <!--eso si, se mostrará cuando el input search no sea nulo y la variable de hallado o algo por el estilo tenga el valor no esperado xD-->
                 <input type="text" id="tipoMsje" value="errorBusquedaCuenta" hidden>
                <script src="../../js/sweetError.js"></script>
          <%}else if(cuenta!=null && cliente==null){%>
                <!--eso quiere decir que el dueño de la cuenta ya no es cliente del banco... xD-->
                <!--o que ingresó en el campo de búsqueda un dato erróneo... xD-->
                <input type="text" id="tipoMsje" value="errorBusquedaDueno" hidden>
                <script src="../../js/sweetError.js"></script>
          <%}%>
        </center>                 
    </body>
</html>
