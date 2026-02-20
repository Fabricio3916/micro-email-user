package dev.fabricio.user.producer;

import dev.fabricio.user.config.RabbitMq;
import dev.fabricio.user.domain.User;
import dev.fabricio.user.dto.EmailDto;
import dev.fabricio.user.dto.UserDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;


    public UserProducer(RabbitTemplate rabbitTemplate, RabbitMq rabbitMq) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserCreatedEvent(User user) {

        EmailDto emailDto = new EmailDto(
                user.getUserId(),
                user.getEmail(),
                "Welcome to Java10x",
                "Hello " + user.getName() + " Welcome to Java10x!"
                );

        String routingKey = "email-queue";
        rabbitTemplate.convertAndSend( "", routingKey, emailDto);
    }

}
