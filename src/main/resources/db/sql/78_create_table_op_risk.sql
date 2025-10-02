create TABLE IF NOT EXISTS `op_risk`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `identity_card`       VARCHAR(20) NOT NULL,
    `name`       VARCHAR(250) NOT NULL,
    `birthday`       VARCHAR(20) NOT NULL,
    `end_date`       VARCHAR(20) NOT NULL,
    `meta_data`       TEXT NOT NULL,
    `pass`       TINYINT(1) NOT NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX op_risk_loan_id_idx
ON op_risk (loan_application_id);