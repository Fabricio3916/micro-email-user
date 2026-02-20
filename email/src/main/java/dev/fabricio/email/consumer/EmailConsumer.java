package dev.fabricio.email.consumer;

import dev.fabricio.email.dto.EmailDto;
import dev.fabricio.email.dto.UserDto;
import dev.fabricio.email.entity.Email;
import dev.fabricio.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email-queue")
    public void listenEmailQueue(@Payload EmailDto dto) {
        var email = new Email();
        BeanUtils.copyProperties(dto, email);
        emailService.sendEmail(email);
        System.out.println("Email enviado");

    }

}
