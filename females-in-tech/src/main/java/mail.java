import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class mail {

    public static void main(String[] args) {
        sendMail("pratikp@yopmail.com", "Kumar verma");
    }


    public static void sendMail(String to, String name) {

        // Sender's email credentials
        final String from = "pratikpanchal5980@gmail.com"; // sender's email address
        final String password = "pfjn ifyz voza aauz"; // sender's email password (use application-specific password for better security)

        // Set up the mail server properties
        Properties properies = new Properties();
        properies.put("mail.smtp.host", "smtp.gmail.com");
        properies.put("mail.smtp.port", "465");
        properies.put("mail.smtp.auth", "true");
        properies.put("mail.smtp.starttls.enable", "true");
        properies.put("mail.smtp.starttls.required", "true");
        properies.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properies.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Set up a session with authentication using the provided email credentials
        Session session = Session.getInstance(properies, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password); // Authenticate with sender email and password
            }
        });

        try {
            // Create a new MimeMessage (email message)
            Message message = new MimeMessage(session);

            // Set the "From" field of the email
            message.setFrom(new InternetAddress(from));

            // Set the "To" field of the email with the recipient's email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set the subject of the email
            message.setSubject("Interview Invitation and Credentials");

            // Craft the email body with a formal and professional message
            String body = "Dear " + name + ",\n\n"
                    + "Note: Please ensure that you are available at the scheduled time. If you need to reschedule or have any questions, feel free to contact us.";

            // Set the email body content
            message.setText(body);

            // Send the email using the Transport.send() method
            Transport.send(message);

            // Log successful email sending
            log.info("Email sent successfully to " + to);

        } catch (Exception e) {
            // Catch any exceptions during the process and print the stack trace for debugging
            e.printStackTrace();
        }
    }

}
