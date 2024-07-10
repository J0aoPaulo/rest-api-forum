package tech.jdev.rest_api_forum.utils;

import tech.jdev.rest_api_forum.controller.dto.ResponseTopicDto;
import tech.jdev.rest_api_forum.entity.Topic;

import java.util.List;

public class ConvertToTopicDto {

    public static List<ResponseTopicDto> convert(List<Topic> topics) {
        return topics.stream()
                .map(topic -> new ResponseTopicDto(
                        topic.getId().toString(),
                        topic.getTitle(),
                        topic.getMessage(),
                        topic.getCreationDate(),
                        topic.getAuthor().getId().toString()))
                .toList();
    }
}