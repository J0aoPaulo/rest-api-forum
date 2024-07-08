package tech.jdev.rest_api_forum.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jdev.rest_api_forum.domain.dto.CreateAuthorDto;
import tech.jdev.rest_api_forum.service.AuthorService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UUID> create(@RequestBody CreateAuthorDto authorDto) {
        var userId = authorService.createUser(authorDto);

        return ResponseEntity.created(URI.create("/v1/authors/" + userId.toString())).build();
    }
}
