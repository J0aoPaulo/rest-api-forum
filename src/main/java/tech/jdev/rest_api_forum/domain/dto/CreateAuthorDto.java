package tech.jdev.rest_api_forum.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateAuthorDto(
        @NotBlank
        String name,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
