create TABLE IF NOT EXISTS `credit_appraisal`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `total_of_debits` BIGINT(30) NULL,
    `total_income` BIGINT(30) NULL,
    `dti` DOUBLE NULL,
    `ltv` DOUBLE NULL,
    `credit_evaluation_result` VARCHAR(50) NULL,
    `before_open_limit_condition` VARCHAR(250) NULL,
    `before_disbursement_condition` VARCHAR(250) NULL,
    `after_disbursement_condition` VARCHAR(250) NULL,
    `business_review` VARCHAR(2000) NULL,
    `css_profile_id` VARCHAR(50) NULL,
    `css_score` DOUBLE NULL,
    `css_grade` VARCHAR(50) NULL,
    `scoring_date` DATETIME  NULL,
    `business_area` VARCHAR(50) NULL,
    `business_name` VARCHAR(150) NULL,
    `sale_full_name` VARCHAR(150) NULL,
    `sale_phone` VARCHAR(50) NULL,
    `manager_full_name` VARCHAR(150) NULL,
    `manager_phone` VARCHAR(50) NULL,
    `signature_level` VARCHAR(50) NULL,

    PRIMARY KEY (`uuid`)
);
