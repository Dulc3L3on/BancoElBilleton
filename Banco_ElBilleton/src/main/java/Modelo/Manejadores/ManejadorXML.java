/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores;

import Modelo.Herramientas.ControladorIndices;
import Modelo.ListaEnlazada;
import Modelo.RegistrosAntiguos.ClasificadorEntidades;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author phily
 */
public class ManejadorXML {
    ClasificadorEntidades clasificador;
    
    public ManejadorXML(){
      clasificador = new ClasificadorEntidades();  
    }    
    
    public void leerXML(String nombreArchivo){//con todo y raíz... obvi xD        
        try{
             DocumentBuilderFactory fabricaDocumento = DocumentBuilderFactory.newInstance();
             DocumentBuilder constructorDocumento = fabricaDocumento.newDocumentBuilder();
             Document documento = constructorDocumento.parse(new File(nombreArchivo));
             
             documento.getDocumentElement().normalize();             
             
             clasificador.clasificar(documento);
             
        }catch(ParserConfigurationException exc){
            JOptionPane.showMessageDialog(null, "No puedo transformarse el archivo", "error de conversion", JOptionPane.ERROR_MESSAGE);
        }catch(IOException | SAXException exc){
            JOptionPane.showMessageDialog(null, "Ha surgido un error al\nintentar leer el XML", "error de lectura", JOptionPane.ERROR_MESSAGE);            
        }              
    }
    
    public ListaEnlazada<String[]> darListaErrores(){
        return clasificador.darListaErrores();
    }   
}
