package tech.jdev.rest_api_forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    private String name;

    public enum Values {
        BASIC(1L),
        ADMIN(2L);

        long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }
    }
}