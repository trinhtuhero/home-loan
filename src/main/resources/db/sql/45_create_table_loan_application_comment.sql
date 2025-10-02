create TABLE IF NOT EXISTS `loan_application_comment`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NULL,
    `code`       VARCHAR(50) NULL,
    `comment`    VARCHAR(500)   NULL,
    `status`    VARCHAR(20) NULL,
    `empl_id`    VARCHAR(10) NULL,
    PRIMARY KEY (uuid)
);
