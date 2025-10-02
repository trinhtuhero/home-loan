create table question
(
    uuid       varchar(36)                               not null
        primary key
        unique,
    status     varchar(10)                               null,
    content    varchar(1000)                             null,
    category_id    varchar(36)                             null,
    created_at timestamp(6) default CURRENT_TIMESTAMP(6) null,
    updated_at timestamp(6) default CURRENT_TIMESTAMP(6) null
);