create TABLE IF NOT EXISTS `field_survey_items`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `credit_appraisal_id` VARCHAR(36) NOT NULL,
    `relationship_type`  VARCHAR(50) NULL,
    `_time` DATETIME NULL,
    `location_type` VARCHAR(50) NULL,
    `field_guide_person` VARCHAR(150) NULL,
    `province` VARCHAR(50) NULL,
    `province_name` VARCHAR(250) NULL,
    `district` VARCHAR(50) NULL,
    `district_name` VARCHAR(250) NULL,
    `ward` VARCHAR(50) NULL,
    `ward_name` VARCHAR(250) NULL,
    `address` VARCHAR(250) NULL,
    `evaluation_result` VARCHAR(50) NULL,

    PRIMARY KEY (`uuid`)
);
