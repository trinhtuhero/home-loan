create TABLE IF NOT EXISTS `file_rules`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `expression`       VARCHAR(500) NULL,
    `description`       VARCHAR(500) NULL,
    PRIMARY KEY (`uuid`)
);
