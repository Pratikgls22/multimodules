package com.job.females.in.tech.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {


    /**
     * Sends an email with the given subject and body to the specified recipient.
     *
     * @param toEmail the recipient's email address
     * @param body    the body of the email
     */
    public void sendMail(String toEmail, String body) {
        // Sender's email credentials
        final String senderEmail = "pratikpanchal5980@gmail.com";
        final String password = "pfjn ifyz voza aauz";

        // Set up the mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");

        // Set up a session with authentication using the provided email credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Authenticate with sender email and password
                return new PasswordAuthentication(senderEmail, password);
            }
        });

        try {
            // Create a new MimeMessage (email message)
            Message message = new MimeMessage(session);

            // Set the "senderEmail" field of the email
            message.setFrom(new InternetAddress(senderEmail));

            // Set the "To" field of the email with the recipient's email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // Set the subject of the email
            message.setSubject("Password Reset Request");

            // Set the email body content
            // message.setText(body);
            message.setContent(body, "text/html");
            // setContent() for HTML content or to add attachments.

            // Send the email using the Transport.send() method
            Transport.send(message);

            // Log successful email sending
            log.info("Email sent successfully to : {}", toEmail);

        } catch (Exception e) {
            // Catch any exceptions during the process and print the stack trace for debugging
            e.getCause();
        }

    }


}
