package com.projeto.forum.dto;

import com.projeto.forum.entities.Reply;

import java.time.Instant;

public record ReplyDTO(
        Long id,
        String message,
        Instant createdAt,
        String authorName
) {
    public ReplyDTO (Reply entity){
        this(
                entity.getId(),
                entity.getMessage(),
                entity.getCreatedAt(),
                entity.getAuthor().getName()
        );
    }
}
