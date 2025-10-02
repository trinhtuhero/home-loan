create TABLE IF NOT EXISTS `feedback_suggestion_map`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `feedback_id` VARCHAR(36)  NOT NULL,
    `suggestion_comment_id` VARCHAR(36)  NOT NULL,
    PRIMARY KEY (`uuid`)
);
