package tech.jdev.rest_api_forum.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTopicDto(
        @NotBlank
        String name,
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotBlank
        String course,
        @NotBlank
        String category
) {
}