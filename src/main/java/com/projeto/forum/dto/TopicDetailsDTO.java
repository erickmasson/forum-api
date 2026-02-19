package com.projeto.forum.dto;

import com.projeto.forum.entities.Topic;
import com.projeto.forum.entities.enums.TopicStatus;

import java.time.Instant;
import java.util.List;

public record TopicDetailsDTO(
        Long id,
        String title,
        String message,
        Instant createdAt,
        TopicStatus status,
        String authorName,
        List<ReplyDTO> replies
) {
    public TopicDetailsDTO (Topic entity){
        this(
                entity.getId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getCreatedAt(),
                entity.getStatus(),
                entity.getAuthor().getName(),
                entity.getReplies().stream().map(ReplyDTO::new).toList()
        );
    }
}
