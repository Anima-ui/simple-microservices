package com.microservice.notificationservice.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${SENDER_EMAIL}")
    private String senderEmail;

    @Value("${SENDER_NAME}")
    private String senderName;

    @KafkaListener(topics = "order-service", groupId = "notification-group")
    public void listen(String customerEmail) {
        try {
            sendEmail(customerEmail);
            log.info("Email sent");
        }catch (MessagingException | UnsupportedEncodingException | ListenerExecutionFailedException e) {
            log.error("Failed to send email to {}: {}", customerEmail, e.getMessage());
            throw new RuntimeException(e);
        }catch (RuntimeException e) {
            log.error("Something went wrong: {}", e.getMessage());
        }
    }

    public void sendEmail(String to) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("Order placement");
        mimeMessageHelper.setFrom(senderEmail, senderName);

        String htmlContent = """
            <html>
                <body style="font-family: Arial, sans-serif; text-align: center;">
                    <h2 style="color: #2e6c80;">Your order was successfully placed</h2>
                    <p>Thanks for using our service</p>
                </body>
            </html>
            """;

        mimeMessageHelper.setText(htmlContent, true);
        mailSender.send(mimeMessage);
    }
}
