create table upload_m_value_history
(
    uuid    varchar(36)   not null
        primary key,
    content varchar(1000) null,
    file_id varchar(36)   null,
    loan_id varchar(36)   null,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP
);