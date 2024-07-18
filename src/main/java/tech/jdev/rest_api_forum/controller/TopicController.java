package tech.jdev.rest_api_forum.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jdev.rest_api_forum.controller.dto.CreateTopicDto;
import tech.jdev.rest_api_forum.controller.dto.ResponseDetailsTopic;
import tech.jdev.rest_api_forum.controller.dto.ResponseTopicDto;
import tech.jdev.rest_api_forum.controller.dto.UpdateTopicDto;
import tech.jdev.rest_api_forum.repository.TopicRepository;
import tech.jdev.rest_api_forum.service.TopicService;
import tech.jdev.rest_api_forum.utils.ConvertPageToDto;

import java.net.URI;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearer-key")
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
    public ResponseEntity<Void> createTopic(@RequestBody @Valid CreateTopicDto createTopicDto) {
        var topicUuid = topicService.createTopic(createTopicDto);

        return ResponseEntity.created(URI.create("/v1/topics/" + topicUuid.toString())).build();
    }

    @PutMapping("/{topicId}")
    @Transactional
    public ResponseEntity<ResponseTopicDto> updateTopic(@PathVariable("topicId") String topicId,
                                                        @RequestBody UpdateTopicDto updateTopicDto) {

        var updatedTopic = topicService.updateTopic(topicId, updateTopicDto);
        return ResponseEntity.ok(new ResponseTopicDto(updatedTopic));
    }

    @GetMapping("/active")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Page<ResponseTopicDto>> getAllActivatedTopics(Pageable pageable) {
        var pageTopics = topicRepository.findAllByActiveTrue(pageable);
        var topics = ConvertPageToDto.convert(pageTopics);

        return ResponseEntity.ok(topics);
    }

    @GetMapping(path = "/inactive")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Page<ResponseTopicDto>> getAllDesactivatedTopics(Pageable pageable) {
        var pageTopics = topicRepository.findAllByActiveFalse(pageable);
        var topics = ConvertPageToDto.convert(pageTopics);

        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{topicId}/info")
    public ResponseEntity<ResponseTopicDto> getTopic(@PathVariable("topicId") String topicId) {
        return topicRepository
                .findById(UUID.fromString(topicId))
                .map(t -> ResponseEntity.ok(new ResponseTopicDto(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{topicId}/info/detail")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDetailsTopic> getDetailTopic(@PathVariable("topicId") String topicId) {
        return topicRepository
                .findById(UUID.fromString(topicId))
                .map(t -> ResponseEntity.ok(new ResponseDetailsTopic(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{topicId}/disable")
    @Transactional
    public ResponseEntity<Void> disableTopic(@PathVariable("topicId") String topicId) {
        topicService.disableTopic(topicId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{topicId}/delete")
    @Transactional
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTopic(@PathVariable("topicId") String topicdId) {
        topicService.deleteTopic(topicdId);
        return ResponseEntity.ok().build();
    }
}