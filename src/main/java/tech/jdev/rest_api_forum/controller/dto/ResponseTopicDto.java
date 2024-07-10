package tech.jdev.rest_api_forum.controller.dto;

import tech.jdev.rest_api_forum.entity.Course;
import tech.jdev.rest_api_forum.entity.Topic;

import java.time.Instant;

public record ResponseTopicDto(
        String id,
        String title,
        String message,
        Course course,
        Instant creationDate) {

    public ResponseTopicDto(Topic topic) {
        this(
            topic.getId().toString(),
            topic.getTitle(),
            topic.getMessage(),
            topic.getCourse(),
            topic.getCreationDate()
        );
    }
}
