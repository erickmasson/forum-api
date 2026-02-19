package com.projeto.forum.services;

import com.projeto.forum.dto.TopicDTO;
import com.projeto.forum.dto.TopicDetailsDTO;
import com.projeto.forum.dto.TopicInsertDTO;
import com.projeto.forum.entities.Topic;
import com.projeto.forum.entities.User;
import com.projeto.forum.entities.enums.TopicStatus;
import com.projeto.forum.repositories.TopicRepository;
import com.projeto.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TopicService {

    @Autowired
    private TopicRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<TopicDTO> findAllPaged(Pageable pageable) {
        Page<Topic> page = repository.findAll(pageable);

        return page.map(TopicDTO::new);
    }

    @Transactional(readOnly = true)
    public TopicDetailsDTO findById(Long id){
        Topic entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
        return new TopicDetailsDTO(entity);
    }

    @Transactional(readOnly = true)
    public TopicDTO insert(TopicInsertDTO dto){
        Topic entity = new Topic();
        entity.setTitle(dto.title());
        entity.setMessage(dto.message());
        entity.setCreatedAt(Instant.now());
        entity.setStatus(TopicStatus.OPEN);

        User author = userRepository.findById(dto.authorId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        entity.setAuthor(author);
        entity = repository.save(entity);

        return new TopicDTO(entity);
    }
}

