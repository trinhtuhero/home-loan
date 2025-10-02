create TABLE IF NOT EXISTS `cic_item`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `cic_id`       VARCHAR(36) NOT NULL,
    `identity_card`       VARCHAR(20) NOT NULL,
    `type_debt`     VARCHAR(10) NOT NULL,
    `type_debt_12`     VARCHAR(10) NOT NULL,
    `type_debt_24`     VARCHAR(10) NOT NULL,
    `description`     VARCHAR(20) NOT NULL,
    `pass`       TINYINT(1) NULL,
    `amount_loan_non_msb_secure`     BIGINT(20) NULL,
    `amount_loan_non_msb_unsecure`     BIGINT(20) NULL,
    `amount_loan_msb_secure`     BIGINT(20) NULL,
    `amount_loan_msb_unsecure`     BIGINT(20) NULL,
    `amount_card_non_msb`     BIGINT(20) NULL,
    `amount_card_msb`     BIGINT(20) NULL,
    `amount_over_draft_msb`     BIGINT(20) NULL,
    `meta_data`       LONGTEXT NOT NULL,
    PRIMARY KEY (`uuid`)
);