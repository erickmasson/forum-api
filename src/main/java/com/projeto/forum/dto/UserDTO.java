package com.projeto.forum.dto;

import com.projeto.forum.entities.User;

public record UserDTO(Long id, String name, String email) {
    public UserDTO(User entity){
        this(entity.getId(), entity.getName(), entity.getEmail());
    }
}
