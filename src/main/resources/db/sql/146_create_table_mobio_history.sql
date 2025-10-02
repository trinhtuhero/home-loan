create TABLE IF NOT EXISTS `mobio_history`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `mobio_status`  VARCHAR(20)  NULL,
    `loan_status`  VARCHAR(30)  NULL,
    `request`  TEXT  NULL,
    `response`  TEXT  NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX mobio_history_loan_application_id_idx
ON mobio_history (loan_application_id);
