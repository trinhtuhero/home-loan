create TABLE IF NOT EXISTS `salary_incomes`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `owner_type`     VARCHAR(20) NULL,
    `payment_method`     VARCHAR(20) NULL,
    `office_name`     VARCHAR(500) NULL,
    `office_phone`     VARCHAR(20) NULL,
    `office_province`     VARCHAR(20) NULL,
    `office_district`     VARCHAR(20) NULL,
    `office_ward`     VARCHAR(20) NULL,
    `office_address`     VARCHAR(500) NULL,
    `office_title`     VARCHAR(50) NULL,
    `value`     BIGINT(30) NULL,
    PRIMARY KEY (`uuid`)
);
