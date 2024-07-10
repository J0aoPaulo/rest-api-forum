package tech.jdev.rest_api_forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.jdev.rest_api_forum.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
