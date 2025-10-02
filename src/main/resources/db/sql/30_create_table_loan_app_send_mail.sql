create TABLE IF NOT EXISTS `loan_drop_send_mail`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_id`       VARCHAR(36) NOT NULL,
    `next_time`       DATETIME NOT NULL,
    `count`    INT(1) NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX loan_app_send_mail_loan_id_idx
ON loan_drop_send_mail (loan_id);