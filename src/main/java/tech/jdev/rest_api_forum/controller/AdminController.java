package tech.jdev.rest_api_forum.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jdev.rest_api_forum.controller.dto.CreateAuthorDto;
import tech.jdev.rest_api_forum.service.AuthorService;

import java.net.URI;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {

    private final AuthorService authorService;

    public AdminController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> createAdmin(@RequestBody @Valid CreateAuthorDto authorDto) {
        var userId = authorService.createAdmin(authorDto);

        return ResponseEntity.created(URI.create("/v1/admins/" + userId.toString())).build();
    }
}
