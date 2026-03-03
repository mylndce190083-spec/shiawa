/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
/**
 *
 * @author Lenovo
 */
public class Email {
    public static void sendVerificationEmail(String toEmail, String token) {

        final String fromEmail = "hienpdt.ce190957@gmail.com";
        final String password = "uudpwqxrdjnudksp";

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

        try {
            String link = "http://localhost:8080/Shiawa_BookStore/verify?token=" + token;

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail));
            message.setSubject("Verify your account");

            message.setText("Click this link to verify: " + link);

            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
//        Email.sendVerificationEmail("phamduongthehien.9a2@gmail.com", "123");
//        System.out.println("done");
    }
}
