CREATE TABLE roles (
    role_id BIGINT NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL UNIQUE,

    PRIMARY KEY (role_id)
);

INSERT INTO roles (name) VALUES ('BASIC');
INSERT INTO roles (name) VALUES ('ADMIN');