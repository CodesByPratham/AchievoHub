package utilities;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

import jakarta.servlet.ServletContext;

/**
 * @author PRATHAM
 */
public class EmailUtil {

    // Sends verification email after user registration
    public static void sendVerificationEmail(String recipientEmail, String verificationLink) {
        sendEmail(recipientEmail, "AchievoHub - Email Verification",
                "Click the link below to verify your email:\n" + verificationLink);
    }

    // Sends reset password email
    public static void sendResetPasswordEmail(String recipientEmail, String resetLink) {
        sendEmail(recipientEmail, "AchievoHub - Reset Password",
                "Click the link below to reset your password:\n" + resetLink);
    }

    // Generic method to send emails
    private static void sendEmail(String recipientEmail, String subject, String content) {
        ServletContext context = AppConfigListener.getServletContext();
        String host = context.getInitParameter("mailHost");
        String port = context.getInitParameter("mailPort");
        final String user = context.getInitParameter("mailUser");
        final String pass = context.getInitParameter("mailPassword");

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println(subject + " email sent successfully!");
        
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}