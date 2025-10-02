create TABLE IF NOT EXISTS `loan_application_item`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `uuid`       VARCHAR(36) NOT NULL,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `debt_payment_method`  VARCHAR(20) NULL,
    `grace_period`  INT(3) NULL,
    `interest_rate_program`  VARCHAR(25)  NULL,
    `disbursement_method`  TEXT NULL,
    `rm_review`  TEXT NULL,
    `interest_code`  VARCHAR(25)  NULL,
    `product_text_code`  VARCHAR(10) NULL,
    `document_number_2`  VARCHAR(150)  NULL,
    `officer_code`  VARCHAR(150)  NULL,
    `loan_purpose`  VARCHAR(250)  NULL,
    `loan_time`  INT(3) NULL,
    `loan_asset_value`  BIGINT(20) NULL,
    `loan_amount`  BIGINT(20) NULL,
    PRIMARY KEY (`uuid`)
);
