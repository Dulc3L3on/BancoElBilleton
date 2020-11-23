<%-- 
    Document   : Creacion_Trabajador
    Created on : 22/11/2020, 12:26:52
    Author     : phily
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" href="../../css/cssGerente.css">
        <title>CreateWork</title>
    </head>
    <body>
        <center>
            <form method="POST" enctype="multipart/form-data" action="../../cargaDPI">
                <div id="contenedorGeneral">
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
                            </th>
                        </tr>
                        <tr>                            
                            <th>
                               <input type="text" name="datosUsuario" id="nombre" required>
                            </th>

                            <th>
                                <input type="number" name="datosUsuario" id="CUI"required>
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
                               <input type="date" name="datosUsuario" id="birth" required>
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
                                <h5>* Turno</h5>
                            </th>                            
                        </tr>
                        <tr>                            
                            <th>
                               <select name="datosUsuario" id="genero"  style="height: 40px; width: 200px;" required>
                                    <option value="matutino" selected>Matutino</option>
                                    <option value="vespertino">Vespertino</option>
                               </select>
                            </th>
                        </tr>
                    </table>
                     <input type="submit" id="submit" name="submit" value="CREAR CLIENTE"><!--todos los sumbit sin importar de qué entidad sean y qué sea lo que suban tendrán el mismo aspecto...-->                    
                </div>
            </form>            
        </center>                   
    </body>
</html>
