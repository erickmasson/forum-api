package com.projeto.forum.dto;

import com.projeto.forum.entities.Topic;
import com.projeto.forum.entities.enums.TopicStatus;

import java.time.Instant;

public record TopicDTO(
        Long id,
        String title,
        String message,
        Instant createdAt,
        TopicStatus status,
        String authorName
) {
    public TopicDTO(Topic entity) {
        this(
                entity.getId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getCreatedAt(),
                entity.getStatus(),
                entity.getAuthor().getName()
        );
    }
}