package dev.fabricio.email.repository;

import dev.fabricio.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface emailRepository extends JpaRepository<Email, UUID> {
}
