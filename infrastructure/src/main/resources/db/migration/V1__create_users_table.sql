create table users (
    id         bigserial primary key,
    username   varchar(100) not null unique,
    email      varchar(255) not null unique,
    role       varchar(50)  not null,
    created_at TIMESTAMP    not null default now()
);