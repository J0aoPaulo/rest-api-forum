package tech.jdev.rest_api_forum.service;

import org.springframework.stereotype.Service;
import tech.jdev.rest_api_forum.controller.dto.CreateTopicDto;
import tech.jdev.rest_api_forum.repository.TopicRepository;

import java.util.UUID;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public UUID createTopic(CreateTopicDto topicDto) {
        return null;
    }
}
