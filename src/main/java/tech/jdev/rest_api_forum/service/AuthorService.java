package tech.jdev.rest_api_forum.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.jdev.rest_api_forum.controller.dto.CreateAuthorDto;
import tech.jdev.rest_api_forum.controller.dto.ResponseAuthorDto;
import tech.jdev.rest_api_forum.controller.dto.UpdateAuthorDto;
import tech.jdev.rest_api_forum.entity.Author;
import tech.jdev.rest_api_forum.entity.Role;
import tech.jdev.rest_api_forum.repository.AuthorRepository;
import tech.jdev.rest_api_forum.utils.ConvertToAuthorDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void verifyUserExist(CreateAuthorDto authorDto) {
        var usernameExist = authorRepository.existsByUsername(authorDto.username());
        var emailExist = authorRepository.existsByEmail(authorDto.email());

        if (usernameExist || emailExist)
            throw new NoSuchElementException("User already registered");
    }

    public UUID createUser(CreateAuthorDto authorDto) {
        verifyUserExist(authorDto);

        var user = new Author(
                null,
                authorDto.name(),
                authorDto.username(),
                authorDto.email(),
                passwordEncoder.encode(authorDto.password()),
                Role.BASIC
        );
        authorRepository.save(user);

        return user.getId();
    }

    public UUID createAdmin(CreateAuthorDto authorDto) {
        verifyUserExist(authorDto);

        var user = new Author(
                null,
                authorDto.name(),
                authorDto.username(),
                authorDto.email(),
                passwordEncoder.encode(authorDto.password()),
                Role.ADMIN
        );
        authorRepository.save(user);

        return user.getId();
    }

    public Optional<Author> getAuthor(String id) {
        return Optional.ofNullable(authorRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NoSuchElementException("Author with id " + id + " dont found")));
    }

    public List<ResponseAuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(ConvertToAuthorDto::convert)
                .toList();
    }

    public Author updateAuthor(String authorId, UpdateAuthorDto updateAuthorDto) {
        var user = authorRepository.findById(UUID.fromString(authorId))
                .orElseThrow(() -> new NoSuchElementException("No author found with id" + authorId));

        var name = Optional.ofNullable(updateAuthorDto.name()).orElse(user.getName());
        var email = Optional.ofNullable(updateAuthorDto.email()).orElse(user.getEmail());

        if (updateAuthorDto.password() != null)
            user.setPassword(passwordEncoder.encode(updateAuthorDto.password()));

        user.setName(name);
        user.setEmail(email);
        authorRepository.save(user);

        return user;
    }
}