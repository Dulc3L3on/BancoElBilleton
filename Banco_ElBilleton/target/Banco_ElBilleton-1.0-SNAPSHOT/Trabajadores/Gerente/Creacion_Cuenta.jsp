<%-- 
    Document   : Creacion_Cuenta
    Created on : 22/11/2020, 12:36:44
    Author     : phily
--%>

<%@page import="Modelo.Entidades.Usuarios.Cliente"%>
<%@page import="Modelo.Herramientas.ControladorIndices"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" href="../../css/cssGerente.css">
         <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>     
         <link rel="icon" href="img/Logos/Favicon_Banco_ElBilleton.ico"><!--puesto que cuando regresa, lo hace con un dirección directa xD-->
        <title>CreateAccount</title>
        <%!ControladorIndices controlador = new ControladorIndices();
           Cliente cliente;%>
    </head>
    <body>
        <%if(request.getAttribute("mostrarMsje")==null){%>
            <center><!--recuerda que ahora se obtendrán los datos por medio de la notación $_{} puesto que es un atributo... si no funcionara, entonces obtienes el atributo con el request y usas los métodos xD, recibirás un "usuario" o de una vez el tipo... todo depende XD-->                                   
               <%cliente = (Cliente)request.getSession().getAttribute("usuarioBuscado_Cliente");%>            
                <form method="POST" action="../../gestorCreacionCuenta">
                    <div id="contenedorGeneral">
                        <table>                        
                            <tr>
                                <th colspan="2">
                                    <h3 id="subencabezado">>>DATOS DUEÑO DE CUENTA</h3>
                                </th>
                            </tr>
                            <tr id="nombresDatos">
                                <th>
                                    <h5 id="subtitulo">Código:</h5>
                                </th>
                                <th>
                                   <input type="number" name="datosCuenta" id="nombre" value="<%=(cliente!=null)?cliente.getCodigo():""%>" readonly>
                                </th>
                                
                            </tr>
                            <tr>                            
                                <th>
                                    <h5 id="subtitulo">Nombre:</h5>
                                </th>                                
                                <th>
                                    <input type="text" name="datosCuenta" id="CUI" value="<%=(cliente!=null)?cliente.getNombre():""%>" readonly>
                                </th>
                            </tr>                       
                            <tr>                            
                                <th colspan="2">
                                    <h3 id="subencabezado">>>DATOS NUEVA CUENTA</h3>
                                </th>
                            </tr>    
                            <tr id="nombresDatos">                            
                                <th>
                                    <h5 id="subtitulo">Número:</h5>
                                </th>
                                <th>
                                    <input type="number" name="datosCuenta" id="numeroCuenta" min="0" value="<%=(cliente!=null)?controlador.autoincrementarEntidad("numeroCuenta", 4):""%>" readonly><!--Sino cada vez estaría aparenciendo un número de cuenta [diferente o no, dependiendo de si se han creado más cuentas o no...]aunque no se tenga ningún solo dato... xD-->
                                </th>
                                
                            </tr>
                            <tr>                            
                                <th>
                                    <h5 id="subtitulo">Tipo:</h5>
                                </th>                          
                                <th>
                                    <select name="tipoDeCuenta"  id="tipoCuenta" style="width: 500px; height: 35px;" required>
                                        <option value="xD">Monetaria</option><!--no creo que sea necesario poner un vacío en el valor... creo que con no declararlo basta...-->
                                    </select>
                                </th>
                           </tr>
                           <tr id="nombresDatos">                            
                                <th>
                                    <h5 id="subtitulo">* Monto Inicial:</h5>
                                </th>                                                                        
                                <th>
                                    <input type="number" name="datosCuenta" id="monto" min="100" required>                           
                                </th>                            
                            </tr>
                        </table>
                        <%if(request.getSession().getAttribute("usuarioBuscado_Cliente")!=null){%>
                             <input type="submit" id="submit" name="crearCuenta" value="CREAR CUENTA">
                        <%}%><!--para que no existan inconsistencias...-->
                    </div>
                </form>            
            </center>                 
        <%}else{
            if(request.getAttribute("mostrarMsje").equals("correcto")){%>
                <input type="text" id="tipoMsje" value="creacionCuentaExitoso" hidden>
                <script src="js/sweetInformativo.js"></script>
            <%}else{%>
                <input type="text" id="tipoMsje" value="errorCreacionCuenta" hidden>
                <script src="js/sweetError.js"></script>
           <%}
        }%>
    </body>
</html>
