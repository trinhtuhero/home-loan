create TABLE IF NOT EXISTS `partner_channel`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_id`       VARCHAR(36) NOT NULL,
    `deal_assignee`       VARCHAR(100) NULL,
    `deal_reference_code`    VARCHAR(100) NULL,
    `deal_referral_channel`     VARCHAR(100) NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX partner_channel_loan_id_idx
ON partner_channel (loan_id);