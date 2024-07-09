package tech.jdev.rest_api_forum.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jdev.rest_api_forum.controller.dto.CreateAuthorDto;
import tech.jdev.rest_api_forum.controller.dto.ResponseAuthorDto;
import tech.jdev.rest_api_forum.entity.Author;
import tech.jdev.rest_api_forum.service.AuthorService;

import java.net.URI;

@RestController
@RequestMapping("/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Author> create(@RequestBody @Valid CreateAuthorDto authorDto) {
        var userId = authorService.createUser(authorDto);

        return ResponseEntity.created(URI.create("/v1/authors/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseAuthorDto> getUser(@PathVariable("userId") String userId) {
        var author = authorService.getUser(userId);

        if (author.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new ResponseAuthorDto(author.get()));
    }
}
