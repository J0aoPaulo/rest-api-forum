package tech.jdev.rest_api_forum.service;

import org.springframework.stereotype.Service;
import tech.jdev.rest_api_forum.controller.dto.CreateAuthorDto;
import tech.jdev.rest_api_forum.controller.dto.ResponseAuthorDto;
import tech.jdev.rest_api_forum.controller.dto.ResponseTopicDto;
import tech.jdev.rest_api_forum.controller.dto.UpdateAuthorDto;
import tech.jdev.rest_api_forum.entity.Author;
import tech.jdev.rest_api_forum.entity.Topic;
import tech.jdev.rest_api_forum.repository.AuthorRepository;
import tech.jdev.rest_api_forum.repository.TopicRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final TopicRepository topicRepository;

    public AuthorService(AuthorRepository authorRepository, TopicRepository topicRepository) {
        this.authorRepository = authorRepository;
        this.topicRepository = topicRepository;
    }

    public UUID createUser(CreateAuthorDto authorDto) {
        var user = new Author(
                null,
                authorDto.name(),
                authorDto.email(),
                authorDto.password()
        );
        authorRepository.save(user);

        return user.getId();
    }

    public Optional<Author> getAuthor(String id) {
        return authorRepository.findById(UUID.fromString(id));
    }

    public ResponseAuthorDto convertToAuthorDto(Author author) {
        var topics = convertToTopicDto(author.getTopics());

        return new ResponseAuthorDto(
                author.getName(),
                author.getEmail(),
                author.getPassword(),
                topics
                );
    }

    public List<ResponseAuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(this::convertToAuthorDto)
                .toList();
    }

    public List<ResponseTopicDto> convertToTopicDto(List<Topic> topics) {
        return topics.stream()
                .map(topic -> new ResponseTopicDto(
                        topic.getId().toString(),
                        topic.getTitle(),
                        topic.getMessage(),
                        topic.getCreationDate(),
                        topic.getAuthor().getId().toString()))
                .toList();
    }

    public Author updateAuthor(UpdateAuthorDto updateAuthorDto) {
        var id = UUID.fromString(updateAuthorDto.id());
        var user = authorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No author found with id" + updateAuthorDto.id()));

        var name = Optional.ofNullable(updateAuthorDto.name()).orElse(user.getName());
        var email = Optional.ofNullable(updateAuthorDto.email()).orElse(user.getEmail());
        var password = Optional.ofNullable(updateAuthorDto.password()).orElse(user.getPassword());

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        authorRepository.save(user);

        return user;
    }
}