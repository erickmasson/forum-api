package com.projeto.forum.controllers.exceptions;

import com.projeto.forum.services.exceptions.DatabaseException;
import com.projeto.forum.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validation(MethodArgumentNotValidException e, HttpServletRequest request){
        Map<String, Object> err = new HashMap<>();
        err.put("timestamp", Instant.now());
        err.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
        err.put("error", "Erro de validação");

        Map<String, String> fieldErrors = new HashMap<>();
        for(FieldError f : e.getBindingResult().getFieldErrors()){
            fieldErrors.put(f.getField(), f.getDefaultMessage());
        }

        err.put("errors", fieldErrors);
        err.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        Map<String, Object> err = new HashMap<>();

        err.put("timestamp", Instant.now());
        err.put("status", HttpStatus.NOT_FOUND.value());
        err.put("error", "Recurso não encontrado");
        err.put("message", e.getMessage());
        err.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Map<String, Object>> database(DatabaseException e, HttpServletRequest request){
        Map<String, Object> err = new HashMap<>();
        err.put("timestamp", Instant.now());
        err.put("status", HttpStatus.BAD_REQUEST.value());
        err.put("error", "Erro de integridade de dados");
        err.put("message", e.getMessage());
        err.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(org.springframework.dao.InvalidDataAccessApiUsageException.class)
    public ResponseEntity<Map<String, Object>> invalidDataUsage(RuntimeException e, HttpServletRequest request) {
        Map<String, Object> err = new HashMap<>();
        err.put("timestamp", Instant.now());
        err.put("status", HttpStatus.BAD_REQUEST.value());
        err.put("error", "Parâmetro de ordenação ou paginação inválido");
        err.put("message", "Verifique se os campos de ordenação existem na entidade.");
        err.put("path", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
