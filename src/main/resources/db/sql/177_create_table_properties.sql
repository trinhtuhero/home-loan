create TABLE IF NOT EXISTS `properties`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `name`         VARCHAR(250) NOT NULL,
    `description`         VARCHAR(500) NULL,
    `current_value`         TEXT NULL,
    `default_value`         TEXT NULL,
    PRIMARY KEY (`uuid`)
);
