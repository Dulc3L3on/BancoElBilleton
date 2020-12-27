/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phily
 */
@WebServlet("/gstorEnvioEmail")
public class GestorEnvioEmail extends HttpServlet{
    
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response) {
       
       
       
   }
   
//   private static void enviarConGMail(String destinatario, String asunto, String cuerpo) {
//    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
//    String remitente = "ingeniousl3on@gmail.com";//Para la dirección nomcuenta@gmail.com
//
//    Properties props = System.getProperties();
//    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
//    props.put("mail.smtp.user", remitente);
//    props.put("mail.smtp.clave", "miClaveDeGMail");    //La clave de la cuenta
//    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
//    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
//    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
//
//    Session session = Session.getDefaultInstance(props);
//    MimeMessage message = new MimeMessage(session);
//
//    try {
//        message.setFrom(new InternetAddress(remitente));
//        message.addRecipient(Message.RecipientType.TO, destinatario);   //Se podrían añadir varios de la misma manera
//        message.setSubject(asunto);
//        message.setText(cuerpo);
//        Transport transport = session.getTransport("smtp");
//        transport.connect("smtp.gmail.com", remitente, clave);
//        transport.sendMessage(message, message.getAllRecipients());
//        transport.close();
//    }
//    catch (MessagingException me) {
//        me.printStackTrace();   //Si se produce un error
//    }
//}
    
    
}
