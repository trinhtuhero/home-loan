create TABLE IF NOT EXISTS `suggestion_comment`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `rate_from`         TINYINT NOT NULL,
    `rate_to`         TINYINT NOT NULL,
    `comment`         VARCHAR(500)  NOT NULL,
    `status`         TINYINT(1)  NOT NULL,
    PRIMARY KEY (`uuid`)
);
