create TABLE IF NOT EXISTS `send_mail_log`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_id`       VARCHAR(36) NOT NULL,
    `send_mail_to`  VARCHAR(100) NOT NULL,
    `email_type`    VARCHAR(50) NOT NULL,
    `status`    VARCHAR(50) NOT NULL,
    `description`    VARCHAR(255) NOT NULL,
    `meta_data`    LONGTEXT NOT NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX send_mail_log_loan_id_idx
ON send_mail_log (loan_id);