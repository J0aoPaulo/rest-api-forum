package tech.jdev.rest_api_forum.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateTopicDto(
        @NotEmpty (message = "{required.name}")
        String name,
        @NotEmpty (message = "{required.title}")
        String title,
        @NotEmpty (message = "{required.message}")
        String message,
        @NotEmpty (message = "{required.course}")
        String course,
        @NotEmpty (message = "{required.category}")
        String category
) {
}