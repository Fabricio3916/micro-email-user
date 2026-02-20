package dev.fabricio.email.service;

import dev.fabricio.email.entity.Email;
import dev.fabricio.email.entity.EmailStatus;
import dev.fabricio.email.repository.emailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    private final emailRepository emailRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailService(emailRepository emailRepository, JavaMailSender mailSender) {
        this.emailRepository = emailRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    public void sendEmail(Email email) {
        email.setEmailFrom(emailFrom);
        email.setSentDate(LocalDateTime.now());
        email.setEmailStatus(EmailStatus.PENDING);

        try {
            var message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(email.getEmailTo());
            message.setSubject(email.getEmailSubject());
            message.setText(email.getBody());

            mailSender.send(message);
            email.setEmailStatus(EmailStatus.SENT);
        } catch (Exception e) {
            email.setEmailStatus(EmailStatus.FAILED);
            System.err.println("Erro ao enviar email: " + e.getMessage());
        }

        emailRepository.save(email);
    }
}
