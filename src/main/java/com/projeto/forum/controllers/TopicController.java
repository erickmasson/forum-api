package com.projeto.forum.controllers;

import com.projeto.forum.dto.TopicDTO;
import com.projeto.forum.dto.TopicDetailsDTO;
import com.projeto.forum.dto.TopicInsertDTO;
import com.projeto.forum.dto.TopicUpdateDTO;
import com.projeto.forum.services.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsDTO> findById(@PathVariable Long id){
        TopicDetailsDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TopicDTO> insert(@Valid @RequestBody TopicInsertDTO dto){
        TopicDTO newDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();

        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDTO> update(@PathVariable Long id, @Valid @RequestBody TopicUpdateDTO dto){
        TopicDTO updatedDto = service.update(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
