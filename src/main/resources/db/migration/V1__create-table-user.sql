create table authors (
    id binary(16) not null,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,

    primary key (id)
)