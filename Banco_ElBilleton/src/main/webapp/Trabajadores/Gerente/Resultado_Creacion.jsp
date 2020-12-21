<%-- 
    Document   : Resultado_Creacion.jsp
    Created on : 27/11/2020, 11:33:20
    Author     : phily
--%>

<%@page import="Modelo.Herramientas.GuardiaSeguridad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/cssGerente.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script> 
        <link rel="icon" href="img/Logos/Favicon_Banco_ElBilleton.ico"><!--se que no se mostrará puesto que no se mostrará por el hecho de ser una página interna, pero mejor se lo agrego xD-->        
        <%!GuardiaSeguridad guardia = new GuardiaSeguridad();%>
        
        <title>ResultCreation</title>
    </head>
    <body>
        <%if(!guardia.esPermitidaEstadia(request, response, (String) request.getSession().getAttribute("codigo"), "Gerente") && !guardia.estaEnHorario("Gerente", (String) request.getSession().getAttribute("codigo"))){
            response.sendRedirect(request.getContextPath() + "/Login.jsp");//el context, es para obtener la dirección raiz, es decir la que tiene solo el nombre del proyecto y el servidor... [o cviceversa mejor dicho xD]            
        }%>
        <center>
            <%if(request.getAttribute("mostrarMsje")== null){%>    
                
                <table id="resultados" border="solid"> 
                    <tr id="subencabezado">
                        <th colspan="4">
                            <center>
                                <h2>------------->>>DATOS DEL USUARIO<<<-----------</h2>
                            </center>                
                        </th>
                   </tr>
                   <tr>
                        <th>
                            <h4><strong>Código:</strong></h4>
                       </th>
                       <th>
                           <h5 id="datos">${usuario.getCodigo()}</h5>
                       </th>
                       <th>
                           <h4><strong>Nombre:</strong></h4>
                       </th>
                       <th>
                            <h5 id="datos">${usuario.getNombre()}</h5>
                       </th>
                   </tr>
                   <tr>
                       <th>
                           <h4><strong>Password:</strong></h4>
                       </th>
                       <th>
                           <h5 id="datos">${usuario.getPassword()}</h5>
                       </th>
                       <th>
                            <h4><strong>DPI:</strong></h4>
                       </th>
                       <th>
                           <h5 id="datos">${usuario.getDPI()}</h5>
                       </th>                    
                   </tr>
                   <tr>
                       <th>
                           <h4><strong>Dirección:</strong></h4>
                       </th>
                       <th>
                           <h5 id="datos">${usuario.getDireccion()}</h5>
                       </th>
                       <th>
                           <h4><strong>Sexo:</strong></h4>
                       </th>
                       <th>
                           <h5 id="datos">${usuario.getSexo()}</h5>
                       </th>
                   </tr>
                   <tr>
                       <%if(request.getAttribute("birth")!=null){%>
                           <th>
                               <h4><strong>Cumpleaños:</strong></h4>
                            </th>
                            <th>
                               <h5 id="datos">${usuario.getBirth()}</h5><!--o bien podrías usar birth, para colocar el valor de na vez xD...-->
                            </th>
                      <%}%>
                      <%if(request.getAttribute("turno")!=null){%>
                           <th>
                                <h4><strong>Turno:</strong></h4>
                           </th>
                           <th>
                                <h5 id="datos">${turno.toString()}</h5><!--o bien podrías usar birth, para colocar el valor de na vez xD...-->
                           </th>
                      <%}%>                        
                   </tr>                                           
               </table><!--esto debería ser reemplazado por el html del JR...-->                                                                                                  
                
            <%}else if(request.getAttribute("mostrarMsje").equals("repetido")){%> 
                <input type="text" id="tipoMsje" value="CUIrepetido" hidden>
                <script src="js/sweetError.js"></script>   
            <%}else{%>    
                <input type="text" id="tipoMsje" value="erroCreacionUsuario" hidden>
                <script src="js/sweetError.js"></script>                     
            <%}%>
            
            <input type="submit" id="submit" name="descargarRestultados" value="DESCARGAR"><!--aún no coloco el form porque tengo que resolver cómo le voy a indicar a la clase que maneja los reportes [1ro DIos sea solo 1 xD para minimizar el # de clases xD] cuál es el que debe procesar... [es decir lo que mandará, los datos que buscará y así xD... al parecer, será una clase de convergencia y tendrán que haber otras [no muchas xD] que se encarguen de mandarle lo qu enecesite para hacer la transformación...-->
        </center>           
    </body>
</html>
