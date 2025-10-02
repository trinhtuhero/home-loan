create TABLE IF NOT EXISTS `loan_item_collateral_distribute`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `uuid`       VARCHAR(36) NOT NULL,
    `loan_item_id`       VARCHAR(36) NULL,
    `collateral_id`       VARCHAR(36) NULL,
    `percent`       DECIMAL(5,2) NULL,
    PRIMARY KEY (`uuid`)
);
