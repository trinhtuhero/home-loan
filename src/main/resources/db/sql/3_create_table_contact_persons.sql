create TABLE IF NOT EXISTS `contact_persons`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `type`     VARCHAR(20) NULL,
    `full_name`     VARCHAR(150) NULL,
    `phone`     VARCHAR(20) NULL,
    PRIMARY KEY (`uuid`)
);