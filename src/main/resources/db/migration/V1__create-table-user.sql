CREATE TABLE authors (
    id BINARY(16) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) unique NOT NULL,
    password VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
)