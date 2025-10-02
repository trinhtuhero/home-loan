create TABLE IF NOT EXISTS `creditworthiness_items`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `credit_appraisal_id` VARCHAR(36) NOT NULL,
    `target` VARCHAR(50) NULL,
    `credit_institution` VARCHAR(50) NULL,
    `credit_institution_name` VARCHAR(150) NULL,
    `form_of_credit` VARCHAR(50) NULL,
    `current_balance` BIGINT(30) NULL,
    `monthly_debt_payment` BIGINT(30) NULL,
    PRIMARY KEY (`uuid`)
);
