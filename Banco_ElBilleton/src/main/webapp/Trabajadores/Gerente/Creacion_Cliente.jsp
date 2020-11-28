<%-- 
    Document   : Creacion_Cliente
    Created on : 22/11/2020, 10:13:01
    Author     : phily
--%>

<%@page import="Modelo.Entidades.Usuarios.Usuario"%>
<%@page import="Modelo.Manejadores.DB.Buscador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../../css/cssGerente.css">
        <title>CreateClient</title>
        <%!Buscador buscador = new Buscador();
           Usuario[] usuarios;%>
    </head>
    <body>
        <select id="DPIsRegistrados" hidden>
            <%usuarios = buscador.buscarUsuario("Cliente", "codigo");
                if(usuarios!=null){
                    for(int usuarioActual=0; usuarioActual< usuarios.length; usuarioActual++){%>                    
                        <option value="<%=usuarios[usuarioActual].getDPI()%>"></option>
                    <%}%>
                <%}%>            
        </select>
        <input type="text" id="msjeDPIrepetido" value="El CUI ingresado se encuentra registrado!" style="color: red; font-size: 15px;" hidden>                
        
        <center>
            <form method="POST" enctype="multipart/form-data" action="../../cargaDPI">
                <div id="contenedorGeneral">
                    <table>                        
                        <tr>
                            <th colspan="2">
                                <h2>>>DATOS NUEVO CLIENTE</h2>
                            </th>
                        </tr>
                        <tr id="nombresDatos">
                            <th>
                                <h5>* Nombre</h5>
                            </th>
                            <th>
                                <h5>* CUI</h5>
                            </th>
                        </tr>
                        <tr>                            
                            <th>
                                <input type="text" name="datosUsuario" id="nombre" required>
                            </th>

                            <th>
                                <input type="number" name="datosUsuario" id="CUI" onblur="verificarDPIcoincidente(this)" minlength="8" maxlength="13" min="0" required><!--yo recuerdo que el pasaporte tiene como mínimo 8#...-->
                            </th>
                        </tr>
                        <tr id="nombresDatos">                            
                            <th>
                                <h5>* Género</h5>
                            </th>
                            <th>
                                <h5>* Fecha nacimiento</h5>
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
                               <input type="date" name="datosUsuario" id="birth" max="<%=java.time.LocalDate.now()%>" required>
                            </th>
                        </tr>
                        <tr id="nombresDatos">                           
                            <th>
                                <h5>* Dirección</h5>                                
                            </th>                            
                        </tr>
                        <tr>                            
                            <th colspan="2">
                               <input type="text" name="datosUsuario" id="direccion" placeholder="Direccion" style="width: 733px;" required><!--si quieres le mandas la fecha actual.... pero por ser "requerido" no hbará problemas de ausencia xD-->
                            </th>
                        </tr>
                        <tr id="nombresDatos">                            
                            <th>
                                <h5>* PDF DPI</h5>
                            </th>                            
                        </tr>
                        <tr>                            
                            <th>
                               <input type="file" name="archDPI" id="dpi" accept=".pdf" required>
                            </th>
                        </tr>
                    </table>
                     <input type="submit" id="submit" name="submit" value="CREAR CLIENTE"><!--todos los sumbit sin importar de qué entidad sean y qué sea lo que suban tendrán el mismo aspecto...-->                    
                </div>
            </form>            
        </center>                  
    <script>
        function verificarDPIcoincidente(inputDPI){
            var DPIsRegistrados = document.getElementById("DPIsRegistrados");
            
            if(DPISRegistrados!==null){
                for(int dpiActual =0; dpiActual< DPIsRegistrados.length; dpiActual++){
                    if(inputDPI.value === DPIsRegistrados.value){
                        inputDPI.value="";
                        document.getElementById("msjeDPIrepetido").hidden="false";
                        return;
                    }                
                } 
                document.getElementById("msjeDPIrepetido").hidden="true";                
            }            
        }
    </script>
    
    </body>
</html>
