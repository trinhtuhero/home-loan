create TABLE IF NOT EXISTS `css`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `profile_id`       INT(10) NOT NULL,
    `score`       VARCHAR(20) NULL,
    `grade`       VARCHAR(10) NULL,
    `scoring_date`       DATETIME NULL,
    `meta_data`       TEXT NOT NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX css_loan_id_idx
ON css (loan_application_id);