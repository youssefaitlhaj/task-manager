create table projects (
    id bigserial primary key,
    name varchar(255) not null,
    description Text,
    owner_id bigint not null references users(id),
    created_at timestamp not null default now()
);