package tech.jdev.rest_api_forum.service;

import org.springframework.stereotype.Service;
import tech.jdev.rest_api_forum.controller.dto.CreateAuthorDto;
import tech.jdev.rest_api_forum.controller.dto.UpdateAuthorDto;
import tech.jdev.rest_api_forum.entity.Author;
import tech.jdev.rest_api_forum.repository.AuthorRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public UUID createUser(CreateAuthorDto authorDto) {
        var user = new Author(
                null,
                authorDto.name(),
                authorDto.email(),
                authorDto.password()
        );
        authorRepository.save(user);

        return user.getId();
    }

    public Optional<Author> getUser(String userId) {
        return authorRepository.findById(UUID.fromString(userId));
    }

    public Author updateAuthor(UpdateAuthorDto updateAuthorDto) {
        var id = UUID.fromString(updateAuthorDto.id());
        var user = authorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No author found with id" + updateAuthorDto.id()));

        var name = Optional.ofNullable(updateAuthorDto.name()).orElse(user.getName());
        var email = Optional.ofNullable(updateAuthorDto.email()).orElse(user.getEmail());
        var password = Optional.ofNullable(updateAuthorDto.password()).orElse(user.getPassword());

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        authorRepository.save(user);

        return user;
    }
}
