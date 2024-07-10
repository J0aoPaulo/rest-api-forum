package tech.jdev.rest_api_forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.jdev.rest_api_forum.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
