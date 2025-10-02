create TABLE IF NOT EXISTS `file_config`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `file_config_category_id`       VARCHAR(36) NOT NULL,
    `code`       VARCHAR(50) NULL,
    `name`       VARCHAR(250) NULL,
    `description`       VARCHAR(500) NULL,
    `short_guide_desc`       VARCHAR(500) NULL,
    `long_guide_desc`       TEXT NULL,
    `require`       TINYINT NULL,
    `max_limit` INT NULL,
    PRIMARY KEY (`uuid`)
);
