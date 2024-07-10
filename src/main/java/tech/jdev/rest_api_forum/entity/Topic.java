package tech.jdev.rest_api_forum.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Topic {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "active")
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @Enumerated(EnumType.STRING)
    private Course course;
}