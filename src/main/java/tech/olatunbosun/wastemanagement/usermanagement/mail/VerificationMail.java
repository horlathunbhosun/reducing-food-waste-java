package tech.olatunbosun.wastemanagement.usermanagement.mail;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.olatunbosun.wastemanagement.emailservice.EmailService;

import java.io.IOException;
import java.util.Map;
@Service
//@AllArgsConstructor
public class VerificationMail {

    private StringRedisTemplate redisTemplate;

    private EmailService emailService;

    @Autowired
    public VerificationMail(EmailService emailService, StringRedisTemplate redisTemplate) {
        this.emailService = emailService;
        this.redisTemplate = redisTemplate;
    }


     //This annotation is used to indicate that a method should be run asynchronously. This means that the method will return immediately upon invocation and the actual execution of the method will occur in a task that has been submitted to a Spring TaskExecutor
    public void sendVerificationEmail(String to, Map<String, Object> dynamicValue) throws MessagingException {
        System.out.println("Sending verification email to: " + to);
        String subject = "Verify your email address";
        String body;
        body = emailService.loadEmailTemplate("verification");
        String verificationToken = (String) dynamicValue.get("verificationToken");
        String fullName = (String) dynamicValue.get("fullName");
        body = body.replace("{{fullName}}", fullName);
        body = body.replace("{{verificationToken}}", verificationToken);

//        emailService.sendEmail(to, subject, body);
        // Queue the email message in Redis
        String emailMessage = to + "," + subject + "," + body;
        System.out.println("Email message: " + emailMessage);
        redisTemplate.opsForList().leftPush("emailQueue", emailMessage);
        System.out.println("Email queued for delivery"+redisTemplate.opsForList().size("emailQueue"));
    }
}
