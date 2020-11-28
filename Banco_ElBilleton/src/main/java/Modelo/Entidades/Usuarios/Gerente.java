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
public class Gerente extends Trabajador{
    
    public Gerente(int elCodigo, String elNombre, String elDPI, String laDireccion, String elGenero, String elPassword, String elTurno) {
        super(elCodigo, elNombre, elDPI, laDireccion, elGenero, elPassword, elTurno);
    }
    
}
/*<%=(pagina.startsWith("Reportes")?"marcadoGerente":"")%> ya no xD porque se mira con muuuuchos colores... xD
  <%=(pagina.startsWith("Modificacion")?"marcadoGerente":"")%>
  <%=(pagina.startsWith("Creacion")?"marcadoGerente":"")%>
*/