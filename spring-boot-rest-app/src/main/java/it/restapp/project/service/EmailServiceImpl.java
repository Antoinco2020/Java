package it.restapp.project.service;

import it.crypt.project.utils.CryptUtils;
import it.restapp.project.entities.Review;
import it.restapp.project.utils.ResourcePropertiesUtil;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private CryptUtils cryptUtils;

    public void sendReviewEmail(Review review) throws Exception {
        try {
            LOGGER.info("Send email from new review inserted");
            ResourcePropertiesUtil propertiesUtil = ResourcePropertiesUtil.getInstance("myprop.properties");
            Properties mailProps = new Properties();
            mailProps.put("mail.smtp.host", propertiesUtil.getProperty("mailHost"));
            mailProps.put("mail.smtp.port", propertiesUtil.getProperty("mailPort"));
            mailProps.put("mail.smtp.ssl.enable", propertiesUtil.getProperty("enableSSL"));
            String username = cryptUtils.decrypt(propertiesUtil.getProperty("mailUser"));
            String password = cryptUtils.decrypt(propertiesUtil.getProperty("mailPassword"));

            Session session = Session.getInstance(mailProps, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(propertiesUtil.getProperty("mailReviewFrom")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(propertiesUtil.getProperty("mailReviewTo")));
            message.setSubject("Inserted new review for book: " + review.getBook().getIsbn());
            String htmlBody = String.format("<html><body><p>Dear Manager Library</p><p>A new review has been posted</p>" +
                    "<p>Book ISBN: %s</p>" +
                    "<p>Book Title: %s</p>" +
                    "<p>Review vote: %s</p>" +
                    "<p>Review description: %s</p><p>Best regards</p><p>API Library</p></body></html>",
                    review.getBook().getIsbn(),
                    review.getBook().getTitle(),
                    review.getVote(), review.getDescription());
            message.setContent(htmlBody, "text/html");
            Transport.send(message);
        }
        catch (Exception e){
            LOGGER.error("Error sendReviewEmail. Details: ", e);
            throw e;
        }
    }
}
