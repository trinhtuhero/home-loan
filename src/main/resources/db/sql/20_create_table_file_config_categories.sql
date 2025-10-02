create TABLE IF NOT EXISTS `file_config_categories`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `parent_id`       VARCHAR(36) NOT NULL,
    `code`       VARCHAR(50) NULL,
    `name`       VARCHAR(250) NULL,
    `description`       VARCHAR(500) NULL,
    PRIMARY KEY (`uuid`)
);
