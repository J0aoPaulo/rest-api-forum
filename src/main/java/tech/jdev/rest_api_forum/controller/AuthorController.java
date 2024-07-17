package tech.jdev.rest_api_forum.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jdev.rest_api_forum.controller.dto.*;
import tech.jdev.rest_api_forum.entity.Author;
import tech.jdev.rest_api_forum.repository.AuthorRepository;
import tech.jdev.rest_api_forum.service.AuthorService;
import tech.jdev.rest_api_forum.utils.ConvertToTopicDto;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearer-key")
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
    public ResponseEntity<Void> create(@RequestBody @Valid CreateAuthorDto authorDto) {
        var userId = authorService.createUser(authorDto);

        return ResponseEntity.created(URI.create("/v1/authors/" + userId.toString())).build();
    }

    @PutMapping("/{authorId}")
    @Transactional
    public ResponseEntity<UpdateAuthorDto> updateAuthor(@PathVariable("authorId") String authorId,
                                                        @RequestBody @Valid UpdateAuthorDto updateAuthorDto) {

        var updatedAuthor = authorService.updateAuthor(authorId, updateAuthorDto);

        return ResponseEntity.ok(new UpdateAuthorDto(updatedAuthor));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseAuthorDto> getUser(@PathVariable("userId") String userId) {
        var author = authorService.getAuthor(userId).get();
        List<ResponseTopicDto> topics = ConvertToTopicDto.convert(author.getTopics());

        return ResponseEntity.ok(new ResponseAuthorDto(author, topics));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ResponseAuthorDto>> getAllUsers() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @DeleteMapping("/{userId}")
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("userId") String userId) {
        if (authorRepository.existsById(UUID.fromString(userId))) {
            authorRepository.deleteById(UUID.fromString(userId));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteOwnAccount(@RequestBody @Valid DeleteAccountDto deleteDto) {
        var user = authorRepository.findByUsername(deleteDto.username())
                .orElseThrow(() -> new NoSuchElementException("Username incorrect"));

        authorRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}