package uzparser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    private static final String USERNAME_TO = "ks.13.bogdan@gmail.com";
    private static final String USERNAME_FROM = "ks.13.bogdan@gmail.com";
    private static final String PASSWORD = "51020c2332Ks54107";

    private static final String TEXT = "Hello!";
    private static final String SUBJECT = "Booking uz.gov.ua";

    public void send(String respons) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME_FROM, PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME_FROM));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(USERNAME_TO));
            message.setSubject(SUBJECT);
            message.setText(TEXT + " " + respons);
            System.out.println(respons);
            Transport.send(message);
            System.out.println("Email sent");
        } catch (MessagingException e) {
            System.out.println("Email was not sent");
        }
    }
}