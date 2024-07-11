package tech.jdev.rest_api_forum.service;

import org.springframework.stereotype.Service;
import tech.jdev.rest_api_forum.controller.dto.CreateTopicDto;
import tech.jdev.rest_api_forum.controller.dto.UpdateTopicDto;
import tech.jdev.rest_api_forum.entity.Topic;
import tech.jdev.rest_api_forum.exceptions.TopicAlreadyExistException;
import tech.jdev.rest_api_forum.repository.AuthorRepository;
import tech.jdev.rest_api_forum.repository.TopicRepository;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final AuthorRepository authorRepository;

    public TopicService(TopicRepository topicRepository, AuthorRepository authorRepository) {
        this.topicRepository = topicRepository;
        this.authorRepository = authorRepository;
    }

    public UUID createTopic(CreateTopicDto topicDto) {
        var titleExist = topicRepository.existsByTitle(topicDto.title());
        var messageExist = topicRepository.existsByMessage(topicDto.message());

        if (titleExist || messageExist)
            throw new TopicAlreadyExistException("Topic already exist in database. Change title or message.");

        var author = authorRepository.findById(UUID.fromString(topicDto.authorId()))
                .orElseThrow(() -> new NoSuchElementException("Not author found with id " + topicDto.authorId()));

        var topic = new Topic(
                null,
                topicDto.title(),
                topicDto.message(),
                Instant.now(),
                true,
                author,
                topicDto.course()
        );
        topicRepository.save(topic);

        return topic.getId();
    }

    public Topic updateTopic(String id, UpdateTopicDto updateDto) {
        var updatedTopic = topicRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NoSuchElementException("Topic with id " + id + " not found"));

        if (updateDto.message() != null)
            updatedTopic.setMessage(buildUpdatedMessage(updatedTopic.getMessage(), updateDto.message()).toString());

        if (updateDto.active() == null)
            updatedTopic.setActive(updatedTopic.isActive());
        else if (updatedTopic.isActive() != updateDto.active())
            updatedTopic.setActive(updateDto.active());

        updatedTopic.setCourse(Optional.ofNullable(updateDto.course()).orElse(updatedTopic.getCourse()));

        topicRepository.save(updatedTopic);
        return updatedTopic;
    }

    private StringBuilder buildUpdatedMessage(String actualMessage, String newMesage) {
        StringBuilder sb = new StringBuilder();

        return sb.append(actualMessage).append(" ").append(newMesage);
    }

    public void disableTopic(String userId) {
        var topicUuid = UUID.fromString(userId);
        var topic = topicRepository.findById(topicUuid)
                .orElseThrow(() -> new NoSuchElementException("Topic with id " + userId + " not found"));

        topic.setActive(false);
    }

    public void deleteTopic(String userId) {
        var topicUuid = UUID.fromString(userId);

        if (!topicRepository.existsById(topicUuid))
            throw new NoSuchElementException("Topic with id " + userId + " not found");

        topicRepository.deleteById(topicUuid);
    }
}