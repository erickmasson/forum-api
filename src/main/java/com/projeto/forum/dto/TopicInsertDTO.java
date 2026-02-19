package com.projeto.forum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TopicInsertDTO(
        @NotBlank(message = "O título não pode ser vazio")
        @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
        String title,

        @NotBlank(message = "A mensagem não pode ser vazia")
        String message,

        @NotNull(message = "O ID do autor é obrigatório")
        Long authorId
) {
}
