package tech.jdev.rest_api_forum.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CreateAuthorDto(
        @NotEmpty(message = "{required.name}")
        String name,
        @NotEmpty
        String username,
        @Email (message = "{format.email}")
        @NotEmpty (message = "{required.email}")
        String email,
        @NotEmpty (message = "required.password")
        String password
) {
}
