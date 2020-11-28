/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Usuarios;

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
    
    public Usuario(int elCodigo, String elNombre, String elDPI, String laDireccion,
    String elGenero, String elPassword){
    
       codigo =elCodigo;
       nombre = elNombre;
       DPI = elDPI;
       direccion = laDireccion;
       sexo = elGenero;
       password = elPassword;
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
}
