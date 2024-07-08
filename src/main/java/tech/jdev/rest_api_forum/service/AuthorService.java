package tech.jdev.rest_api_forum.service;

import org.springframework.stereotype.Service;
import tech.jdev.rest_api_forum.domain.dto.CreateAuthorDto;
import tech.jdev.rest_api_forum.domain.entity.Author;
import tech.jdev.rest_api_forum.repository.AuthorRepository;

import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public UUID createUser(CreateAuthorDto authorDto) {

        var user = new Author(
                UUID.randomUUID(),
                authorDto.name(),
                authorDto.email(),
                authorDto.password()
        );
        authorRepository.save(user);

        return user.getId();
    }
}
