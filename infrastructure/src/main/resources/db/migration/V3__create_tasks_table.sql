create table tasks (
    id bigserial not null primary key,
    title varchar(255) not null,
    description text,
    status varchar(50) not null,
    priority varchar(50) not null,
    project_id bigint not null references projects(id),
    assignee_id bigint references users(id),
    created_at timestamp not null default now(),
    due_date date
);