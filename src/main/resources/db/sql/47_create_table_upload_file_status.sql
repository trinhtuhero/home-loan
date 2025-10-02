create TABLE IF NOT EXISTS `upload_file_status`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NULL,
    `file_config_id`       VARCHAR(36) NULL,
    `status`    VARCHAR(20) NULL,
    `record_status`    VARCHAR(20) NULL,
    `empl_id`    VARCHAR(10) NULL,
    PRIMARY KEY (uuid)
);
