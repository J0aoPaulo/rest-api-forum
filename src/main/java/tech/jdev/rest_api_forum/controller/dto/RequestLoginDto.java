package tech.jdev.rest_api_forum.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record RequestLoginDto(
        @NotEmpty
        String username,
        @NotEmpty
        String password) {
}