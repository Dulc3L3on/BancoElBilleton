<%-- 
    Document   : Creacion_Trabajador
    Created on : 22/11/2020, 12:26:52
    Author     : phily
--%>

<%@page import="Modelo.Entidades.Usuarios.Usuario"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script> 
        <link rel="stylesheet" href="../../css/cssGerente.css">
        <title>CreateWork</title>
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();
           Buscador buscador = new Buscador();
           Usuario[] usuarios;%>
    </head>
    <body>
        <%if(guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Gerente")==false){%>            
            <%response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        <%if(guardia.estaEnHorario("Gerente", (String) request.getSession().getAttribute("codigo"))==false){%>
            <input type="text" id="tipoMsje" value="fueraDeHorario" hidden>
            <script src="../../js/sweetInformativo.js"></script><!--recuerda que veremos cómo está la apariencia de la página cuando se redireccione ella misma hacia aquí para add o no el sweet con una dir menos profunda o no xD-->
        <%}else{%>
             <select id="DPIsRegistrados" hidden>
                <%for (int tipoUsuarioActual = 0; tipoUsuarioActual < 3; tipoUsuarioActual++) {                                            
                    usuarios = buscador.buscarUsuarios(((tipoUsuarioActual==0)?"Cliente":(tipoUsuarioActual==1)?"Cajero":"Gerente"), "codigo");                  
                    if(usuarios!=null){
                        for(int usuarioActual=0; usuarioActual< usuarios.length; usuarioActual++){%>                    
                            <option value="<%=usuarios[usuarioActual].getDPI()%>"></option>
                        <%}%>
                    <%}%>            
                <%}%>
            </select>                     
        
            <center>
                <form method="POST" action="../../gestorCreacionTrabajadores">
                    <div id="contenedorGeneral">
                        <input type="text" name="trabajador" value="<%=request.getParameter("trabajador")%>" hidden>
                        <table>                        
                            <tr>
                                <th colspan="2">
                                    <h2>>>DATOS NUEVO <%=request.getParameter("trabajador")%></h2><!--trabajador será reemplazado por el valor que se transportó por la queryString, por medio de la URL enviada por el manejadorDeNavegación...-->
                                </th>
                            </tr>
                            <tr id="nombresDatos">
                                <th>
                                    <h5>* Nombre</h5>
                                </th>
                                <th>
                                    <h5>* CUI</h5>
                                    <div id="aviso" style="color: red; font-size: 15px;" hidden>                                   
                                        <h4>El CUI le pertenece a agluien más</h4>                                                                   
                                    </div>          
                                </th>
                            </tr>
                            <tr>                            
                                <th>
                                   <input type="text" name="datosUsuario" id="nombre" required>
                                </th>
                                
                                <th>
                                    <input type="number" name="datosUsuario" id="CUI" onblur="verificarDPIcoincidente(this)" minlength="8" maxlength="13" min="0" required><!--recuerda que 8 por el pasaporte...-->
                                </th>
                            </tr>                                                 
                            <tr id="nombresDatos">                            
                                <th>
                                    <h5>* Género</h5>
                                </th>
                                <th>
                                    <h5>* Turno</h5>
                                </th>   
                            </tr>                                                       
                            <tr>
                                <th>
                                    <select name="datosUsuario" id="genero"  style="height: 40px;" required>
                                        <option value="femenino" selected>Femenino</option>
                                        <option value="masculino">Masculino</option>
                                    </select>
                               </th>                                                                                         
                               <th>
                                   <select name="datosUsuario" id="turno"  style="height: 40px;" required>
                                        <option value="matutino" selected>Matutino</option>
                                        <option value="vespertino">Vespertino</option>
                                   </select>
                                </th>
                            </tr>                            
                            <tr id="nombresDatos">                           
                                <th>
                                    <h5>* Dirección</h5><!--ahí te recuerda que cuando estés creando a los trabajadores el arreglo no tiene el mismo orden que el de la vrs anterior de este proyecto, por el hecho de que ahora dirección está arribita xD...ahí comparas el orden antiguo con el de ahora...-->                             
                                </th>          
                                 <th>
                                    <h5>Correo</h5>                                
                                </th> 
                            </tr>
                            <tr>                            
                                <th>
                                   <input type="text" name="datosUsuario" id="direccion" placeholder="Direccion" required><!--si quieres le mandas la fecha actual.... pero por ser "requerido" no hbará problemas de ausencia xD-->
                                </th>
                                 <th>
                                   <input type="email" name="datosUsuario" id="correo" placeholder="Correo Eletrónico">
                                </th>
                            </tr>   
                            <tr id="nombresDatos">                                                                                
                            </tr>
                            <tr></tr>
                        </table>
                        <input type="submit" id="submit" name="submit" value="CREAR <%=request.getParameter("trabajador")%>"><!--todos los sumbit sin importar de qué entidad sean y qué sea lo que suban tendrán el mismo aspecto...-->                    
                    </div>
                </form>            
            </center>                   
        <%}%>
    
     <script>
        function verificarDPIcoincidente(inputDPI){
            var DPIsRegistrados = document.getElementById("DPIsRegistrados").options;
            
            if(DPIsRegistrados!==null){
                for(let dpiActual =0; dpiActual< DPIsRegistrados.length; dpiActual++){
                    if(inputDPI.value === DPIsRegistrados[dpiActual].value){//no creo qu ede error con el hecho de que tenga valor nulo, porque de todos modos no sería igual...
                        inputDPI.value="";
                        document.getElementById("aviso").hidden= false;
                        return;
                    }                
                } 
                document.getElementById("aviso").hidden= true;                
            }            
        }
    </script>
    </body>
</html>
