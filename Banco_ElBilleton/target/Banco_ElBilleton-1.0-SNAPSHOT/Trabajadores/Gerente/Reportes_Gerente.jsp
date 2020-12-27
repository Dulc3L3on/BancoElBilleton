<%-- 
    Document   : Reportes_Gerente
    Created on : 2/12/2020, 15:10:45
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page import="Modelo.Manejadores.DB.BuscadorExistencia"%>
<%@page import="Modelo.Entidades.Objetos.Cuenta"%>
<%@page import="Modelo.Manejadores.DB.BuscadorParaReportesTrabajador"%>
<%@page import="Modelo.Manejadores.DB.BuscadorParaReportesGerente"%>
<%@page import="Modelo.Entidades.Usuarios.Cliente"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page import="Modelo.Entidades.Usuarios.Usuario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssReportes.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>  
        <link rel="icon" href="../../img/Logos/Favicon_Banco_ElBilleton.ico"><!--se que no se mostrará puesto que no se mostrará por el hecho de ser una página interna, pero mejor se lo agrego xD-->        
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           BuscadorParaReportesTrabajador buscadorParaReportesTrabajador = new BuscadorParaReportesTrabajador();           
           Buscador buscador = new Buscador();
           BuscadorExistencia buscadorExistencia = new BuscadorExistencia();
           List<Usuario> listadoUsuarios;
           double limiteMenor;
           double montoMenor;
           Cliente[] clientes;
           Cuenta[] cuentas;%>            
        <title>ManagerReports</title>
    </head>
    <body>
        <%if(guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Gerente")==false){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        
        <div id="ContenedorReportes"><!--le colocaremos un layout para que se organicen de forma "automática"...-->        
            <form method="POST" action="Reportes_Gerente.jsp">
                <center><!--asumo que por medio del nombre podré mandar a la clase que se encarga de traer los reportes [o clases, puesto que algunos requieren de tiepo s de obj que no tienen relación, si es así necesitaría 1 por cada grupo general... bueno, ya veremos xD] qué tipo de reporte es el que quiero..-->
                    <button type ="submit" name="reporte" value="Gerente_HistorialCambiosPropios" <%=(buscadorExistencia.haRealizadoCambiosPropios((String)request.getSession().getAttribute("codigo"))?"":"disabled")%>><img  src="../../img/iconos_Billeton/Historial.png"><br/>Historial <br/>de cambios</button>
   <!--LISTO-->     <button type ="submit" name="reporte" value="Usuario_HistorialCambiosUsuarios"><img  src="../../img/iconos_Billeton/Historial.png"><br/>Historial <br/>de cambios</button><!--por los break entonces solo tendría que colocar los case de tal forma que no llegue a afectar a otro que posea a más de un caso [en especial de usuarios] para establecer los parámetros... xD[si es de que hay otro con más de un tipo de personales xD]-->
   <!--LISTO-->     <button type ="submit" name="reporte" value="MenorMonto_ClientesConMayoresTransacciones"><img  src="../../img/iconos_Billeton/Transaccion.png"><br/>Clientes <br/>con Transacciones<br/>Mayores</button><!--Estos que involucran Clientes no le pondré una revisión previa puesto que para deshabilitarlos tendría que NO existir clientes y eso no es posible puesto que la página LOgin solicita la carga de datos y siempre se caerá a esta página si es que no se ha logeado el usuario en cuestión...-->
   <!--LISTO-->     <button type ="submit" name="reporte" value="MenorSuma_ClientesMayoresSumasTransaccionales"><img  src="../../img/iconos_Billeton/Transaccion.png"><br/>Clientes <br/>con Mayores<br/>Transacciones Sumadas</button><br/><br/>
   <!--LISTO-->     <button type ="submit" name="reporte" value="Nada_ClientesMasDinero"><img  src="../../img/iconos_Billeton/Dinero.png"><br/>Clientes <br/>con Más<br/>Dinero</button><!--Si basta con enviar un MAP NULL cuando un rep no requiera de paráms xD xD, imagino que es porque como no teiene nada que revisar entonces ni siquiera se da cta que el map es null xD, pero como debe enviarse algo entonces por eso se agrega xD bueno aunque eso permite que se pueda generalizar xD... pero si AHORA QUE RECUERDO no enviaste un map null por la horaActual :0 entonces puede que no se arregle con enviar un map null cuando no se requieran paráms :v-->
   <!--LISTO-->     <button type ="submit" name="reporte" value="Fecha_ClientesCuentasAbandonadas"><img  src="../../img/iconos_Billeton/Dinero.png"><br/>Clientes <br/>con Cuentas<br/>Abandonadas</button>
   <!--LISTO-->     <button type ="submit" name="reporte" value="Cliente_HistorialTransacciones"><img  src="../../img/iconos_Billeton/Historial.png"><br/>Historial de<br/>Transacciones</button>
   <!--LISTO-->     <button type ="submit" name="reporte" value="Fecha_CajeroMuyEficiente" <%=(buscadorExistencia.existenTransacciones())?"":"disabled"%>><img  src="../../img/iconos_Billeton/Eficiencia.png"><br/>Cajero<br/>Más Eficiente</button><!--lo mismo digo para la búsqueda de los reportes que involucran transacciones, puesto que si no tuviera nada, el sistema solicitaría la carga puesto que para "iniciar" la página simepre se partirá del Login... xD, pero por si las moscas... :| :v xD-->
                </center>           
            </form>            
        </div>
        
        <%if(request.getParameter("reporte")!=null){%>
            <div id="back" onclick="cancelarEnvio()">               
                <%if(request.getParameter("reporte").equals("Usuario_HistorialCambiosUsuarios")){%>
                    <%listadoUsuarios = buscadorParaReportesTrabajador.buscarUsuariosNoGerentes();%>                                
                    <center>   
                       <div id="form">
                           
                            <form id="formulario" action="../../gestorParametrosGerente" method="POST">
                                <input type="text" name="reporte" value="<%=request.getParameter("reporte")%>" hidden>                                
                                
                                <input type="radio" name="tipoUsuario" value ="Cliente" id="radio1" class="tipoUsuario" checked onclick="esconderOtrosUsuarios()">
                                <label for="radio1">Cliente</label> 
                            
                                <input type="radio" name="tipoUsuario" value ="Cajero" id="radio2" class="tipoUsuario" onclick="esconderOtrosUsuarios()">
                                <label for="radio2">Cajero</label>                                   
                                
                                <h4>* Código o Nombre de Usuario</h4>
                                <input type="search" list="listaUsuarios" name="datosUsuario" required><!--No asocie la lista porque en google se mira feo... pues siempre muestra el listado completo y eso no es lo que quiero, sino que sea como en firefox, muestra el listado de las coincidencias cuando se ha escrito algo, de lo contrario no...-->                                
                                <datalist id="listaUsuarios">                                  
                                    <%for(int usuarioActual=0; usuarioActual<buscadorParaReportesTrabajador.darNumeroDeClientes(); usuarioActual++){%>                                    
                                         <option id="<%=(usuarioActual< buscadorParaReportesTrabajador.darNumeroDeClientes())?"Cliente":"Cajero"%>" value="<%=listadoUsuarios.get(usuarioActual).getCodigo()%> <%=listadoUsuarios.get(usuarioActual).getNombre()%>" <%=(usuarioActual< buscadorParaReportesTrabajador.darNumeroDeClientes())?"":"hidden"%>> </option>                                       
                                    <%}%>
                                </datalist><br/><br/>                                                             
                                <input id="boton" type="submit" value="ACEPTAR">                                     
                           </form>                                                      
                       </div>
                    </center>
                
             <%}else if(request.getParameter("reporte").contains("ClientesConMayoresTransacciones")){
                   montoMenor =  buscadorParaReportesTrabajador.buscarLimiteMinimoReportesTransacciones("limiteMayorMonto");%><!--este es para el menor monto posible de transacciones xD-->                   
                    <center>   
                       <div id="form">    
                            <form id="formulario" action="../../gestorParametrosGerente" method="POST">
                                <input type="text" name="reporte" value="<%=request.getParameter("reporte")%>" hidden>                                
                                <%System.out.println(montoMenor);%>
                                
                                <h4>* Límite menor monto</h4><!--podrías poner un btn para reestablecer el valor... por si acaso no le convenciera el que estableció recientemente...-->
                                <input type="number" name="minimoMonto" value ="<%=montoMenor%>" min="0" required><br/><br/>   
                                
                                <input id="boton" type="submit" value="ACEPTAR">                                                                     
                           </form>                                                      
                       </div>
                    </center>
             <%}else if(request.getParameter("reporte").contains("Sumas")){
                    limiteMenor =  buscadorParaReportesTrabajador.buscarLimiteMinimoReportesTransacciones("limiteMayorSumaMonto");//<!--y este es el mínimo para la suma de transacciones mayor xD-->
                    montoMenor =  buscadorParaReportesTrabajador.buscarLimiteMinimoReportesTransacciones("limiteMayorMonto");%>
                    <center>   
                       <div id="form">    
                            <form id="formulario" action="../../gestorParametrosGerente" method="POST">
                                <input type="text" name="reporte" value="<%=request.getParameter("reporte")%>" hidden>                                
                                
                                <h4>* Límite menor suma</h4>
                                <input type="number" name="minimaSuma" value ="<%=limiteMenor%>" min="<%=montoMenor+1%>" required><br/><br/>   
                                
                                <input id="boton" type="submit" value="ACEPTAR">                                     
                                <h6><i>El límite inferior para 
                                       este reporete es mayor al
                                       del reporte "Clientes con
                                       Transacciones Mayores"</i></h6> 
                           </form>                                                      
                       </div>
                    </center>
             <%}else if(request.getParameter("reporte").contains("Abandonadas") || request.getParameter("reporte").contains("Eficiente")){%>
                    <center>                    
                    <div id="form">
                        <form id="formulario" method="POST" action="../../gestorParametrosGerente">
                            <input type="text" name="reporte" value="<%=request.getParameter("reporte")%>" hidden>
                            <h4>* Fecha Inicial</h4>
                            <input type="date" name="fechaInicial" max="<%=java.time.LocalDate.now()%>" required>      
                            <h4>* Fecha Final</h4>
                            <input type="date" name="fechaFinal" max="<%=java.time.LocalDate.now()%>" required><br/><br/> 
                            <input id="boton" type="submit" value="ACEPTAR">                        
                        </form>
                    </div>                                          
                    </center>       
             <%}else if(request.getParameter("reporte").contains("HistorialTransacciones")){
                    //Deplano que tendrás que hacer que muestre un seet cuando el nombre que
                    //ingresen no exista, de tal forma que no exista una excepción... porque no
                    //es excatamente como un select, puesto que pueden ingresar una palabra que coincida con toda la ingresada en la opción, que puede exista o no...
                    //o hacer algo para que no tenga oporunidad de enviar el form con un nombre inexistente...
                    clientes = (Cliente[])buscador.buscarUsuarios("Cliente", "nombre");
                    cuentas = buscadorExistencia.buscarTodasLasCuentasExistentes();%>
                    
                    <center>
                        <form id="formulario" method="POST" action="../../gestorParametrosGerente">
                            <input type="text" id="datosUsuario" name="reporte" value="<%=request.getParameter("reporte")%>" onkeypress="esconderOtrosUsuarios(event)" hidden>
                            
                            <h4>* Nombre ó Código del Cliente</h4>
                            <input name="datosUsuario" type="search" list="listaClientes" required>
                                
                           <%if(clientes!=null){%>
                               <datalist id="listaClientes">
                                    <%for(int clienteActual=0; clienteActual<clientes.length; clienteActual++){%>                                    
                                           <option value="<%=clientes[clienteActual].getCodigo()%> <%=clientes[clienteActual].getNombre()%>"> </option> 
                                    <%}%>
                                </datalist>
                                
                                <%if(cuentas!=null){%>
                                    <select name="numeroCuenta" id="listadoCuentas"> 
                                        <%for(int cuentaActual = 0; cuentaActual<cuentas.length; cuentaActual++){%>
                                        <option id="<%=cuentas[cuentaActual].getCodigoDuenoCuenta()%>" value="<%=cuentas[cuentaActual].getNumeroCuenta()%>"><%=cuentas[cuentaActual].getNumeroCuenta()%></option>
                                        <%}%>
                                    </select><br/><br/>
                                <%}%>
                            <%}%>
                            <input id="boton" type="submit" value="ACEPTAR">              
                        </form>                
                    </center>                              
              <%}else{
                     request.getRequestDispatcher("../../gestorParametrosGerente").forward(request, response);
                }%>            
            </div>   
        <%}%>     
        
        <script>
            function cancelarEnvio(){               
                var elementoClickeado = document.onclick;
                             
                if(event.srcElement.id === "back"){                    
                    window.location.href = "Reportes_Gerente.jsp";                                          
                }                                                     
            }//NICE XD
        </script>
        <script>
             function esconderOtrosUsuarios(){
                var usuarios = document.getElementById("listaUsuarios").options;                
                var seleccionado = (document.getElementByName("tipoUsuario")[0].checked)?document.getElementByName("tipoUsuario")[0]: document.getElementByName("tipoUsuario")[1];                
                
                for (var elementoActual = 0; elementoActual < usuarios.length; elementoActual++) {
                    if(usuarios[elementoActual].id!== seleccionado.value){
                        usuarios[elementoActual].hidden = true;                        
                    }else{
                        usuarios[elementoActual].hidden = false;                                               
                    }
                }//y así debería hacer invisibles las opciones de los usuarios que no necesito x|
            }             
        </script>
        <script>
            function mostrarCuentasDeDueno(event){
                if(event.keyCode === 13){
                    var cuentas = document.getElementById("listadoCuentas").options;
                
                    for (var numeroCuentaActual = 0; numeroCuentaActual < cuentas.length; numeroCuentaActual++) {
                        if(cuentas[numeroCuentaActual].id === document.getElementById("datosUsuario").value.split(" ")[0]){
                            cuentas[numeroCuentaActual].hidden = false;                    
                        }else{
                            cuentas[numeroCuentaActual].hidden = true;                          
                        }
                    }                                                  
                }                
            }            
        </script>     
       <%if(request.getSession().getAttribute("sinDatos")!=null){%>        
            <input type="text" name="tipoMsje" value="sinDatos" hidden>         
            <script src="../../js/sweetInformativo.js"></script>
            <script src="js/sweetInformativo.js"></script>
           <%request.getSession().removeAttribute("sinDatos");
       }%>        
    </body>
</html>
