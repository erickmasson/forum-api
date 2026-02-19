package com.projeto.forum.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TopicUpdateDTO(
        @NotBlank(message = "O título não pode ser vazio")
        @Size(min = 5, max = 100)
        String title,

        @NotBlank(message = "A mensagem não pode ser vazia")
        String message
) {

}
