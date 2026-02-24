package com.projeto.forum.repositories;

import com.projeto.forum.entities.Topic;
import com.projeto.forum.entities.enums.TopicStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Topic> findByAuthorNameContainingIgnoreCase(String authorName, Pageable pageable);

    Page<Topic> findByStatus(TopicStatus status, Pageable pageable);
}