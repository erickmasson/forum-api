package com.projeto.forum.config;

import com.projeto.forum.entities.Reply;
import com.projeto.forum.entities.Topic;
import com.projeto.forum.entities.User;
import com.projeto.forum.entities.enums.TopicStatus;
import com.projeto.forum.repositories.ReplyRepository;
import com.projeto.forum.repositories.TopicRepository;
import com.projeto.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "Jose", "jose@gmail.com", "123456");
        User u2 = new User(null, "Maria", "maria@gmail.com", "123456");

        userRepository.saveAll(Arrays.asList(u1, u2));

        Topic t1 = new Topic(null, "Erro ao subir container Docker", "Estou recebendo o erro 'pipe not found' ao tentar dar docker-compose up. Alguém ajuda?", Instant.parse("2026-02-13T10:00:00Z"), TopicStatus.OPEN, u1);
        Topic t2 = new Topic(null, "Qual melhor framework JS?", "Gostaria de saber a opinião de vocês sobre React vs Angular em 2026.", Instant.parse("2026-02-13T11:00:00Z"), TopicStatus.SOLVED, u2);

        topicRepository.saveAll(Arrays.asList(t1, t2));

        Reply r1 = new Reply(null, "Verifique se o Docker Desktop está aberto e com a luz verde acessa!", Instant.parse("2026-02-13T10:30:00Z"), t1, u2);
        Reply r2 = new Reply(null, "Obrigado, era exatamente isso! O app estava fechado.", Instant.parse("2026-02-13T10:45:00Z"), t1, u1);

        replyRepository.saveAll(Arrays.asList(r1, r2));
    }
}
