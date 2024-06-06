package tech.olatunbosun.wastemanagement.emailservice;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import tech.olatunbosun.wastemanagement.emailservice.dto.EmailDetailDTO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    @Value("${application.mail.sent.from}")
    private String fromUsr;
    public void sendEmail(String to, String subject, String body) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setFrom(fromUsr);
        helper.setSubject(subject);
        helper.setText(body, true); // Set HTML content to true
        emailSender.send(message);
    }

    @RabbitListener(queues = "email_queue")
    public void processEmailMessage(EmailDetailDTO emailDetailDTO) throws MessagingException {
        String to = emailDetailDTO.getTo();
        String subject = emailDetailDTO.getSubject();
        String body = generateEmailBody(emailDetailDTO); // Generate body from DTO

        sendEmail(to, subject, body);
    }


    public String generateEmailBody(EmailDetailDTO emailDetailDTO) {
        String templateName = emailDetailDTO.getTemplateName();  // or some other template name
        String template = loadEmailTemplate(templateName);

        // Replace placeholders with actual values from EmailDetailDTO
        String body = template;

        // If there are additional dynamic values
        if (emailDetailDTO.getDynamicValue() != null) {
            for (Map.Entry<String, Object> entry : emailDetailDTO.getDynamicValue().entrySet()) {
                body = body.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
            }
        }

        return body;
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
