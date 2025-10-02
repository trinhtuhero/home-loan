create TABLE IF NOT EXISTS `aml`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `id_passport`       VARCHAR(20) NOT NULL,
    `meta_data`       TEXT NULL,
    `pass`       TINYINT(1) NOT NULL,
    `active`     TINYINT(1) NULL,
    `check_date`     TIMESTAMP(6) NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX aml_loan_id_idx
ON aml (loan_application_id);