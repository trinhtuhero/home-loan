create TABLE IF NOT EXISTS `cic`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `mue`       DECIMAL NULL,
    `dti`       DECIMAL NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX cic_loan_id_idx
ON cic (loan_application_id);