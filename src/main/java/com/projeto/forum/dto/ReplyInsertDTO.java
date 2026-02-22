package com.projeto.forum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReplyInsertDTO(
        @NotBlank(message = "A mensagem não pode ser vazia")
        String message,

        @NotNull(message = "O ID do tópico é obrigatório")
        Long topicId,

        @NotNull(message = "O ID do autor é obrigatório")
        Long authorId
) {
}
