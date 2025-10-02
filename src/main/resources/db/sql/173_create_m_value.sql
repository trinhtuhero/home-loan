create TABLE IF NOT EXISTS `m_value`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `loan_application_id`         VARCHAR(36)  NOT NULL,
    `asset_code`         VARCHAR(255) NULL,
    `check_date`     TIMESTAMP(6) NULL,
    PRIMARY KEY (`uuid`)
);
