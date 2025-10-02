create TABLE IF NOT EXISTS `cms_file_config_rule`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `file_config_id`       VARCHAR(36) NOT NULL,
    `cms_file_rule_id`       VARCHAR(36) NOT NULL,
    PRIMARY KEY (`uuid`)
);
