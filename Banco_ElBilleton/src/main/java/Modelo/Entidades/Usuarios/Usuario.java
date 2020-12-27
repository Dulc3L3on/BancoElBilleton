/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Usuarios;

import Modelo.Herramientas.Kit;

/**
 *
 * @author phily
 */
public class Usuario {
    int codigo;
    String nombre;
    String DPI;
    String direccion;
    String sexo;
    String password;
    String fechaIncorporacion;
    String correo = "???";
    private Kit herramienta = new Kit();
    
    public Usuario(int elCodigo, String elNombre, String elDPI, String laDireccion,
    String elGenero, String elPassword, String laFechaIncorporacion, String elCorreo){
    
       codigo =elCodigo;
       nombre = elNombre;
       DPI = elDPI;
       direccion = laDireccion;
       sexo = elGenero;
       password = herramienta.desencriptarContrasenia(elPassword);
       fechaIncorporacion = laFechaIncorporacion;
        System.out.println(elCorreo);
       correo = (elCorreo!=null && !elCorreo.isBlank() && !elCorreo.isEmpty())?elCorreo:correo;
    }
    
    //los coloco así por el hecho de que JR no reconoce los métodos a menos que tengan la palabra get y el nombre del atributo implicado...
    public int getCodigo(){
        return codigo;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getDPI(){
        return DPI;
    }
    
    public String getDireccion(){
        return direccion;
    }
    
    public String getSexo(){
        return sexo;
    }
    
    public String getPassword(){
        return password;
    }    
    
    public String getFechaIncorporacion(){
        return fechaIncorporacion;
    }
    
    public void establecerCorreo(String elCorreo){//creo que no será necesario puesto que se agregará al cnstrc y al modificar a una entidad se crea un obj nuevo y no se reemplazan los datos...
        correo = elCorreo;
    }
    
    public String getCorreo(){
        return correo;
    }
}
