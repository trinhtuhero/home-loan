create TABLE IF NOT EXISTS `collaterals`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `type`       VARCHAR(150) NULL,
    `status`    VARCHAR(20) NULL,
    `relationship`    VARCHAR(20) NULL,
    `full_name`     VARCHAR(250) NULL,
    `province`     VARCHAR(30),
    `province_name`     VARCHAR(250),
    `district`     VARCHAR(30),
    `district_name`     VARCHAR(250),
    `ward`     VARCHAR(30),
    `ward_name`     VARCHAR(250),
    `address`     VARCHAR(250) NULL,
    `value`     BIGINT(20) NULL,
    `location`     VARCHAR(100) NULL,
    `description`     VARCHAR(250),
    PRIMARY KEY (`uuid`)
);