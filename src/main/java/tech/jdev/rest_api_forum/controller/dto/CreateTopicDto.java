package tech.jdev.rest_api_forum.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import tech.jdev.rest_api_forum.entity.Course;

public record CreateTopicDto (
        @NotEmpty (message = "{required.title}")
        String title,
        @NotEmpty (message = "{required.message}")
        String message,
        @NotEmpty
        String authorId,
        @NotNull(message = "{required.course}")
        Course course
) {
}