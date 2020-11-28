/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.ListaEnlazada;
import Modelo.Nodo;

/**
 *
 * @author phily
 */
public class Verificador {      
    ListaEnlazada<String> listadoDPIs = new ListaEnlazada();    
    ListaEnlazada<String> listadoCodigosUnUsuario = new ListaEnlazada();//puesto que haré la revisión se hace de primero de un usuario, luego de otro y al terminar este el útimo [pues en este caso son 3 xD] entonces el listado vivirá mientras se esté en un usuario xD lo hago así pues eso es lo que necesito ya que si podrían haber codigos iguales en otras tablas pero ese es otro ambiente, a diferencia del DPI que si solo puede haber 1 sin importar que tipo de Usuario sea...
    
    public String verificarDatosCompletosUsuario(String codigo, String CUI, String nombre, String genero, String turno, String path){                    
        String resultadoVerificacion = verificarUnicidadCodigo(codigo);//si llegara a aparecer es un error faltal... por ello ya no se sigue revisando...
         
        if(resultadoVerificacion.equals("???")){
            resultadoVerificacion = verificarUnicidadDPIxml(CUI);//este tb es fatal xD, es decir con solo este basta para decir que es un error...
            
            if(resultadoVerificacion.equals("???")){//Es decir no es repetido xD
                String segundoResultado = verificarDatosUsuario(nombre, genero, turno, path);//y así de esta manera si no hay errores hallados de ningún tipo, se devulve ??? xD
                resultadoVerificacion+= (segundoResultado.isEmpty())?"":segundoResultado;
            }        
        }                                
        return resultadoVerificacion;
    }    
    
    private String verificarDatosUsuario(String nombre, String genero, String turno, String path){//el DPI no porque puede ser el código de otro documento personal [pasaporte supuestamente tien 8 dígitos, pero no se si es el máximo...]
        String datosErrados="";//para que si se cumpla la condi de estar vacío...
        
        if(!permitirSoloLetras(nombre)){
            datosErrados+="-Nombre-";
        }
        if(!verificarGenero(genero)){
            datosErrados+="-Genero-";
        }
        if(!verificarTurno(turno)){
            datosErrados+="-Turno-";
        }
        if(!verificarExtension(path)){
            datosErrados+="-Extension-";
        }
        
        return datosErrados;
    }
    
    private boolean permitirSoloLetras(String nombre){        
        char[] nombreDesglosado;
        nombreDesglosado = nombre.trim().replace(" ", "").toCharArray();
        
        for (int caracterActual = 0; caracterActual < nombreDesglosado.length; caracterActual++) {
            if(!(nombreDesglosado[caracterActual]>64 && nombreDesglosado[caracterActual]<91)
                 && !(nombreDesglosado[caracterActual]>96 && nombreDesglosado[caracterActual]<123)){
                return false;
            }
        }
        return true;
    }
    //no se si el pasaporte incluye letras por eso no pongo permitir solo números...
    
    private boolean verificarGenero(String genero){
        if(!genero.equalsIgnoreCase("femenino") && !genero.equalsIgnoreCase("masculino")){
            return false;
        }
        return true;
    }
    
    private boolean verificarTurno(String turno){
        if(turno!=null && (!turno.equalsIgnoreCase("matutino") && !turno.equalsIgnoreCase("vespertino"))){//puesto que para los usuarios que no contengan ese tipo de dato mandarán un null...
            return false;
        }
        return true;
    }
    
    private boolean verificarExtension(String path){       
        
        if(path!= null){//los usuarios que no posean ese tipo de dato enviarán un null, por ello debe verificarse de primero que no tenga o sea xD este valor xD
            String[] pathPartido = new String[2]; 
            pathPartido = path.split("\\.");
            
            if(!pathPartido[1].equals("pdf")){//Se supone que fijos deberían tener almacenados archivos... así que el arreglo si tendría que almacenar en 0 el nombre y en 1 la extensión...
                return false;
            }                        
        }
        return true;
    }

    private String verificarUnicidadDPIxml(String CUIrecibido){
        Nodo<String> nodoAuxiliar = listadoDPIs.obtnerPrimerNodo();
        
        for (int dpiActual = 0; dpiActual < listadoDPIs.darTamanio(); dpiActual++) {//puesto que hay que revisar y no solo añadir...
            if(nodoAuxiliar.contenido.equals(CUIrecibido)){
                return "-DPI repetido-";
            }
            
            nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
        }
        
        listadoDPIs.anadirAlFinal(CUIrecibido);//Cuando no exista ninguno, por el hecho de tener tamaño 0 e iniciar en 0 no recorrerá nada xD y por ello no habrá problemas xD
        return "???";   
    }
    
    private String verificarUnicidadCodigo(String codigoUsuario){
        Nodo<String> nodoAuxiliar = listadoCodigosUnUsuario.obtnerPrimerNodo();
        
        for (int codigoActual = 0; codigoActual < listadoCodigosUnUsuario.darTamanio(); codigoActual++) {//puesto que hay que revisar y no solo añadir...
            if(nodoAuxiliar.contenido.equals(codigoUsuario)){
                return "-Código repetido-";
            }            
            nodoAuxiliar = nodoAuxiliar.nodoSiguiente;
        }
        
        listadoCodigosUnUsuario.anadirAlFinal(codigoUsuario);//Cuando no exista ninguno, por el hecho de tener tamaño 0 e iniciar en 0 no recorrerá nada xD y por ello no habrá problemas xD
        return "???";   
    }
    
    public void limpiarListadoCodigos(){
        listadoCodigosUnUsuario.limpiar();    
    }
    
    //no se revisarán fechas porque se debe saber que signo es el que separa a los números... y eso puede variar así que...
    //y las horas no porque.... porque no reviso las fechas [para manejar un estándar xD]
}
