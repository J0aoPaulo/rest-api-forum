package tech.jdev.rest_api_forum.controller.dto;

import tech.jdev.rest_api_forum.entity.Course;

public record UpdateTopicDto(String topicId, String message, Boolean active, Course course) {
}
