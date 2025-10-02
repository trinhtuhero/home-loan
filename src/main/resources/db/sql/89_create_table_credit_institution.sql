create TABLE IF NOT EXISTS `credit_institution`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `code` VARCHAR(20) NULL,
    `name` VARCHAR(150) NULL,
    PRIMARY KEY (`uuid`)
);
