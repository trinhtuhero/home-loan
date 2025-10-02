create table IF NOT EXISTS `overdraft`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `loan_application_id`   VARCHAR(36) NOT NULL,
    `overdraft_purpose`   VARCHAR(150) NULL,
    `loan_amount`   BIGINT(20) NULL,
    `overdraft_subject`   VARCHAR(150) NULL,
    `form_of_credit`   VARCHAR(150) NULL,
    `loan_time`   INT(3) NULL,
    `interest_code`  VARCHAR(150)  NULL,
    `margin`       DECIMAL(5,2) NULL,
    `payment_account_number`    VARCHAR(150) NULL,

    `debt_payment_method`  VARCHAR(150) NULL,
    `loan_asset_value`  BIGINT(20) NULL,
    `product_text_code`  VARCHAR(150) NULL,
    `document_number_2`  VARCHAR(150)  NULL,

    `created_at` timestamp(6) default CURRENT_TIMESTAMP(6) null,
    `updated_at` timestamp(6) default CURRENT_TIMESTAMP(6) null,
    PRIMARY KEY (`uuid`)
);
