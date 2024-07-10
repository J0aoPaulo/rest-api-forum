package tech.jdev.rest_api_forum.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jdev.rest_api_forum.controller.dto.CreateTopicDto;
import tech.jdev.rest_api_forum.controller.dto.ResponseTopicDto;
import tech.jdev.rest_api_forum.controller.dto.UpdateTopicDto;
import tech.jdev.rest_api_forum.entity.Topic;
import tech.jdev.rest_api_forum.exceptions.TopicAlreadyExistException;
import tech.jdev.rest_api_forum.repository.TopicRepository;
import tech.jdev.rest_api_forum.service.TopicService;
import tech.jdev.rest_api_forum.utils.ConvertPageToDto;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.UUID;

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
        } catch (TopicAlreadyExistException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{topicId}")
    @Transactional
    public ResponseEntity<ResponseTopicDto> updateTopic(@PathVariable("topicId") String topicId,
                                                        @RequestBody UpdateTopicDto updateTopicDto) {
        return null;
    }

    @GetMapping
    public ResponseEntity<Page<ResponseTopicDto>> getAllTopics(Pageable pageable) {
        var pageTopics = topicRepository.findAll(pageable);
        var topics = ConvertPageToDto.convert(pageTopics);

       return ResponseEntity.ok(topics);
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<ResponseTopicDto> getTopic(@PathVariable("topicId") String topicId) {
        return topicRepository.findById(UUID.fromString(topicId))
                .map(topic -> ResponseEntity.ok(new ResponseTopicDto(topic)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
