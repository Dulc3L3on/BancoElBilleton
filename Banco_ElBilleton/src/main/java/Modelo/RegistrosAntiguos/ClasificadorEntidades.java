/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.RegistrosAntiguos;

import Modelo.ListaEnlazada;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author phily
 */
public class ClasificadorEntidades {    
    private CreadorEntidadesXML creadorEntidades = new CreadorEntidadesXML();
    
   public void clasificar(Document documento){//el XML está formado por raiz [hospital], categorias [admin, labo, pac...] y atribitos                          
       leerGerente(documento.getElementsByTagName("GERENTE"));//con esto se obtiene a todos los nodos que correspondan a la categoría "admin" en este caso...                                 
       leerCajero(documento.getElementsByTagName("CAJERO"));//de esta manera no importará en qué orden puedan venir, puesto que YO soy quien los llama según el orden que requiero...                               
       leerCliente(documento.getElementsByTagName("CLIENTE"));
       leerTransaccion(documento.getElementsByTagName("TRANSACCION"));           
    }
   
   private void leerGerente(NodeList gerentes){
        if(gerentes!=null){
            for(int gerenteAcual=0; gerenteAcual < gerentes.getLength(); gerenteAcual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
                Node gerente = gerentes.item(gerenteAcual);//que aquí lo nombraron como item xD
            
                if(gerente.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) gerente;                
                
                    creadorEntidades.crearGerente(elemento.getElementsByTagName("CODIGO").item(0).getTextContent(), elemento.getElementsByTagName("NOMBRE").item(0).getTextContent(), 
                            elemento.getElementsByTagName("DPI").item(0).getTextContent(), elemento.getElementsByTagName("DIRECCION").item(0).getTextContent(), elemento.getElementsByTagName("SEXO").item(0).getTextContent(),
                            elemento.getElementsByTagName("PASSWORD").item(0).getTextContent(), elemento.getElementsByTagName("TURNO").item(0).getTextContent());//no olvides que todo lo devuelve como un string...
                }                        
            }                
        }        
   }
   
   private void leerCajero(NodeList cajeros){
       creadorEntidades.limpiarListadoCodigos();//puesto que pueden coincidir con otros de otras tablas pero JAMPAS NUNCA con los de la misma xD
       
        if(cajeros!=null){
            for(int cajeroAcual=0; cajeroAcual < cajeros.getLength(); cajeroAcual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
                Node cajero = cajeros.item(cajeroAcual);//que aquí lo nombraron como item xD
            
                if(cajero.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) cajero;                
                
                    creadorEntidades.crearCajero(elemento.getElementsByTagName("CODIGO").item(0).getTextContent(), elemento.getElementsByTagName("NOMBRE").item(0).getTextContent(), //vamos a probar con la dirección xD
                            elemento.getElementsByTagName("DPI").item(0).getTextContent(), elemento.getElementsByTagName("DIRECCION").item(0).getTextContent(), elemento.getElementsByTagName("SEXO").item(0).getTextContent(),
                            elemento.getElementsByTagName("PASSWORD").item(0).getTextContent(), elemento.getElementsByTagName("TURNO").item(0).getTextContent());//no olvides que todo lo devuelve como un string...
                }                        
            }                
       }      
   }
   
   private void leerCliente(NodeList clientes){    
        creadorEntidades.limpiarListadoCodigos();//puesto que pueden coincidir con otros de otras tablas pero JAMPAS NUNCA con los de la misma xD
        creadorEntidades.limpiarListadoNumerosCuenta();
        boolean clienteCreadoCorrectamente =true;                
            
        if(clientes!=null){
            for (int clienteActual = 0; clienteActual < clientes.getLength(); clienteActual++) {
                Node cliente = clientes.item(clienteActual);
                   
                if(cliente.getNodeType() == Node.ELEMENT_NODE){
                    Element elemento = (Element) cliente;
                    
                    clienteCreadoCorrectamente = creadorEntidades.crearCliente(elemento.getElementsByTagName("CODIGO").item(0).getTextContent(), elemento.getElementsByTagName("NOMBRE").item(0).getTextContent(), 
                       elemento.getElementsByTagName("DPI").item(0).getTextContent(), elemento.getElementsByTagName("DIRECCION").item(0).getTextContent(), elemento.getElementsByTagName("SEXO").item(0).getTextContent(),
                       elemento.getElementsByTagName("PASSWORD").item(0).getTextContent(), elemento.getElementsByTagName("BIRTH").item(0).getTextContent(), elemento.getElementsByTagName("DPI-PDF").item(0).getTextContent());
                   
                    NodeList listaCuentas = elemento.getElementsByTagName("CUENTA");
                       
                   if(clienteCreadoCorrectamente){
                        leerCuentas(listaCuentas, elemento.getElementsByTagName("CODIGO").item(0).getTextContent(), elemento.getElementsByTagName("NOMBRE").item(0).getTextContent());
                    }                                                                    
                }                    
            }                                   
        }                                                                                    
   }//debes revisar... por el hecho de obtener la lisa de las cuentas como aquellas de > jerarquía como cliente, cajero, gerente...
   
   private void leerCuentas(NodeList cuentas, String codigoDueno, String nombre){                       
        if(cuentas!=null){            
            for (int cuentaActual = 0; cuentaActual < cuentas.getLength(); cuentaActual++) {
                Node cuenta = cuentas.item(cuentaActual);
                
                if(cuenta.getNodeType() == Node.ELEMENT_NODE){
                    Element elementoCuenta =  (Element) cuenta;
                    
                    creadorEntidades.crearCuenta(elementoCuenta.getElementsByTagName("CODIGO").item(0).getTextContent(), codigoDueno, elementoCuenta.getElementsByTagName("CREDITO").item(0).getTextContent(),
                        elementoCuenta.getElementsByTagName("CREADA").item(0).getTextContent(), nombre);//no olvides que todo lo devuelve como un string...                    
                }                                                
            }                                               
        }                                                 
   }
   
   private void leerTransaccion(NodeList transacciones){
       creadorEntidades.limpiarListadoCodigos();
       
        if(transacciones!=null){
            for(int transaccionAcual=0; transaccionAcual < transacciones.getLength(); transaccionAcual++ ){//es decir el numero de ocurrencia del mismo tipo de categoría...
                Node transaccion = transacciones.item(transaccionAcual);//que aquí lo nombraron como item xD
            
                if(transaccion.getNodeType()== Node.ELEMENT_NODE){//aquí reviso si es un elemnto que por lo tanto contiene atributos [es decir si p.ej es un admin con su DPI, nombre, contra y codigo...
                    Element elemento = (Element) transaccion;                
                
                    creadorEntidades.crearTransaccion(elemento.getElementsByTagName("CODIGO").item(0).getTextContent(), elemento.getElementsByTagName("CUENTA").item(0).getTextContent(), 
                            elemento.getElementsByTagName("FECHA").item(0).getTextContent(), elemento.getElementsByTagName("HORA").item(0).getTextContent(), elemento.getElementsByTagName("TIPO").item(0).getTextContent(),
                            elemento.getElementsByTagName("MONTO").item(0).getTextContent(), elemento.getElementsByTagName("CAJERO-ID").item(0).getTextContent());//no olvides que todo lo devuelve como un string...
                }                        
            }                
       }      
   }
   
   public ListaEnlazada<String[]> darListaErrores(){
       return creadorEntidades.darListadoErrores();
   }
 
   
   /*NOTA:
        -> Cuando tengas grupos de etiquetas dentro de un elemento del grupo exterior, debes tratarlos
            tal y como trataste al agrupador general a cada uno de dichos subgrupos... en este caso 
            "banco", es decir ignorar el nombre de su etiqueta y solo tomar el listado de nodos 
            NodeList que agrupa [en este caso Cliente, Cajero...] y los elementos que contiene [supongamos
            que el listado de nodo "agarrado" fue Cliente, entonces sus elementos [no los llamo atributos
            porque eso algo diferente en este contexto xD] serían: nombre, DPI, DPI-PDF, direccion... y 
            también, por ser caso especial, la subagrupación CUENTAS, que viene a ser el equivalente a banco...
            [lo que expliqué antes xD]
   */      
}
