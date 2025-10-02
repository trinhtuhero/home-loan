create TABLE IF NOT EXISTS `loan_pre_approval`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_id`        VARCHAR(36) NOT NULL,
    `meta_data`      BLOB NULL,
    PRIMARY KEY (`loan_id`)
);

create index loan_pre_approval_loan_id_idx
on loan_pre_approval (loan_id);