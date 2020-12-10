<%-- 
    Document   : Enviar_Asociacion
    Created on : 22/11/2020, 22:55:04
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.Analizador"%>
<%@page import="Modelo.Entidades.Objetos.Asociacion"%>
<%@page import="Modelo.Entidades.Usuarios.Cliente"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/cssCliente.css">        
        <link rel="stylesheet" href="css/cssCliente.css"><!--esto por el hecho de emplear el get para llenar al obj cliente...-->             
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script> 
        <link rel="icon" href="../../img/Logos/Favicon_Banco_ElBilleton.ico"><!--se que no se mostrará puesto que no se mostrará por el hecho de ser una página interna, pero mejor se lo agrego xD-->        
        
        <title>SendAssociation</title>
        <%String ubicacionGestor = "../gestorEnvioSolicitud";%>
    </head>
    <body>    
        <%if(request.getAttribute("ubicacionGestor")!=null){//Aquí SÍ es necesario esto puesto que al principio se llega aquí de una forma [es decir con la dirección de forma directa...], pero después se llega aquí desde el servlet... por lo cual si cb la ubicación... a diferencia de las solicitudes recibidas [El JSP específicamente] siempre se llega a ellas desde una misma dirección...            
            ubicacionGestor = (String) request.getAttribute("ubicacionGestor");
            System.out.println("ubicacion gestor"+ ubicacionGestor);
        }%>            
            
        <div style="margin-top: 105px;">
        <%if(request.getAttribute("mostrarMsje")==null){%>
            <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->
                <form method="GET" action="<%=ubicacionGestor%>">                
                    <table>
                        <tr>
                            <th> 
                                <h4>>Cuenta A Asociar</h4>
                            </th>                    
                            <th>
                                <center>
                                    <input type="number" name="cuentaBuscada" placeholder="Número de Cuenta" id="buscada" required>
                                </center>                                
                            </th><!--NOTA: NO me parece correcto mostrarle al usuario el listado de todas las cuentas existentes con el nombre del dueño, sé que a cuentas ajenas solo se puede depositar, pero aún así NO, no mostraré el datalist... es fácil hacerlo pero no lo haré xD-->
                            <th>
                                <center>
                                    <input type="submit" name="submit" id="busqueda" value="BUSCAR">
                                </center>                                
                            </th>
                        </tr>
                         <tr>
                            <th colspan="3">
                                <h5>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>o<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<</h5>
                            </th>                        
                        </tr>
                        <tr>
                            <th colspan="3">
                                <center>
                                    <h6><i>Tienes 3 intentos para enviar una solicitud de asociación a una misma cuenta</i></h6>
                                </center>                                                                                        
                            </th>                            
                        </tr>
                       
                    </table>                
               </form>                                                       
            
                <%if(request.getAttribute("cliente")!=null){%><!--es decir que ya se empleó el get del gestorEnvioSolicitud... que sería lo único por lo cual podría tener algún valor el cliente, puesto que al terminar su trabajo el doPost del gestoEnvioSoli muestra el msje corresp y nada más por lo cual el user está obligado a presionar nuevamente el btn para reailar un envío si es que esto desea y por el hecho de usar un response, los request y sus atrib no se conservan xD jajaja xD-->
                    <form method="POST" action="gestorEnvioSolicitud"><!--coloco así la dir por el hecho de emplear el get, de lo contrario debería ser ../gestorEnvioSolicitud-->
                        <div id="contenedorGeneral">
                            <input type="text" name="codigoSolicitado" value="${cliente.getCodigo()}" hidden>
                            <table>                        
                                <tr>
                                    <th colspan="2">
                                        <h3 id="subencabezado">>>INFORMACIÓN DE ASOCIACIÓN</h3>
                                    </th>
                                </tr>
                                <tr>
                                    <th>
                                        <h5 id="subtitulo">Número de intentos</h5>
                                    </th>
                                    <th>
                                       <input type="number" name="numeroIntentos" id="intentos" value="${numeroIntentos}" readonly><!--le sumo 1 puesto que cuando llegue a los 3, se mostrará el msje de error y no la pantalla normal en donde puede realizar la solicitud...-->
                                    </th>                            
                                </tr>
                                <tr id="nombresDatos">
                                    <th>
                                        <h5 id="subtitulo">Nombre</h5>
                                    </th>
                                    <th>
                                       <input type="text" name="nombreDueno" id="nombre" value="${cliente.getNombre()}" readonly>
                                    </th>                            
                                </tr>                                          
                                <tr>
                                    <th>
                                       <h5 id="subtitulo">DPI</h5>
                                    </th>                                
                                    <th>
                                        <input type="number" name="DPI" id="DPI" value="${cliente.getDPI()}" readonly>
                                    </th>
                                </tr>
                                 <tr id="nombresDatos">                            
                                    <th>
                                        <h5 id="subtitulo">* Número Cuenta</h5>
                                    </th>
                                    <th>
                                       <input type="number" name="numeroCuenta" id="numeroCuenta" min="0" value="<%=request.getParameter("cuentaBuscada")%>" readonly>
                                    </th>                            
                                </tr>                            
                            </table>
                            <input type="submit" id="enviar" name="solicitar" value="ENVIAR SOLICITUD">
                        </div>
                    </form>            
               <%}else if(request.getParameter("cuentaBuscada")!=null && request.getAttribute("cliente")==null){
                    if((Integer)request.getAttribute("situacionBusqueda")==-1){%>
                        <!--se muestra el sweet mostrando un msje indicando que algo salió mal en la búsqueda...-->                        
                        <input type="text" id="tipoMsje" value="errorBusquedaDueno" hidden><!--lo quería pequeño [Es decir con el atrib toast, pero ya tenía un msje de este tipo creado, así que...-->
                        <script src="js/sweetError.js"></script>            
                        
                        <%}else if(String.valueOf(request.getAttribute("situacionAnalizada")).equals("aceptada")){%>
                            <!--se muestra un msje informando que ya posee una asociación con la cuenta ingresada......-->                            
                            <input type="text" id="tipoMsje" value="solicitudAceptada" hidden>
                            <script src="js/sweetInformativo.js"></script>            
                            
                    <%}else if(String.valueOf(request.getAttribute("situacionAnalizada")).equals("agotada")){%>
                        <!--ya acabo sus 3 intentos-->
                        <input type="text" id="tipoMsje" value="intentosAgotados" hidden>
                        <script src="js/sweetInformativo.js"></script>            
                        
                    <%}else if(String.valueOf(request.getAttribute("situacionAnalizada")).equals("sinReaccion")){%>
                        <!--ya envió una y aún no le han respondido...-->
                        <input type="text" id="tipoMsje" value="sinReaccion" hidden>
                        <script src="js/sweetInformativo.js"></script>     
                    <%}else if(String.valueOf(request.getAttribute("situacionAnalizada")).equals("cuentaPropia")){%>
                        <!--pues... no es lógico que envíe una solicitud hacia una de sus propias ctas... pues ya están realcionadas :v xD-->
                        <input type="text" id="tipoMsje" value="cuentaPropia" hidden>
                        <script src="js/sweetInformativo.js"></script>     
                    <%}%>                            
                <%}%>
        <%}else{%>
            <%if(request.getAttribute("mostrarMsje").equals("exitoso")){%>
                <input type="text" id="tipoMsje" value="envioExitoso" hidden>
                <script src="js/sweetInformativo.js"></script>
            <%}else{%>
                <input type="text" id="tipoMsje" value="errorEnvio" hidden>
                <script src="js/sweetError.js"></script>            
            <%}%>
        <%}%>
            </center>       
        </div>                  
    </body>
</html>
