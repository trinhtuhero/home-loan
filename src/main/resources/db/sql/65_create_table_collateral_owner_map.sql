create TABLE IF NOT EXISTS `collateral_owner_map`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_id`        VARCHAR(36) NOT NULL,
    `collateral_owner_id`       VARCHAR(36) NOT NULL,
    `collateral_id`    VARCHAR(36) NOT NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX collateral_owner_map_loan_id_idx
ON collateral_owner_map (loan_id);