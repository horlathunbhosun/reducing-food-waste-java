package tech.olatunbosun.wastemanagement.emailservice;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailService {

    private  JavaMailSender emailSender;
    private StringRedisTemplate redisTemplate;

    private static final int MAX_RETRIES = 3; // Maximum number of retry attempts


    @Scheduled(fixedRate = 1000) // Polls the queue every second
    public void processEmails() {
        String emailMessage = redisTemplate.opsForList().rightPop("emailQueue");
        if (emailMessage != null) {
            // Process and send the email
            sendEmail(emailMessage);
        }
    }

    @Scheduled(fixedRate = 60000) // Check for failed emails every minute
    public void retryFailedEmails() {
        List<String> failedEmails = redisTemplate.opsForList().range("failedEmails", 0, -1);
        if (failedEmails != null) {
            for (String email : failedEmails) {
                if (shouldRetry(email)) {
                    // Retry the email delivery
                    sendEmail(email);
                } else {
                    // Log permanent failure or take other action
                    System.out.println("Email delivery permanently failed: " + email);
                }
            }
        }
    }

    private boolean shouldRetry(String email) {
        int retries = Integer.parseInt(email.split(":")[1]); // Extract retry count from email
        return retries < MAX_RETRIES;
    }

//    public  void sendEmail(String to, String subject, String body) throws MessagingException {
//        MimeMessage message = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(body, true); // Set HTML content to true
//        emailSender.send(message);
//    }

    private void sendEmail(String emailMessage) {
        String[] parts = emailMessage.split(",");
        String to = parts[0];
        String subject = parts[1];
        String body = parts[2];

        try {
            if (StringUtils.hasText(to) && StringUtils.hasText(subject) && StringUtils.hasText(body)) {
                MimeMessage mimeMessage = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(body, true);

                emailSender.send(mimeMessage);
                System.out.println("Email sent to: " + to);
            } else {
                throw new IllegalArgumentException("Invalid email message: " + emailMessage);
            }
        } catch (MessagingException e) {
            // Log the failure and requeue the email
            redisTemplate.opsForList().rightPush("failedEmails", emailMessage);
            handleFailedEmail(emailMessage);
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }


    private void handleFailedEmail(String email) {
        String[] parts = email.split(":");
        String emailMessage = parts[0];
        int retries = Integer.parseInt(parts[1]);
        retries++;

        // Requeue the email with updated retry count
        redisTemplate.opsForList().rightPush("emailQueue", emailMessage + ":" + retries);
        // Optionally, log the failure or take other actions
        System.out.println("Email delivery failed, retrying... (" + retries + " retries)");
    }





    public String loadEmailTemplate(String templateName) {
        ClassPathResource resource = new ClassPathResource("templates/emails/" + templateName + ".html");
        try {
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error loading email template " + templateName, e);
        }
    }
}
