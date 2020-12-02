<%-- 
    Document   : Transferencia
    Created on : 22/11/2020, 22:29:10
    Author     : phily
--%>

<%@page import="Modelo.Entidades.Objetos.Cuenta"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/cssCliente.css">
        <title>Transference</title>
        <%!Buscador buscador = new Buscador();           
           Cuenta cuentasOrigen[] = null;
           Cuenta cuentasAsociadas[];           
           Cuenta cuentasDestino[] = null;%>        
    </head>
    <body>            
        <%cuentasOrigen = buscador.buscarCuentasDeDueno(Integer.parseInt((String) request.getSession().getAttribute("codigo")));
          cuentasAsociadas = buscador.buscarCuentasAsociadas((String)request.getSession().getAttribute("codigo"));
          
            if(request.getParameter("tipoCuentaDestino")!=null){            
                if(request.getParameter("tipoCuentaDestino").equals("Propia")){
                    cuentasDestino = cuentasOrigen;                
                }else{
                    cuentasDestino = cuentasAsociadas;
                }
            }%>
        <br/>              
        
        <%if(buscador.darTipoSituacion()>-1){%>
            <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->            
                <form method="POST" action="Transferencia.jsp">                
                    <table>
                        <tr>
                            <th> 
                                <h4>>Cuenta Destino</h4>
                            </th>                    
                            <th>
                                <center>
                                    <input type="submit" name="tipoCuentaDestino" id="busqueda" value="Propia"> 
                                </center>                                
                            </th>
                            <th>
                                <center>
                                    <input type="submit" name="tipoCuentaDestino" id="busqueda" <%=(buscador.darTipoSituacion()==0)?"disabled":""%> value="Terceros"> 
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
                                
                <%if(cuentasOrigen!=null && cuentasDestino!=null){%>            
                   <form method="POST" action="../gestorTransferencia">
                       <input type="text" name="tipoTransferencia" value="<%=(request.getParameter("tipoCuentaDestino").equals("Propia")?"propia":"terceros")%>" hidden>
                       
                        <div id="contenedorGeneral">
                            <table>                        
                                <tr>
                                    <th colspan="2">
                                        <h3 id="subencabezado">>>DATOS TRANSFERENCIA</h3>
                                    </th>
                                </tr>
                                <tr id="nombresDatos">
                                    <th>
                                        <h5 id="subtitulo">* Cuenta Origen</h5>
                                    </th>
                                    <th>
                                        <select name="origen" id="opTransferencia" class="origen"  onchange="convergencia()" onclick="" required> 
                                            <option value="-Seleccione Número Cuenta-" disabled selected>-Seleccione Número Cuenta-</option>                                                                                                                                                                                 
                                            <%for(int cuentaActual=0; cuentaActual < cuentasOrigen.length; cuentaActual++){%>
                                                <option value="<%=cuentasOrigen[cuentaActual].getNumeroCuenta()%>" ><%=cuentasOrigen[cuentaActual].getNumeroCuenta()%></option><!--no creo que sea necesario poner un vacío en el valor... creo que con no declararlo basta...-->                                                                                                          
                                            <%}%><!--Se seleccionará la primer cta por defecto [y para el combo de la cta emisora se desactivará el 1ro por default, para después cb conforme al cb de selección realizado...] para cumplir con la función JS, que se activa al haber cb en este combo...-->
                                        </select>
                                    </th>                            
                                </tr>                                          
                                <tr>
                                    <th>
                                        <h5 id="subtitulo">* Cuenta Destino</h5>
                                    </th>                                
                                    <th>
                                        <select name="destino" id="opTransferencia" class="destino" required> 
                                            <option value="-Seleccione Número Cuenta-" disabled selected>-Seleccione Número Cuenta-</option>                                                                                                                                         
                                            <%for(int cuentaActual=0; cuentaActual < cuentasDestino.length; cuentaActual++){%>
                                                <option value="<%=cuentasDestino[cuentaActual].getNumeroCuenta()%>" ><%=cuentasDestino[cuentaActual].getNumeroCuenta()%></option>                                                                                              
                                            <%}%>
                                        </select>
                                    </th>
                                </tr>
                                <tr id="nombresDatos">                            
                                    <th>
                                        <select id="montos" hidden>
                                            <%for(int cuenta =0; cuenta<cuentasOrigen.length; cuenta++){%>
                                                <option id="option" value="<%=cuentasOrigen[cuenta].getMonto()%>"></option>
                                            <%}%>
                                        </select>
                                         <h5 id="subtitulo">Saldo</h5>
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
                                        <input type="number" name="monto" id="opTransferencia" class="monto" min="1" required>
                                    </th>                            
                                </tr>                            
                            </table>
                             <input type="submit" id="enviar" name="transferir" value="TRANSFERIR">
                        </div>
                    </form>   
                  
                   <script>
                       function convergencia(){
                            eliminacionRedundancia();
                            maximoMontoRetiro();
                       }
                   </script>                      
                  
                  <%if(request.getParameter("tipoCuentaDestino").equals("Propia")){%>
                        <script>
                            function eliminacionRedundancia(){
                                var origen = document.getElementsByClassName('origen');
                                var opcionesDestino = document.getElementsByClassName('destino').options;                                                        
                            
                                for (var opcionDestionActual = 1; opcionDestionActual < opcionesDestino.length; opcionDestionActual++) {
                                    opcionesDestino[origen.selectedIndex].setAttribute("disabled", "false");                            
                                }//de esta manera se habilitan todas las opciones menos el msje indicador xD
                                opcionesDestino[origen.selectedIndex].setAttribute("disabled", "true");//Así se deshabilita la opción que corresponde a la cta origen xD para evitar redundancIa xD
                            }    
                        </script>
                    <%}%>
                    <script>                                              
                        function maximoMontoRetiro(){
                            var cuentasOrigen = document.getElementsByClassName('origen');
                            var opcionesMontos = document.getElementById('montos').options;
                            document.getElementsByClassName('monto').max = opcionesMontos[cuentasOrigen.selectedIndex-1].value;  
                            
                            if(cuentasOrigen.selectedIndex>0){                        
                                document.getElementById('saldo').value = opcionesMontos[cuentasOrigen.selectedIndex-1].value;  
                                }else{
                                    document.getElementById('saldo').value =0;//De aquí hacia abajo no funciona :v... aún xD
                                }                                               
                            }
                    </script>                    
                <%}else{%>
                    <!--se llama al mismo sweet de abajito xD-->            
                <%}%>        
            </center> 
        <%}else{%>
          <!--se muestra el sweet con el msje diciendo que surgió un error al buscar las cuentas... xD o algo así que no delate del todo xD-->
        <%}%>
    </body>
</html>

<!--Hay 4 formas con las que puedes hacer que se exe el método de eliRed solo cuando las de destino sean las propias y sin importar que tipo sean las ctas de destino exe siempre el de máx monto...-->
<!--1. seguir usando este if y colocar afuera de él al script con la otra funcion y colocar antes del script con condicion la función que contiene a estas 2 y llamar a esta último función de convergencia-->    
<!--2. colocar a la función de máx monto en el evt de onclick y listo xD [Aunque no sabría decir si funciona = de bien que antes xD-->
<!--3. colocar un operador ternario en el que llames a la función de convergencia solo cuando el request sea "Propias" [es decir que con esto no se requeriría el if para eliRed] y que colocara solo a la de maxMonto cuando no fuera así... xD-->
<!--4. llenar un input con el valor del btn seleccionado luego de saber que no es null xD [para evitar usar JS xD] y dependiendo de eso... naaa, se parece a 3 y tiene aún más agregados xD-->