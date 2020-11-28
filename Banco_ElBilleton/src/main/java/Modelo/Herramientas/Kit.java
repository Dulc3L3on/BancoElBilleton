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
import java.util.Date;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author phily
 */
public class Kit {
     final String contraseniaMaestra = "ElBilleton";
    
     public String darFechaActualString(){
        return java.time.LocalDate.now().toString();               
    }//:v xD
    
    public String darHoraActual(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);                
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
            System.out.println("surgió un error al descifrar\nla contrasenia\n"+ex.getMessage());
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
    
    public int buscarUbicacionUsuarioBuscado(String codigo, Usuario[] listadoUsuarios){
        
        try{
            for (int usuarioActual = 0; usuarioActual < listadoUsuarios.length; usuarioActual++) {
                if(listadoUsuarios[usuarioActual].getCodigo() == Integer.parseInt(codigo)){
                    return usuarioActual;
                }                           
            }
        }catch(NumberFormatException e){//porque no recuerdo la otra además de numberFormlatException, por el hecho de tratar de covertir a número un dato alfanumérico... si te da excepción xD entonces copia el tipo y ya xD
            System.out.println("Error al obtener la ubicación del usuario buscado del listado"+ e.getMessage());
        }        
        return -1;      
    }
    
}
