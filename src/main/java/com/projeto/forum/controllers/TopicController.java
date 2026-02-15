package com.projeto.forum.controllers;

import com.projeto.forum.dto.TopicDTO;
import com.projeto.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    private TopicService service;

    @GetMapping
    public ResponseEntity<Page<TopicDTO>> findAll(Pageable pageable){
        Page<TopicDTO> page = service.findAllPaged(pageable);
        return ResponseEntity.ok(page);
    }
}
