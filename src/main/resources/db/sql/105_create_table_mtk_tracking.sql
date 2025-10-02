create TABLE IF NOT EXISTS `mtk_tracking`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `uuid`        VARCHAR(36) NOT NULL,
    `loan_id`        VARCHAR(36) NOT NULL,
    `loan_code`        VARCHAR(50) NOT NULL,
    `utm_source`        VARCHAR(250) NOT NULL,
    `utm_medium`      VARCHAR(250) NOT NULL,
    `utm_campaign`      VARCHAR(250) NOT NULL,
    `utm_content`      VARCHAR(250) NOT NULL,
    PRIMARY KEY (`uuid`)
);

create index mtk_tracking_loan_id
on mtk_tracking (loan_id);

create index mtk_tracking_loan_code
on mtk_tracking (loan_code);