create TABLE IF NOT EXISTS `other_incomes`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `owner_type`     VARCHAR(20) NULL,
    `income_from`     VARCHAR(20) NULL,
    `value`     BIGINT(30) NULL,
    PRIMARY KEY (`uuid`)
);
