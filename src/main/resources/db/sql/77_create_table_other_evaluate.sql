create TABLE IF NOT EXISTS `other_evaluate`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `owner_type`     VARCHAR(20) NULL,
    `legal_record`     VARCHAR(100) NOT NULL,
    `rm_input_value`     BIGINT(30) NOT NULL,
    `rm_review`     VARCHAR(250) NULL,
    PRIMARY KEY (`uuid`, `loan_application_id`)
);
