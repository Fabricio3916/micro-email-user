package dev.fabricio.email.dto;

import java.util.UUID;

public record EmailDto(
            UUID userId,
            String emailTo,
            String emailSubject,
            String body
) {}
