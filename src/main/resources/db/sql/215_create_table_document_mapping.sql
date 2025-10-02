CREATE TABLE `document_mapping`
(
    `id`            int          NOT NULL AUTO_INCREMENT,
    `created_at`    timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at`    timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP (6),
    `parent_id`     varchar(36)           DEFAULT NULL,
    `code`          varchar(50)           DEFAULT NULL,
    `name`          varchar(250)          DEFAULT NULL,
    `description`   varchar(500)          DEFAULT NULL,
    collateral_type varchar(50)           DEFAULT NULL,
    `type`          varchar(50)           DEFAULT NULL,
    PRIMARY KEY (`id`)
);