package tech.jdev.rest_api_forum.controller.dto;

import tech.jdev.rest_api_forum.entity.Author;
import tech.jdev.rest_api_forum.entity.Topic;

import java.util.List;

public record ResponseAuthorDto(String name, String email, String password, List<Topic> topics) {

    public ResponseAuthorDto(Author author) {
        this(author.getName(), author.getEmail(), author.getPassword(), null);
    }
}
