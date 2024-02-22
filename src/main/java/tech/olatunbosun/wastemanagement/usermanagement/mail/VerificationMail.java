package tech.olatunbosun.wastemanagement.usermanagement.mail;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.olatunbosun.wastemanagement.emailservice.EmailService;

import java.io.IOException;
import java.util.Map;
@Service
public class VerificationMail {


    private EmailService emailService;

    @Autowired
    public VerificationMail(EmailService emailService) {
        this.emailService = emailService;
    }


    @Async
    public void sendVerificationEmail(String to, Map<String, Object> dynamicValue) throws MessagingException {
        String subject = "Verify your email address";
        String body;
        body = emailService.loadEmailTemplate("verification");
        String verificationToken = (String) dynamicValue.get("verificationToken");
        String fullName = (String) dynamicValue.get("fullName");
        body = body.replace("{{fullName}}", fullName);
        body = body.replace("{{verificationToken}}", verificationToken);

        emailService.sendEmail(to, subject, body);
    }
}
