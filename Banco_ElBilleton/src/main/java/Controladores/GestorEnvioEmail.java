/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Entidades.Usuarios.Cliente;
import Modelo.Entidades.Usuarios.Trabajador;
import Modelo.Entidades.Usuarios.Usuario;
import Modelo.Manejadores.DB.Buscador;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phily
 */
@WebServlet("/gestorEnvioEmail")
public class GestorEnvioEmail extends HttpServlet{
    private Buscador buscador = new Buscador();    
    private Usuario usuario;
    
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response) {//0-> asunto [tipoCorreo],  1-> codigoUsuario, -> tipoUsuario [cliente/ trabajador]
        try {
            String[] datosSubmit; 
            
            if(request.getAttribute("datosEnvio")==null){
                datosSubmit = request.getParameter("envio").split("_");
            }else{
                String datos = (String)request.getAttribute("datosEnvio");
                datosSubmit = datos.split("_");//hay que probar si funciona, porque hasta donde yo recuerdo, con esto debe hacerse una conversion y no usar un INteger o ne este caso String.valueOf...
            }            
            
            if(datosSubmit[2].equals("Cliente")){
                usuario =(Cliente) buscador.buscarUsuario(datosSubmit[2], "codigo", datosSubmit[1]);
            }else{
                usuario = (Trabajador) buscador.buscarUsuario(datosSubmit[2], "codigo", datosSubmit[1]);
            }            
            
            if(!usuario.getCorreo().equals("???")){//puesto que después no será posibe ingresar un dato que no posea el formato que corresponde a los correos xD, y recuerda que basta con eso, es decir no es necesario que se haya ingresado uno real, solo uno válido xD                
                request.setAttribute("mostrarMsjeEnvio", (enviarConGMail(usuario.getCorreo(), datosSubmit[0], (String)request.getSession().getAttribute("cuerpo"))));//se envía msje de éxito :) o fracaso :( xD                              
                request.getSession().removeAttribute("cuerpo");
            
                if(!request.getSession().getAttribute("redireccionPorEnvioMail").equals("???")){                
                    request.getRequestDispatcher((String) request.getSession().getAttribute("redireccionPorEnvioMail")).forward(request, response);//para no redireccionar a la otra pag solo par mostra eso xD, sino se puede, deplano que se tendrá que hacer así xD        
                }//no da problema el hecho de no removerlos puesto que siempre que se requieran, poseerán el valor correcto xD                        
            }
            
        } catch (ServletException | IOException e) {
            System.out.println("Error al intentar ENVIAR el email -> "+e.getMessage());
        }        
   }
   
   private boolean enviarConGMail(String destinatario, String asunto, String cuerpo) {    
    final String remitente = "leondulc3@gmail.com";

    Properties props = System.getProperties();
    props.put("mail.smtp.host", "smtp.gmail.com");//El servidor SMTP de Google
    props.put("mail.smtp.user", remitente);
    props.put("mail.smtp.clave", buscador.buscarContraseniaRemitente());    //La clave de la cuenta
    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google [TLS]
    
    Session session = Session.getInstance(props,  new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(remitente, buscador.buscarContraseniaRemitente());
                    }
                });
    session.setDebug(false);
    MimeMessage message = new MimeMessage(session);
    MimeBodyPart mimebodypart = new MimeBodyPart();//2

    try {
        //los datos relevantes del msje
        message.setFrom(new InternetAddress(remitente));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));//Se podrían añadir varios de la misma manera
        message.setSubject(asunto);
        //message.setText(cuerpo);//puesto que se establecerá por medio del objeto mimeBodyPart
        //se crea el contenido del mensaje [2]
        mimebodypart.setText(cuerpo);
        mimebodypart.setContent(cuerpo, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimebodypart);        
        message.setContent(multipart);
        message.setSentDate(new Date());        
        
        try (Transport transporte = session.getTransport("smtp")) {
            transporte.connect("smtp.gmail.com", remitente, buscador.buscarContraseniaRemitente());
            transporte.sendMessage(message, message.getAllRecipients());
        }
        return true;
    }
    catch (MessagingException | NullPointerException e) {//el null lo coloqué por el hecho de no establecer el cuerpo del email al no realizar bisn la búsqueda [esto para el correo por creación de cuenta...]
        System.out.println("Error al enviar el email -> "+e.getMessage());
    }
    return false;
   }        
}
