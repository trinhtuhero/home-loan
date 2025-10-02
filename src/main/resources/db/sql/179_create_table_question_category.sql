create table question_category
(
    uuid    varchar(36)  not null
        primary key,
    name    varchar(255) null,
    parent  varchar(36)  null,
    product varchar(255) null,
    status  varchar(10)  null,
    created_at     timestamp(6) default CURRENT_TIMESTAMP(6),
    updated_at     timestamp(6) default CURRENT_TIMESTAMP(6),
    constraint uuid
        unique (uuid)
);