package tech.jdev.rest_api_forum.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jdev.rest_api_forum.controller.dto.*;
import tech.jdev.rest_api_forum.entity.Topic;
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

        return ResponseEntity.created(URI.create("/v1/authors/" + userId)).build();
    }

    @PutMapping("/{authorId}")
    @Transactional
    public ResponseEntity<UpdateAuthorDto> updateAuthor(@PathVariable("authorId") String authorId,
                                                        @RequestBody @Valid UpdateAuthorDto updateAuthorDto) {

        var updatedAuthor = authorService.updateAuthor(authorId, updateAuthorDto);

        return ResponseEntity.ok(new UpdateAuthorDto(updatedAuthor));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ResponseAuthorDto>> getAllUsers() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseAuthorDto> getUser(@PathVariable("userId") String userId) {
        var author = authorRepository
                .findById(UUID.fromString(userId))
                .orElseThrow(() -> new NoSuchElementException("Author with " + userId + " not found."));

        if (!author.isActive())
            throw new NoSuchElementException("Author is disabled");

        var topicsActive = author.getTopics().stream().filter(Topic::isActive).toList();

        List<ResponseTopicDto> topics = ConvertToTopicDto.convert(topicsActive);

        return ResponseEntity.ok(new ResponseAuthorDto(author, topics));
    }

    @DeleteMapping("/{userId}")
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("userId") String userId) {
        if (authorRepository.existsById(UUID.fromString(userId))) {
            authorRepository.deleteById(UUID.fromString(userId));
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/disable")
    @Transactional
    public ResponseEntity<Void> disableOwnAccount(@RequestBody @Valid DisableAccountDto disableDto) {
        var user = authorRepository
                .findByUsername(disableDto.username())
                .orElseThrow(() -> new NoSuchElementException("Username incorrect"));

        authorService.disableAuthor(user);

        return ResponseEntity.ok().build();
    }
}