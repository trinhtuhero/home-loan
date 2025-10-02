create TABLE IF NOT EXISTS `loan_upload_files`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `file_config_id`       VARCHAR(36) NOT NULL,
    `file_name`       VARCHAR(250) NULL,
    `folder`       VARCHAR(250) NULL,
    PRIMARY KEY (`uuid`)
);
