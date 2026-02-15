package com.projeto.forum.services;

import com.projeto.forum.dto.TopicDTO;
import com.projeto.forum.entities.Topic;
import com.projeto.forum.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository repository;

    @Transactional(readOnly = true)
    public Page<TopicDTO> findAllPaged(Pageable pageable) {
        Page<Topic> page = repository.findAll(pageable);

        return page.map(TopicDTO::new);
    }
}
