package com.projeto.forum.controllers;

import com.projeto.forum.dto.ReplyDTO;
import com.projeto.forum.dto.ReplyInsertDTO;
import com.projeto.forum.dto.ReplyUpdateDTO;
import com.projeto.forum.services.ReplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping
    public ResponseEntity<ReplyDTO> insert(@Valid @RequestBody ReplyInsertDTO dto){
        ReplyDTO newDto = replyService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.id()).toUri();

        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReplyDTO> update(@PathVariable Long id, @RequestBody ReplyUpdateDTO dto){
        ReplyDTO updatedDto = replyService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReplyDTO> delete(@PathVariable Long id){
        replyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
