/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Entidades.Usuarios;

import Modelo.Usuario;

/**
 *
 * @author phily
 */
public class Trabajador extends Usuario{
    
    
    /*NOTA: esta clase me será útil para reducir el # de JSP's que tengan 
      un mismo fin para dichos trabajadores, de tal forma que pueda emplear una sola página para
    la realización de la acción [solo app para la creación :v porrque en la modificación se hace algo diferente
    a menos que lo hagas en los servlets... pero para cliente deberías usar, user o cliente mismo y para 
    gerente y cajero trabajadador...
    
    NOP! XD, utiliza usuario para no tener que colocar if's de más y como cuando
        1. debes mostrar, verificas que el dato que vayas a mostrar exista [es decir != null], entonces no hay problema porque el papá puede hacer todo lo que sus hijos hacen [pero no al revés...]
        2. no se mandó un dato [porque no estaba en el form ya que no le correspondía al user [puesto que todos son required...]] simplemente no lo agrega al arr [recuerda eso lo compro cuando enviavas el path del DPI... 
            entonces lo que debes hacer es mandar un valor por medio de una query string por medio del manejador de naveación... que variará dependiendo de la opción presionada... xD este valor te será útil para que sepas 
            como deberás tratar la info recibida... puesto que existen algunas excepciones... si está muy fello entonces eli esta clase y hazlo = que antes xD*/
}
