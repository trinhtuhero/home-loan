create TABLE IF NOT EXISTS `pic_rm_history`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `loan_application_id`         VARCHAR(36)  NOT NULL,
    `pic_from`         VARCHAR(150) NULL,
    `pic_to`         VARCHAR(150) NULL,
    `_system`         VARCHAR(150) NULL,
    `message_id`         VARCHAR(36) NULL,
    PRIMARY KEY (`uuid`)
);
