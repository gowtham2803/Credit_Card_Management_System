package com.ccms.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtil {

    private static final String FROM_EMAIL = "gowthamr280321@gmail.com";
    private static final String APP_PASSWORD = "rdej xjdx gftz xzci";

    public static void sendEmail(String toEmail, String subject, String messageText) {

        System.out.println("Email function started...");
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");

        try {

            System.out.println("Creating session...");

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                        }
                    });

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageText);

            System.out.println("Sending email now...");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (Exception e) {
            System.out.println("EMAIL ERROR OCCURRED");
            e.printStackTrace();
        }
    }
}