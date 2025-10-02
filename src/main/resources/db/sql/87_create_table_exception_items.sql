create TABLE IF NOT EXISTS `exception_items`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `credit_appraisal_id` VARCHAR(36) NOT NULL,
    `criterion_group_1` VARCHAR(50) NULL,
    `criteria_group_2` VARCHAR(50) NULL,
    `detail` VARCHAR(250) NULL,
    `regulation` VARCHAR(250) NULL,
    `recommendation` VARCHAR(2000) NULL,

    PRIMARY KEY (`uuid`)
);
