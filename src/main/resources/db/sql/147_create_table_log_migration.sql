create TABLE IF NOT EXISTS `log_migration`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `message`  TEXT  NULL,
    `stack_trace`  TEXT  NULL,
    `id` int NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`id`)
);
