/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Usuarios;

import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Cliente extends Usuario implements Serializable{
    private String birth;
    private String pathDPI;
    private double saldoTotalCuentas;
    private double totalDebitos;
    private double totalCreditos;
    
    public Cliente(int elCodigo, String elNombre, String elDPI, String laDireccion,
            String elGenero, String elPassword, String fechaIncorporacion, String elCorreo, String elBirth, String elPathDPI) {
        super(elCodigo, elNombre, elDPI, laDireccion, elGenero, elPassword, fechaIncorporacion, elCorreo);
        
        birth = elBirth;
        pathDPI = elPathDPI;
    }
 
    public String getBirth(){
        return birth;
    }
    
    public String getPathDPI(){
        return pathDPI;
    }
    
    public void establecerSaldoTodasCuentas(double elTotal){
        saldoTotalCuentas = elTotal;    
    }
    
    public double getSaldoTodasLasCuentas(){
        return saldoTotalCuentas;
    }
    
    public void establecerTotalDebitos(double elTotalDebitos){
        totalDebitos = elTotalDebitos;
    }
    
    public double getTotalDebitos(){
        return totalDebitos;
    }
    
    public void establecerTotalCreditos(double elTotalCreditos){
        totalCreditos = elTotalCreditos;
    }
    
    public double getTotalCreditos(){
        return totalCreditos;
    }
    
}
