package com.projeto.forum.controllers;

import com.projeto.forum.dto.LoginDTO;
import com.projeto.forum.dto.TokenDTO;
import com.projeto.forum.entities.User;
import com.projeto.forum.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dto){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        Authentication authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }
}
