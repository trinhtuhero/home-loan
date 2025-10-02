create TABLE IF NOT EXISTS `credit_card`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_id`        VARCHAR(36) NOT NULL,
    `rank`       VARCHAR(30) NULL,
    `type`    VARCHAR(30) NULL,
    `credit_limit`    BIGINT(30) NULL,
    `object`       VARCHAR(30) NULL,
    `card_policy_code`     VARCHAR(25) NULL,
    `time_limit`    INT(3) NULL,
    `first_primary_school`    VARCHAR(250) NULL,
    `auto_debit`    TINYINT NULL,
    `debt_deduction_rate`    VARCHAR(30) NULL,
    `debit_account_number`    VARCHAR(50) NULL,
    `receiving_address`    VARCHAR(500) NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX credit_card_loan_id_idx
ON credit_card (loan_id);