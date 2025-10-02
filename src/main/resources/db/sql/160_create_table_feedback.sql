create TABLE IF NOT EXISTS `feedback`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `loan_application_id` VARCHAR(36)  NOT NULL,
    `rate` TINYINT NOT NULL,
    `additional_comment` TEXT NULL,
    PRIMARY KEY (`uuid`)
);
