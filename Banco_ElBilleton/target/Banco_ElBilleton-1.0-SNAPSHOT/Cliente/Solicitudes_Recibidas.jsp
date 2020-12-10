<%-- 
    Document   : Solicitudes_Recibidas
    Created on : 22/11/2020, 23:15:58
    Author     : phily
--%>

<%@page import="Modelo.Entidades.Objetos.Asociacion"%>
<%@page import="Modelo.Entidades.Usuarios.Cliente"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../css/cssCliente.css">
        <link rel="stylesheet" href="css/cssCliente.css">
        <title>RequestReceived</title>
        <%!Buscador buscador = new Buscador();
           Cliente clienteSolicitante;
           Asociacion[] solicitudes;
           String[] datosInteraccion;%>
    </head>
    <body>                
        <%solicitudes = buscador.buscarSolicitudes("RECIBIDAS","codigoSolicitado", (String) request.getSession().getAttribute("codigo"));%><!--aunque por el hecho de que aún no ha ido al doPost quien se encarga de cb el estado por medio del cual se sabe si aún es solicitud o no, entonces no habría problema con que estuviera antes de la redirección al gestor, pero si sale bien para evitar realizar una axn extra, eso considerando que el valor del obj solicitudes se conserva, sino pues pasalo antes del bloque de arriba xD y listo xD-->
         <br/>
        <%if(buscador.darTipoSituacion()==1){%>
            <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->               
                <form method="POST" action="<%=(request.getAttribute("ubicacionGestor")!=null?(String)request.getAttribute("ubicacionGestor"):"../gestorSolicitudesRecibidas")%>">                
                    <%request.getSession().setAttribute("solicitudes", solicitudes);%><!--para que se pueda utilizar la solcitud del arreglo que corresponde al número enviado por medio del btn...-->
                    <table id="listadoSolicitudes">
                        <tr>
                            <th colspan="4">
                                <h3 id="subencabezado">>>SOLICITUDES</h3>
                            </th>                       
                        </tr>
                        <tr>
                            <th>
                                <h5><strong>Datos Solicitante</strong></h5>
                            </th> 
                            <th>
                                <h5><strong>Cuenta Solicitada</strong></h5>
                            </th>
                            <th colspan="2"></th>                                                
                        </tr>                        
                        <%for(int solicitudActual=0; solicitudActual<solicitudes.length; solicitudActual++){
                               clienteSolicitante = (Cliente)buscador.buscarUsuario("Cliente", "codigo", String.valueOf(solicitudes[solicitudActual].getCodigoSolicitante()));//puesto que requiero los datos por el cual se armó la solicitud...
                            %>
                            <%if(clienteSolicitante!=null){%><!--de tal forma que el cliente no se de cuenta del error xD y por ello tb no le coloqué numeración xD-->
                                <tr>
                                    <th style="width: 700px;"> 
                                        <h5><%=clienteSolicitante.getNombre()%> [<%=clienteSolicitante.getDPI()%>]</h5>
                                    </th>               
                                    <th>
                                        <center>
                                            <h5><%=solicitudes[solicitudActual].getNumeroCuentaSolicitado()%></h5>
                                        </center>        
                                    </th>
                                    <th>
                                        <center>
                                            <button type="submit" name="reaccion" class="reaccion aceptada" value="<%=solicitudActual%> aceptada">ACEPTAR</button>                                            
                                        </center>                                
                                    </th>
                                    <th>
                                        <center>
                                            <button type="submit" name="reaccion" class="reaccion aceptada" value="<%=solicitudActual%> rechazada">RECHAZAR</button>
                                        </center>                                
                                    </th>
                                </tr><!--las solicitudes que aquí se muestren serán las que tengan como estado "enEspera"... pero eso no bastaría para mostrar el globito de notificaciones... dependería del atrib "vista" o del atrib "fecha".... o de ambas, por el hecho de que puede que revise o no en la fecha que corresponde [es decir en aquella en la que le "cayeron", las solicitudes recibidas...-->
                                <tr style="height: 35px;">
                                    <th>
                                        <h6><i><%=solicitudes[solicitudActual].getFechaCreacion()%></i></h6>
                                    </th>                            
                                </tr>
                            <%}%>
                        <%}%>
                    </table>                
                </form>             
            </center>
        <%}else if(buscador.darTipoSituacion()==0){%>
            <!--se muestra el msje de SIN SOLICITUDES-->
        <%}else{%>
            <!--es decir que SURGIÓ UN ERROR porque estado de búsqueda ==-1 xD-->
        <%}%><!--si este valor fuera == null quiere decir que surgió un error en el servlet, no en mis métodos xD-->
        
        <%if(request.getAttribute("mostrarMsjeError")!=null){%>
            <!--se muestra el sweet sea qu ehalla salido bien la búsuqeda de las solicitudes o no... por ello se posiciona en una ubicación de la esquina inferior derecha en la que no tape al msje de que surgió un error al buscar... si es qu ellagara a suceder ademaś del error del registro de la soli...-->
        <%}%>
    </body><!--solo con esto basta puesto que siempre se estará cayendo a este JSP mientras se esté interactuando con las solicitudes recibidas...-->
</html>
