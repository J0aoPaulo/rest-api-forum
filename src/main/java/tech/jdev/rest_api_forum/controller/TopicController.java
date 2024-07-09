package tech.jdev.rest_api_forum.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jdev.rest_api_forum.controller.dto.CreateTopicDto;
import tech.jdev.rest_api_forum.entity.Topic;
import tech.jdev.rest_api_forum.repository.TopicRepository;
import tech.jdev.rest_api_forum.service.TopicService;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/topics")
public class TopicController {

    private final TopicRepository topicRepository;
    private final TopicService topicService;

    public TopicController(TopicRepository topicRepository, TopicService topicService) {
        this.topicRepository = topicRepository;
        this.topicService = topicService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Topic> createTopic(@RequestBody @Valid CreateTopicDto createTopicDto) {
        try {
            var topicUuid = topicService.createTopic(createTopicDto);
            return ResponseEntity.created(URI.create("/v1/topics" + topicUuid.toString())).build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
