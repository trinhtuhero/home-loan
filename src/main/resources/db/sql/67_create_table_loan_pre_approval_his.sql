create TABLE IF NOT EXISTS `loan_approval_his`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_id`        VARCHAR(36) NOT NULL,
    `status`        VARCHAR(50) NOT NULL,
    `meta_data_from`      BLOB NULL,
    `meta_data_to`      BLOB NULL,
    PRIMARY KEY (`loan_id`)
);

create index loan_approval_his_id_idx
on loan_approval_his (loan_id);