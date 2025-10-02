create TABLE IF NOT EXISTS `shedlock`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `name`       VARCHAR(64),
    `lock_until`  DATETIME NULL,
    `locked_at`    DATETIME NULL,
    `locked_by`   VARCHAR(255),
    PRIMARY KEY (`name`)
);
