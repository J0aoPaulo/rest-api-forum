package tech.jdev.rest_api_forum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.jdev.rest_api_forum.entity.Topic;

import java.util.UUID;

@Repository
public interface TopicRepository extends JpaRepository<Topic, UUID> {

    boolean existsByMessage(String message);

    boolean existsByTitle(String title);

    Page<Topic> findAll(Pageable pageable);

    Page<Topic> findAllByActiveTrue(Pageable pageable);

    Page<Topic> findAllByActiveFalse(Pageable pageable);
}
