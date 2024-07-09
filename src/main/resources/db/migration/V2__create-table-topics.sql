CREATE TABLE topics (
    id BINARY(16) NOT NULL,
    title VARCHAR(255) NOT NULL,
    message VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL,
    author_id BINARY(16) NOT NULL,
    course VARCHAR(255) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES authors(id)
)