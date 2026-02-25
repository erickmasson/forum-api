package com.projeto.forum.services;

import com.projeto.forum.dto.ReplyDTO;
import com.projeto.forum.dto.ReplyInsertDTO;
import com.projeto.forum.dto.ReplyUpdateDTO;
import com.projeto.forum.entities.Reply;
import com.projeto.forum.entities.Topic;
import com.projeto.forum.entities.User;
import com.projeto.forum.repositories.ReplyRepository;
import com.projeto.forum.repositories.TopicRepository;
import com.projeto.forum.repositories.UserRepository;
import com.projeto.forum.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ReplyDTO insert(ReplyInsertDTO dto){

        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Reply entity = new Reply();
        entity.setMessage(dto.message());
        entity.setCreatedAt(Instant.now());

        Topic topic = topicRepository.findById(dto.topicId())
                .orElseThrow(() -> new ResourceNotFoundException("Tópico não encontrado com ID: " + dto.topicId()));

        entity.setTopic(topic);
        entity.setAuthor(author);

        entity = replyRepository.save(entity);
        return new ReplyDTO(entity);
    }

    @Transactional
    public ReplyDTO update(Long id, ReplyUpdateDTO dto){
        try {
            Reply entity = replyRepository.getReferenceById(id);
            entity.setMessage(dto.message());
            entity = replyRepository.save(entity);
            return new ReplyDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Resposta não encontrada com ID: " + id);
        }
    }

    @Transactional
    public void delete(Long id){
        if(!replyRepository.existsById(id)){
            throw new ResourceNotFoundException("Resposta não encontrada com ID: " + id);
        }
        replyRepository.deleteById(id);
    }
}
