package tech.jdev.rest_api_forum.utils;

import org.springframework.data.domain.Page;
import tech.jdev.rest_api_forum.controller.dto.ResponseTopicDto;
import tech.jdev.rest_api_forum.entity.Topic;

public class ConvertPageToDto {

    public static Page<ResponseTopicDto> convert(Page<Topic> topics) {
        return topics.map(topic -> new ResponseTopicDto (
                topic.getId().toString(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.isActive(),
                topic.getAuthor().getId().toString(),
                topic.getCourse()));
    }
}