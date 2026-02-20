package dev.fabricio.user.service;

import dev.fabricio.user.domain.User;
import dev.fabricio.user.dto.UserDto;
import dev.fabricio.user.producer.UserProducer;
import dev.fabricio.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional
    public User saveAndPublish(User user) {
        userRepository.save(user);
        userProducer.sendUserCreatedEvent(user);
        return user;
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
