create TABLE IF NOT EXISTS `loan_application_sequence`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `day`       BIGINT(10) NOT NULL,
    `sequence`       BIGINT(10) NOT NULL,
    PRIMARY KEY (`uuid`, `day`)
);