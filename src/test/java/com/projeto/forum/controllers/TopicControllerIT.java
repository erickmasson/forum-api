package com.projeto.forum.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.forum.dto.LoginDTO;
import com.projeto.forum.dto.TopicInsertDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(properties = "api.security.token.secret=chave-de-teste-muito-segura")
@Transactional
public class TopicControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @Test
    @DisplayName("Deveria criar um tópico quando usuário está autenticado")
    void insertShouldReturnCreatedWhenAuthenticated() throws Exception {
        LoginDTO loginDTO = new LoginDTO("jose@gmail.com", "123456");
        String loginJson = objectMapper.writeValueAsString(loginDTO);

        String response = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = objectMapper.readTree(response).get("token").asText();

        TopicInsertDTO topicDto = new TopicInsertDTO("Título do Teste", "Mensagem do teste automatizado");
        String topicJson = objectMapper.writeValueAsString(topicDto);

        mockMvc.perform(post("/topics")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topicJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Título do Teste"))
                .andExpect(jsonPath("$.authorName").value("Jose"));
    }

    @Test
    @DisplayName("Deveria retornar 403 ao tentar criar tópico sem token")
    void insertShouldReturnForbiddenWhenNoToken() throws Exception {
        TopicInsertDTO topicDto = new TopicInsertDTO("Título Hacker", "Tentando postar sem login");
        String topicJson = objectMapper.writeValueAsString(topicDto);

        mockMvc.perform(post("/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topicJson))
                .andExpect(status().isForbidden());
    }
}
