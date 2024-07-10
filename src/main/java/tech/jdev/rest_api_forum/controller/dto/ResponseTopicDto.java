package tech.jdev.rest_api_forum.controller.dto;

import tech.jdev.rest_api_forum.entity.Course;
import tech.jdev.rest_api_forum.entity.Topic;

import java.time.Instant;

public record ResponseTopicDto(
        String id,
        String title,
        String message,
        Instant creationDate,
        boolean active,
        String authorId,
        Course course) {

    public ResponseTopicDto(Topic topic) {
        this(
            topic.getId().toString(),
            topic.getTitle(),
            topic.getMessage(),
            topic.getCreationDate(),
            topic.isActive(),
            topic.getAuthor().getId().toString(),
            topic.getCourse()
        );
    }
}
