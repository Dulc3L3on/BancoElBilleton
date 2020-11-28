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
public class Cliente extends Usuario{
    String birth;
    String pathDPI;
 
    public Cliente(int elCodigo, String elNombre, String elDPI, String laDireccion,
            String elGenero, String elPassword, String elBirth, String elPathDPI) {
        super(elCodigo, elNombre, elDPI, laDireccion, elGenero, elPassword);
        
        birth = elBirth;
        pathDPI = elPathDPI;
    }
 
    public String getBirth(){
        return birth;
    }
    
    public String getPathDPI(){
        return pathDPI;
    }
    
}
