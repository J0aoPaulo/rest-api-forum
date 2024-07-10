package tech.jdev.rest_api_forum.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import tech.jdev.rest_api_forum.entity.Author;

public record UpdateAuthorDto(
        String name,
        String email,
        String password) {

        public UpdateAuthorDto(Author author) {
                this(author.getName(), author.getEmail(), author.getPassword());
        }
}
