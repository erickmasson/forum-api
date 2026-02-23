package com.projeto.forum.dto;

import jakarta.validation.constraints.NotBlank;

public record ReplyUpdateDTO(
        @NotBlank(message = "A mensagem não pode ser vazia")
        String message
) {
}
