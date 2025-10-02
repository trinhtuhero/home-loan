create TABLE IF NOT EXISTS `business_incomes`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `owner_type`     VARCHAR(20) NULL,
    `business_type`     VARCHAR(20) NULL,
    `business_line`     VARCHAR(20) NULL,
    `business_code`     VARCHAR(50) NULL,
    `name`     VARCHAR(500) NULL,
    `province`     VARCHAR(20) NULL,
    `district`     VARCHAR(20) NULL,
    `ward`     VARCHAR(20) NULL,
    `address`     VARCHAR(500) NULL,
    `phone`     VARCHAR(20) NULL,
    `value`     BIGINT(30) NULL,
    PRIMARY KEY (`uuid`)
);
