/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores;

/**
 *
 * @author phily
 */
public class ManejadorDeNavegacion {
    /**Se ejecuta luego de haber pasado la prueba de 
     * seguridad y da la pagina que correponde al usuario
     * @param tipoUsuario
     * @return 
     */
    public String darPaginasPrincipales(String tipoUsuario){
        if(tipoUsuario!=null){
            switch(tipoUsuario){
                case "Cliente":
                    return "Cliente/Home_Cliente.jsp";
                case "Cajero":
                    return "Trabajadores/Cajero/Home_Cajero.jsp";
                case "Gerente":
                    return "Trabajadores/Gerente/Home_Gerente.jsp";        
            }        
        }        
        return "Login.jsp";//De tal forma que se le muestre el msje de error y pueda reintentarlo...
    }
    
    public String darPaginasAlGerente(String opcionSeleccionada){
        if(opcionSeleccionada!=null){
            switch(opcionSeleccionada){
                case"CREACION":
                    return "Creacion.jsp";
                case"MODIFICACION":
                    return "Modificacion.jsp";
                case"REPORTES":
                    return "Reportes_Gerente.jsp";//recuerda que la apariencia será diferente a la de la versión anterior...              
            }
        }                
        return "Perfil_Gerente.jsp";//recuerda que como se tendrá una página defaul, entonces se debe poner que cuando sea == a null mande la del perfil, pues en ese punto no recibiría nada xD 
    }
    
    public String darPaginasCreacion(String tipoCreacion){
        if(tipoCreacion!=null){//podrías hacer que se muestre como default lo de cuentas [para seguir el estándar o mostrar otra página donde se pudiera especificar la temática, o algo por el estilo xD pero el TIEMPO niña EL TIEMPO!!!
            switch(tipoCreacion){                
                case "Cajero":
                    return "Creacion_Trabajador.jsp?trabajador=CAJERO";
                case "Cliente":
                    return "Creacion_Cliente.jsp";//y aquí [SI SE PUEDE y NO complica la programada... porque ya quello mimir xD] colocarás el valor que se recuperará para mostrar correctamente el título, y almacenar los datos dónde corresponde [es decir la tabla... [recuerda que para hacer la creación, puesdes emplear el mismo método y devolver un usuario o trbajador para el cajero y gerente...]
                case "Gerente":
                    return "Creacion_Trabajador.jsp?trabajador=GERENTE";
            }        
        }        
        return "ListadoUsuarios.jsp?pagina=Creacion_Cuenta.jsp";           
    }
    
    public String darPaginasModificacion(String tipoModificacion){//debido al gestor de recopilación de datos, al seleccionar el botón que corresponde a un elemento del listado de los usuarios correspondientes que se encuentran registrados, no deberá redirigirse al JSP donde está el listado, sino a gesto que se encarga de recopilar los datos... esto por medio del atributo que indica el tipo de entidad [cuenta ó cliente -> cliente, cjaero -> cajero...]
        if(tipoModificacion!=null){//podrías hacer que se muestre como default lo de cuentas [para seguir el estándar o mostrar otra página donde se pudiera especificar la temática, o algo por el estilo xD pero el TIEMPO niña EL TIEMPO!!!
            switch(tipoModificacion){                
                case "Cajero":
                    return "ListadoUsuarios.jsp?pagina=Modificacion_Cajero.jsp";                            
            }//el del gerente no porque eso se trata en su perfil una parte y el resto en ej servlet...        
        }        
        return "ListadoUsuarios.jsp?pagina=Modificacion_Cliente.jsp";         
    }
    
    public void darPaginasReportes(){
    
    }//Creo que si se usará xd, pero variará un poquito, por el hecho de que se tiene que [mira abajo xD]
       /* 1. llamar al método para que realice la búsqueda de los datos [esto equivalente a la búsuqeda de la página]
          2. revisar si fue exitosa
            2. 1 enviar el objeto al parámetro para mandarlo al JSP [la "fusión", como la que hixo el auxi en e lservlet]
                2.1.1 enviar la URL con el formato del reporte con este método, a la parte donde la necesita el servlet...
            2. 2 sino entonces mostrar la página de error que es universal [o en todo caso el msje xD}
        */
    
    public String darPaginasAlCajero(String opcionSeleccionada){
        if(opcionSeleccionada!=null){
            switch(opcionSeleccionada){
                case "DEPOSITO":
                    return "Deposito.jsp";
                case "RETIRO":
                    return "Retiro.jsp";
                case "REPORTES":
                    return "Reportes_Cajero.jsp";                   
            }
        }        
        return "Perfil_Cajero.jsp";
    }
    
    
    public String darPaginasAlCliente(String opcionSeleccionada){
        if(opcionSeleccionada!=null){
            switch(opcionSeleccionada){
                case "TRANSFERENCIA":
                    return "Transferencia.jsp";
                case "ASOCIACION":
                    return "Asociacion.jsp";
                case "REPORTES":
                    return "Reportes_Cliente.jsp";                   
            }
        }        
        return "Perfil_Cliente.jsp";
    }
    
    public String darPaginasAsociacion(String opcionSeleccionada){
        if(opcionSeleccionada!=null){
            switch(opcionSeleccionada){                                    
                case "RECIBIDAS":
                    return "Solicitudes_Recibidas.jsp";//esto es con JR... pero creo que tb habrá que crear su propia pag... o ser la misma que a que está en reportes, no la que tiene los btn sino la que está en el frame...
                case "ENVIADAS":
                    return "";                   
            }
        }        
        return "Enviar_Asociacion.jsp";//tendŕia que ser la de recibidas la pág default... pero aún no tengo la pág para el JR... ahí lo cb xD
    }//recuerda que tb podrías mandar la denominación del servlet como webServlet, puesto que se emplearía el doGet de dicho servlet para hacer las búsquedas que el JSP necesita para mostrar los comp antes de que el user pueda interactuar con él, los cuales serían enviadoas ocmo atrib desde el gestor hacia el JSP con el dispatcher xD... claro si es necesario xD
}
