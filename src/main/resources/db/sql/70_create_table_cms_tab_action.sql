create TABLE IF NOT EXISTS `cms_tab_action`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `uuid`        VARCHAR(36) NOT NULL,
    `loan_id`        VARCHAR(36) NOT NULL,
    `empl_id`        VARCHAR(50) NOT NULL,
    `tab_code`        VARCHAR(50) NOT NULL,
    `status`      VARCHAR(50) NOT NULL,
    PRIMARY KEY (`uuid`)
);

create index cms_tab_action_loan_id_idx
on cms_tab_action (loan_id);