/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Herramientas;

import Modelo.Entidades.Usuarios.Usuario;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author phily
 */
public class Kit {
    private final String contraseniaMaestra = "ElBilleton";
    
     public String darFechaActualString(){
        return java.time.LocalDate.now().toString();               
    }//:v xD
    
    public java.util.Date convertirStringAUtilDate(String fecha){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            return formatoFecha.parse(fecha);
     
        } catch (ParseException ex) {
            System.out.println("error al convetir la fecha");
        }
        
        return null;        
    }
     
    public String darHoraActual(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);                
    }  
     
    public int darAnioActual(){
        Calendar cal= Calendar.getInstance();
        return cal.get(Calendar.YEAR);            
    }
    
    public int darNumeroDeHoraActual(){
        Calendar cal= Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);      
    }
    
    public int darMinutosActuales(){
        Calendar cal= Calendar.getInstance();
        return cal.get(Calendar.MINUTE);      
    }
     
    public String encriptarContrasenia(String contraseniaReal){    
        String encriptada=null;        
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(contraseniaMaestra.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = contraseniaReal.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.getEncoder().encode(buf);
            encriptada = new String(base64Bytes);            
        } catch (Exception ex) {
            System.out.println("surgió un error al cifrar\nla contrasenia\n"+ex.getMessage());
            encriptada = null;
        }
        return encriptada;
    }
    
    public String desencriptarContrasenia(String encriptada){
       String desencriptada=null;        
        
        try {
            byte[] message = Base64.getDecoder().decode(encriptada.getBytes("utf-8"));            
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(contraseniaMaestra.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            desencriptada = new String(plainText, "UTF-8");

        } catch (Exception ex) {
            System.out.println("surgió un error al descifrar\nla contrasenia ->"+ex.getMessage());
            desencriptada = null;
        }
        return desencriptada;        
    }//SI FUNCIONA!!!! XD jajajaja    
    
    public String generarContraseniaAleatoria(){
        Random caracterAleatorio = new Random();
        Random tamanioAleatorio = new Random();
        String contrasenia = "";
        
        int tamanio = 8 + tamanioAleatorio.nextInt(13);//recuerda que comieza desde 0...
        
        for (int caracterActual = 0; caracterActual < tamanio; caracterActual++) {            
            contrasenia+= Character.toString(33+caracterAleatorio.nextInt(94));            
        }
        
        return contrasenia;
    }//hay que hacer que se generen lo más únicas posibles xD   
    
    /**
     *Empleado para el listado de usuarios del cual el Gerente puede
     * escoger el que requiere de modificacion o cuenta nueva; entonces con este método 
     * se obtiene l aubicación del usario en el listado que se muestra del cual es posible
     * obtner por medio del código que se encuentra en el valor del 
     * btn en el cual tb se halla el nombre de un solo usuario del listado existente
     * o del usuario "buscado" [esto último al ingresar el cod o nombre de un user en cuestión
     * en el input para búsqueda] el cajero o cliente en cuestión...
     * @param tipoBusqueda     
     * @param listadoUsuarios
     * @return
     */
    public List<Integer> buscarUbicacionUsuarioBuscado(String tipoBusqueda, String datoBusqueda, Usuario[] listadoUsuarios){
        List<Integer> listaUbicacionesCoincidentes = new LinkedList<>();
        
        try{
            for (int usuarioActual = 0; usuarioActual < listadoUsuarios.length; usuarioActual++) {
                if(tipoBusqueda.equalsIgnoreCase("codigo")){
                    if(String.valueOf(listadoUsuarios[usuarioActual].getCodigo()).startsWith(datoBusqueda)){
                        listaUbicacionesCoincidentes.add(usuarioActual);
                    }                
                }else{
                    if(listadoUsuarios[usuarioActual].getNombre().contains(datoBusqueda)){
                        listaUbicacionesCoincidentes.add(usuarioActual);
                    }
                }                
            }
            return listaUbicacionesCoincidentes;
        }catch(NumberFormatException e){//porque no recuerdo la otra además de numberFormlatException, por el hecho de tratar de covertir a número un dato alfanumérico... si te da excepción xD entonces copia el tipo y ya xD
            System.out.println("Error al obtener la ubicación del usuario buscado del listado"+ e.getMessage());
        }        
        return new LinkedList<>();      
    }      
}
