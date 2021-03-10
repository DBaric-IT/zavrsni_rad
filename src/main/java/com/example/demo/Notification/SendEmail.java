package com.example.demo.Notification;

import com.example.demo.Model.Notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendEmail {

    public static void  sendMail(Notification notification) throws Exception {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "danijelbaric7@gmail.com";
        String password = "Odrzavanje23";

        Session session = Session.getInstance(properties, new Authenticator() {
           @Override
           protected PasswordAuthentication getPasswordAuthentication(){
               return new PasswordAuthentication(myAccountEmail, password);
           }
        });

        Message message = prepareMessage(session, myAccountEmail, notification);

        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String myAccountEmail, Notification notification){
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(notification.getUser().getEmail()));
            message.setSubject(notification.getSubject());
            message.setText(notification.getMessage());
            return message;
        }
        catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
