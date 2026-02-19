package com.projeto.forum.controllers.exceptions;

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
}
