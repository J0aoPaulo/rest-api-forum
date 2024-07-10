package tech.jdev.rest_api_forum.utils;

import tech.jdev.rest_api_forum.controller.dto.ResponseAuthorDto;
import tech.jdev.rest_api_forum.entity.Author;

public class ConvertToAuthorDto {

    public static ResponseAuthorDto convert(Author author) {
        var topics = ConvertToTopicDto.convert(author.getTopics());

        return new ResponseAuthorDto(
                author.getName(),
                author.getEmail(),
                author.getPassword(),
                author.getId().toString(),
                topics
        );
    }
}
