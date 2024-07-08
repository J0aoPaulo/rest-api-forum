package tech.jdev.rest_api_forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.jdev.rest_api_forum.domain.entity.Topic;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, UUID> {
}
