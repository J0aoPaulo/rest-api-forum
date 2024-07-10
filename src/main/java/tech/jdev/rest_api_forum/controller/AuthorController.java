package tech.jdev.rest_api_forum.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jdev.rest_api_forum.controller.dto.CreateAuthorDto;
import tech.jdev.rest_api_forum.controller.dto.ResponseAuthorDto;
import tech.jdev.rest_api_forum.controller.dto.ResponseTopicDto;
import tech.jdev.rest_api_forum.controller.dto.UpdateAuthorDto;
import tech.jdev.rest_api_forum.entity.Author;
import tech.jdev.rest_api_forum.repository.AuthorRepository;
import tech.jdev.rest_api_forum.service.AuthorService;
import tech.jdev.rest_api_forum.utils.ConvertToTopicDto;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/v1/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorService authorService, AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.authorRepository = authorRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Author> create(@RequestBody @Valid CreateAuthorDto authorDto) {
        var userId = authorService.createUser(authorDto);

        return ResponseEntity.created(URI.create("/v1/authors/" + userId.toString())).build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UpdateAuthorDto> updateAuthor(@RequestBody @Valid UpdateAuthorDto updateAuthorDto) {
        try {
            var updatedAuthor = authorService.updateAuthor(updateAuthorDto);

            return ResponseEntity.ok(new UpdateAuthorDto(updatedAuthor));
        } catch (NoSuchElementException ex) {
           return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseAuthorDto> getUser(@PathVariable("userId") String userId) {
        try {
            var author = authorService.getAuthor(userId).get();
            List<ResponseTopicDto> topics = ConvertToTopicDto.convert(author.getTopics());

            return ResponseEntity.ok(new ResponseAuthorDto(author, topics));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseAuthorDto>> getAllUsers() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @DeleteMapping("/{userId}")
    @Transactional
    public ResponseEntity<Void> deleteAuthor(@PathVariable("userId") String userId) {
        if (authorRepository.existsById(UUID.fromString(userId))) {
            authorRepository.deleteById(UUID.fromString(userId));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}