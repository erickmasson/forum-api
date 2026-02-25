package com.projeto.forum.services;

import com.projeto.forum.dto.UserDTO;
import com.projeto.forum.dto.UserInsertDTO;
import com.projeto.forum.dto.UserUpdateDTO;
import com.projeto.forum.entities.User;
import com.projeto.forum.repositories.UserRepository;
import com.projeto.forum.services.exceptions.DatabaseException;
import com.projeto.forum.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDTO> findAll(){
        return repository.findAll().stream().map(UserDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto){
        User entity = new User();
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setPassword(passwordEncoder.encode(dto.password()));
        entity = repository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto){
        try {
            User entity = repository.getReferenceById(id);
            entity.setName(dto.name());
            entity.setEmail(dto.email());
            entity = repository.save(entity);
            return new UserDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }
    }

    @Transactional
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
        }

        try{
            repository.deleteById(id);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível deletar um usuário que possui tópicos ou respostas vinculadas.");
        }

        repository.deleteById(id);
    }
}
