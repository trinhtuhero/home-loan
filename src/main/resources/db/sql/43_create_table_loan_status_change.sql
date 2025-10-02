create TABLE IF NOT EXISTS `loan_status_change`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `loan_application_id`     VARCHAR(36) NULL,
    `status_from`     VARCHAR(30) NULL,
    `status_to`     VARCHAR(30) NULL,
    `cause`     VARCHAR(50) NULL,
    `note`     VARCHAR(200) NULL,
    `empl_id`     VARCHAR(36) NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    PRIMARY KEY (`uuid`)
    );