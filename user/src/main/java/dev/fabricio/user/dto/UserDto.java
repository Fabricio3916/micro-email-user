package dev.fabricio.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserDto(
        @NotBlank String name,
        @NotBlank @Email String email
) {}
