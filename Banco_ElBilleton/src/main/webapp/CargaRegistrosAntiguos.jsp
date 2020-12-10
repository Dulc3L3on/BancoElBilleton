<%-- 
    Document   : cargaRegistrosAntiguos
    Created on : 24/11/2020, 09:17:15
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page import="Modelo.Herramientas.ControladorIndices"%>
<%@page import="Modelo.Nodo"%>
<%@page import="Modelo.ListaEnlazada"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/sweetDecisionCarga.js" languaje="JavaScript"></script><!--está de más porque no necesito mostrar ningún sweet... puesto que se cambió la manera en la que se muestra el error... ahora está mejor xD-->
        <link rel="stylesheet" href="css/cssCargaDatos.css">
        <title>OldRecords</title>
        <%!ListaEnlazada<String[]> listaErrores = new ListaEnlazada();
           Nodo<String[]> nodoAuxiliar;
           int datoErroneoActual=1;            
           String trabajador;
           GuardiaSeguridad guardia = new GuardiaSeguridad();%>
    </head>
    <body>
        <%if(request.getParameter("reaccion")!=null){%>
            <%if(request.getParameter("reaccion").equals("NO")){//solo que, aunque presione que NO y tengan más de alguna tabla sin registros... lo mandará nuevamente a esta pantalla... debería deshabilitar esta opción cuanod exista este vacío...
                response.sendRedirect("Login.jsp");//REDIRIGIR a la paǵ de add manual y cuando presione finalizar proceso , ahcer a todas las entis autoincre y redirigir con el response xD al LOGIN XD            
            }else if(request.getParameter("reaccion").equals("SI") || request.getParameter("reaccion").equals("REINTENTAR")){                
                response.sendRedirect("CargaRegistrosAntiguos.jsp");//recuerda emlplear este, cuando no tengas necesidad de compartirle los atributos que posee a partir del cual redirigirás [es decir en el lugar en el que estás xD]
            }
        }%> 
        
        <center>
            <%if(request.getAttribute("resultados")==null){%>            
                <form method="POST" enctype="multipart/form-data" action="cargaXML">                    
                    <h1>CARGA DE REGISTROS</h1><br/><br/>
                    <h3>Selecciona el archivo XML en el que se <br/>encuentran los datos a cargar en el sistema</h3>                            
                    <input type="file" id="file" name="archXML" accept=".xml" required>                            
                    <input id="submit" type="submit" name="submit" value="cargar"><!--deberían aparecer a la par xD, puesto que no coloqué un salto de línea... esto lo hice con tal de que la tabla no salga fea por los tamaños que le di a las columnas d ela tabla, puesto que con ellas mostraría los errores...-->                    
                </form>        
                         
            <%}else{
                listaErrores = (ListaEnlazada<String[]>) request.getAttribute("listadoErrores");%>
                <h2>------------->>RESULTADOS<<--------------</h2>                
                    <%if(!listaErrores.estaVacia()){%><!--puesto que no es explícito cuando los reaultados != null, por ello hay que averiguarlo...--->
                        
                        <div style="overflow-y: scroll; height: 800px;">
                            <h4>>PROCESO FALLIDO!</h4><br/>
                            <h5>Registros con errores</h5>
                        
                            <%nodoAuxiliar = listaErrores.obtnerPrimerNodo();
                                if(nodoAuxiliar.contenido[0].equals("Cliente")){%><!--FUNCIONA!!! cuando obtienes el contenido del nodo, lo puedes tratar como si estuviera en su forma normal [es decir con su var corresp y no atrapdo en el nodo xD] por ello si es arreglo puedes colocar los [] si es número puedes usar una funcion que le corresp, si es otro obj puedes invocar a unmétodo que posea... xD supongo que eso tb lo puedes hacer al usar un método que retorna algún valor... xD uuuuu gracias Dios, no me la sabía xD...-->
                                    <h4 id="subencabezado"><%=nodoAuxiliar.contenido[0]%></h4><!--el título de la tabla xD-->                                    
                                    <table id="resultados" style="width: 900px;">
                                        <tr id="titulos">
                                            <th><h5>Código</h5></th>
                                            <th><h5>Nombre</h5></th>
                                            <th><h5>CUI</h5></th>
                                            <th><h5>Dirección</h5></th>
                                            <th><h5>Género</h5></th>
                                            <th><h5>Contraseña</h5></th>
                                            <th><h5>Cunpleaños</h5></th>
                                            <th><h5>PDF DPI</h5></th>
                                            <th id="tipoError"><h5>Tipo Error</h5></th>
                                        </tr>
                                    <%for(int errorActual = datoErroneoActual; errorActual<= listaErrores.darTamanio(); errorActual++){
                                        if(nodoAuxiliar.contenido[0].equals("Cliente")){%><!--esto es por el hecho de que se están revisando todos los nodos y no se sabe cuantos errores de los tipos errados xD hay en la lista...-->
                                            <tr>
                                                <th><%=nodoAuxiliar.contenido[1]%></th>
                                                <th><%=nodoAuxiliar.contenido[2]%></th>
                                                <th><%=nodoAuxiliar.contenido[3]%></th>
                                                <th><%=nodoAuxiliar.contenido[4]%></th>
                                                <th><%=nodoAuxiliar.contenido[5]%></th>
                                                <th><%=nodoAuxiliar.contenido[6]%></th>
                                                <th><%=nodoAuxiliar.contenido[7]%></th>
                                                <th><%=nodoAuxiliar.contenido[8]%></th>
                                                <th id="tipoError"><%=nodoAuxiliar.contenido[9]%></th>
                                            </tr>                                                                                                                         
                                        
                                            <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente;                                                                                   
                                            datoErroneoActual = errorActual+1;
                                        }                                        
                                    }%>
                                   </table>      
                                <%}%>
                                                                 
                                <%for(int trabajadorActual = 0; trabajadorActual< 2; trabajadorActual++){                                                                        
                                    if(datoErroneoActual <= listaErrores.darTamanio()){//<!--coloco aquí la condición porque puede que cajero tenga errores y justo los de él sean los último entonces al tener la condición arriba se intentarí hacer la comparación para ver si lo "siguiente" [que ya no hay pero no se llegó a saber] es un error de cajero o gerente y e ahí la exception de tipo nullPointerException... xD-->
                                        trabajador= nodoAuxiliar.contenido[0];//si no es igual a alguno de estos 2 en la 1ra vuelta, tampoco en la 2da, pero para no poner más if... así está bien xD
                                    
                                        if(trabajador.equals("Cajero") || trabajador.equals("Gerente")){%>                                           
                                           <!--//puesto que el valor que hay en 0 debe ser igual a lo que se tenía al entrar al 2do ciclo xD-->                                    
                                    
                                            <h4 id="subencabezado"><%=trabajador%></h4>                                        
                                            <table id="resultados" style="width: 900px;">
                                                <tr id="titulos">
                                                    <th><h5>Código</h5></th>
                                                    <th><h5>Nombre</h5></th>
                                                    <th><h5>CUI</h5></th>
                                                    <th><h5>Dirección</h5></th>
                                                    <th><h5>Género</h5></th>
                                                    <th><h5>Contraseña</h5></th>
                                                    <th><h5>Turno</h5></th>   
                                                    <th id="tipoError"><h5>Tipo Error</h5></th>
                                                </tr>
                                           <%for(int errorActual = datoErroneoActual; errorActual<= listaErrores.darTamanio(); errorActual++){
                                                if(nodoAuxiliar.contenido[0].equals(trabajador) || nodoAuxiliar.contenido[0].equals(trabajador)){%>
                                                    <tr>
                                                        <th><%=nodoAuxiliar.contenido[1]%></th>
                                                        <th><%=nodoAuxiliar.contenido[2]%></th>
                                                        <th><%=nodoAuxiliar.contenido[3]%></th>
                                                        <th><%=nodoAuxiliar.contenido[4]%></th>
                                                        <th><%=nodoAuxiliar.contenido[5]%></th>
                                                        <th><%=nodoAuxiliar.contenido[6]%></th>
                                                        <th><%=nodoAuxiliar.contenido[7]%></th>                                            
                                                        <th id="tipoError"><%=nodoAuxiliar.contenido[8]%></th>
                                                    </tr>                                                                                                                         
                                                
                                                 <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
                                                 datoErroneoActual = errorActual+1;
                                                 }                                                
                                            }%>
                                            </table>      
                                      <%}%>
                                   <%}%>
                               <%}%>
                                
                                <%if(datoErroneoActual <= listaErrores.darTamanio()){
                                    if(nodoAuxiliar.contenido[0].equals("Transaccion")){%>                                
                                        <h4 id="subencabezado"><%=nodoAuxiliar.contenido[0]%></h4>
                                        <table id="resultados" style="width: 900px;">
                                                <tr id="titulos">
                                                    <th><h5>Código</h5></th>
                                                    <th><h5>Fecha</h5></th>
                                                    <th><h5>Hora</h5></th>
                                                    <th><h5>Número Cuenta</h5></th>
                                                    <th><h5>Tipo</h5></th>
                                                    <th><h5>Monto</h5></th>
                                                    <th><h5>Código Cajero</h5></th>     
                                                    <th id="tipoError"><h5>Tipo Error</h5></th>
                                                </tr>
                                            <%for(int errorActual = datoErroneoActual; errorActual<= listaErrores.darTamanio(); errorActual++){
                                                if(nodoAuxiliar.contenido[0].equals("Transaccion")){%>
                                                    <tr>
                                                        <th><%=nodoAuxiliar.contenido[1]%></th>
                                                        <th><%=nodoAuxiliar.contenido[2]%></th>
                                                        <th><%=nodoAuxiliar.contenido[3]%></th>
                                                        <th><%=nodoAuxiliar.contenido[4]%></th>
                                                        <th><%=nodoAuxiliar.contenido[5]%></th>
                                                        <th><%=nodoAuxiliar.contenido[6]%></th>
                                                        <th><%=nodoAuxiliar.contenido[7]%></th>   
                                                        <th id="tipoError"><%=nodoAuxiliar.contenido[8]%></th>
                                                    </tr>                                                                                                                         
                                        
                                                    <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
                                                    datoErroneoActual = errorActual+1;
                                                }                                                
                                            }%>
                                        </table>                                            
                                     <%}%>
                                <%}%>       
                                <%if(datoErroneoActual <= listaErrores.darTamanio()){
                                    if(nodoAuxiliar.contenido[0].equals("Cuenta")){%>
                                    <h4 id="subencabezado"><%=nodoAuxiliar.contenido[0]%></h4>
                                       <table id="resultados" style="width: 900px;">
                                            <tr id="titulos">
                                                <th><h5>Fecha Creación</h5></th>
                                                <th><h5>Número Cuenta</h5></th>
                                                <th><h5>Código Dueño</h5></th>
                                                <th><h5>Monto</h5></th>     
                                                <th id="tipoError"><h5>Tipo Error</h5></th>
                                            </tr>
                                        <%for(int errorActual = datoErroneoActual; errorActual<= listaErrores.darTamanio(); errorActual++){
                                            if(nodoAuxiliar.contenido[0].equals("Cuenta")){%>
                                                <tr>
                                                    <th><%=nodoAuxiliar.contenido[1]%></th>
                                                    <th><%=nodoAuxiliar.contenido[2]%></th>
                                                    <th><%=nodoAuxiliar.contenido[3]%></th>
                                                    <th><%=nodoAuxiliar.contenido[4]%></th>   
                                                    <th id="tipoError"><%=nodoAuxiliar.contenido[5]%></th>
                                                </tr>                                                                                                                         
                                        
                                                <%nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
                                                datoErroneoActual = errorActual+1;
                                            }                                            
                                        }%>
                                        </table>  
                                    <%}%>
                              <%}
                                   datoErroneoActual =1;%> <!--puesto que es la misma página la que se muestra mientras se mantenga una misma sesión xd [puesto que aunque no tomes en cuenta el id de dicha sesión... si es tomado en cuenta por el navegador xD sino no se podría navegar en la pág [ se oye razonable lo de la no navegación xD]]y por lo tanto son las mismas variables las que revisas cuando regresas, es decir que si las dejaste con valor así las encontrarás a menos que reestablezcas dichos valores...-->
                       </div>                                       
                       <form method="POST" action="CargaRegistrosAntiguos.jsp"><!--sería bueno que indicaras con un msej que implica presionar no ... ya sea un un msje popUp o uno estático... se vería mejor con un popUp xD y que tenga la opción de seguir y de declinar y que dep de la selección de estas hacer autoIncre o no xD-->
                           <%if(guardia.estanTodasLlenas()){%>
                                <h4>Desea reintentar el proceso de carga?</h4>
                                <input id="submit" type="submit" value="NO" name="reaccion" onclick="advertencia()"><!--ENTONCES se redirige al apartado para que lo haga manualmente, con los btn habilitados según las enti en las que hubo fallos...--->
                                <input id="submit" type="submit" value="SI" name="reaccion">
                            <%}else{%>
                                <input id="submit" type="submit" value="REINTENTAR" name="reaccion">
                            <%}%>
                       </form>
                       
                    <%}else{%>
                        <h4>PROCESO EXITOSO!</h4>                        
                        <a id="submit" href="Login.jsp">ACEPTAR</a>
                    <%}%>    
            <%}//fin del else resultados == null%>
        </center>           
    </body>
</html>
