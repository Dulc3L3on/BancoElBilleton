<%-- 
    Document   : Retiro
    Created on : 21/11/2020, 07:44:17
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
        <title>Retirement</title>
        <%!Buscador buscador = new Buscador();
           Cliente cliente;
           Cuenta cuentas[];%>
    </head>
    <body>   
        <%if(request.getParameter("DPI_Buscado")!=null){
            cliente = (Cliente)buscador.buscarUsuario("Cliente", "DPI", request.getParameter("DPI_Buscado"));
            if(cliente!=null){
                cuentas = (Cuenta[]) buscador.buscarCuentasDeDueno(cliente.getCodigo());
            }
        }%>
        <br/>        
        <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
            <form method="POST" action="Retiro.jsp">                
                <table>
                    <tr>
                        <th> 
                            <h4>>Buscar Dueño</h4>
                        </th>                    
                        <th>
                            <input type="text" name="DPI_Buscado" placeholder="DPI del solicitante de retiro" id="buscado" maxlength="13" required>
                        </th>
                        <th>
                            <center>
                                <input type="submit" name="submit" id="busqueda" value="BUSCAR"> 
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
            
            <%if(cuentas!=null && request.getParameter("DPI_Buscado")!=null){%>
                <form method="POST" action="../../gestorRetiro">
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
                                   <input type="text" name="nombre" id="nombre" value="<%=cliente.getNombre()%>" readonly>
                                </th>                            
                            </tr>                                          
                            <tr>
                                <th>
                                    <h5 id="subtitulo">CUI</h5>
                                </th>                                
                                <th>
                                    <input type="number" name="DPI" id="DPI" min="0" value="<%=cliente.getDPI()%>" readonly>
                                </th>
                            </tr>
                            <tr>                            
                                <th colspan="2">
                                    <h3 id="subencabezado">>>DATOS RETIRO</h3>
                                </th>
                            </tr>
                            <tr>                            
                                <th>
                                    <h5 id="subtitulo">* Número de Cuenta</h5>
                                </th>
                                <th>
                                    <select name="numeroCuenta"  id="numeroCuenta"  onChange="maximoMonto()" required><!--si se tomara en cuenta el estado.. aquí solo deberían mostrarse habilitadas las activas y las canceladas inhabilitadas, con tal de que el cajero pueda ver todas las ctas asociadas al cliente... peuesto que no se eli solo se marcan como "canceladas" [lo cual lo hice con tal de que no existiera la posibilidad de que una cuenta nueva tuviera el número de una anterior y por lo tanto haber problemas mayores...]-->
                                        <option disbled selected>-Seleccione la cuenta-</option>
                                        <%for(int cuentaActual=0; cuentaActual<cuentas.length; cuentaActual++){%>
                                            <option value="<%=cuentas[cuentaActual].getNumeroCuenta()%>"><%=cuentas[cuentaActual].getNumeroCuenta()%></option>
                                        <%}%>
                                    </select>                                    
                                </th>
                            </tr>    
                            <tr>                                          
                                <th>
                                    <select id="montos" hidden>
                                        <%for(int cuenta =0; cuenta<cuentas.length; cuenta++){%>
                                            <option id="option" value="<%=cuentas[cuenta].getMonto()%>"></option>
                                        <%}%>
                                    </select>
                                    <h5 class="saldo" id="subtitulo">Saldo</h5>
                                </th>
                                <th>
                                    <input id="saldo" type="number" name="saldo" readonly>  
                                </th>
                            </tr> 
                            <tr id="nombresDatos">                            
                                <th>
                                    <h5 id="subtitulo">* Monto</h5>
                                </th>
                                <th>                                    
                                   <input type="number" name="monto" id="monto" min="1" required>
                                </th>                            
                            </tr>                        
                        </table>
                        <input type="submit" id="submit" name="retirar" value="RETIRAR">
                    </div>
                </form>    
            <%}%>
            <script>
                function maximoMonto(){
                    var cuentas = document.getElementById('numeroCuenta');
                    var opciones = document.getElementById('montos').options;
                    document.getElementById('monto').max = opciones[cuentas.selectedIndex-1].value;  
                    if(cuentas.selectedIndex>0){                        
                        document.getElementById('saldo').value = opciones[cuentas.selectedIndex-1].value;  
                    }else{
                        document.getElementById('saldo').value =0;//De aquí hacia abajo no funciona :v... aún xD
                    }                                                           
                    if(document.getElementById('saldo').value > 0){
                        document.getElementsByClassName('saldo').style.color="green";
                        document.getElementById('submit').disabled =false;
                    }else if(document.getElementById('saldo').value === 0){
                        document.getElementsByClassName('saldo').style.color="gray";
                        document.getElementById('submit').disabled = true;
                    }else{
                        document.getElementsByClassName('saldo').style.color="red";
                        document.getElementById('submit').disabled = true;
                    }
                }
            </script>                
        </center>                 
    </body>
</html><!--de esta parte solo te hace falta colocar el monto máximo que depende de la cuenta seleccionada... estaba pensando agarrar el ínidce seleccionado del select de #Ctas y con ello colocar el valor del select de montos en el máximo de "monto"...
            luego de esto solo faltaría hacer el gestor [Servlet] simi al de la vrs ant... y crear los métodos que le faltan y son eq a los de la vrs ant... recuerda solo imple los métodos que se requieran según el contexot actual-->
