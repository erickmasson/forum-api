package com.projeto.forum.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInsertDTO(
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        @Size(min = 6)
        String password
) {
}
