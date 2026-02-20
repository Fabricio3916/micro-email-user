package dev.fabricio.email.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_email")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID emailId;
    private UUID userId;
    private String emailTo;
    private String emailFrom;
    private String emailSubject;
    private String body;
    private LocalDateTime sentDate;
    @Enumerated(EnumType.STRING)
    private EmailStatus emailStatus;

}
