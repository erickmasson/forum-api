package com.projeto.forum.dto;

public record TopicInsertDTO(
        String title,
        String message,
        Long authorId
) {
}
