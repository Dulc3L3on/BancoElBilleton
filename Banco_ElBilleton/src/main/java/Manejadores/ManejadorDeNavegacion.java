/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

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
        switch(tipoUsuario){
            case "cliente":
                return "Cliente/Home_Cliente.jsp";
            case "cajero":
                return "Trabajadores/Cajero/Home_Cajero.jsp";
            case "gerente":
                return "Trabajadores/Gerente/Home_Gerente.jsp";        
        }
        
        return null;//pero aquí jamás debería llegar, puesto que no se ejecutará 
       //este bloque, al menos que todo haya salido bien, es decir el nombre de usuario 
       //y contra estén correctos... pero de todos modos sería bueno que tuvieas una página de error...
    }
    
    public String darPaginasAlGerente(String opcionSeleccionada){
        if(opcionSeleccionada!=null){
            switch(opcionSeleccionada){
                case"CREACION":
                    return "Creacion.jsp";
                case"MODIFICACION":
                    return "Modificacion.jsp";
                case"REPORTES":
                    return "Reportes_Gerente.jsp";                
            }
        }                
        return "Perfil_Gerente.jsp";//recuerda que como se tendrá una página defaul, entonces se debe poner que cuando sea == a null mande la del perfil, pues en ese punto no recibiría nada xD 
    }
    
    public String darPaginasCreacion(String tipoCreacion){
        if(tipoCreacion!=null){//podrías hacer que se muestre como default lo de cuentas [para seguir el estándar o mostrar otra página donde se pudiera especificar la temática, o algo por el estilo xD pero el TIEMPO niña EL TIEMPO!!!
            switch(tipoCreacion){                
                case "Cajero":
                    return "Creacion_Cajero.jsp";
                case "Cliente":
                    return "Creacion_Cliente.jsp";
                case "Gerente":
                    return "Creacion_Gerente.jsp";
            }        
        }        
        return "Creacion_Cuentas.jsp";           
    }
    
    public String darPaginasModificacion(String tipoModificacion){
        if(tipoModificacion!=null){//podrías hacer que se muestre como default lo de cuentas [para seguir el estándar o mostrar otra página donde se pudiera especificar la temática, o algo por el estilo xD pero el TIEMPO niña EL TIEMPO!!!
            switch(tipoModificacion){                
                case "Cajero":
                    return "Modificacion_Cajero.jsp";
                case "Gerente":
                    return "Modificacion_Gerente.jsp";                    
            }        
        }        
        return "Modificacion_Cliente.jsp";         
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
                    return "";//esto es con JR... pero creo que tb habrá que crear su propia pag... o ser la misma que a que está en reportes, no la que tiene los btn sino la que está en el frame...
                case "REDACTADAS":
                    return "";                   
            }
        }        
        return "Enviar_Asociacion.jsp";//tendŕia que ser la de recibidas la pág default... pero aún no tengo la pág para el JR... ahí lo cb xD
    }
}
