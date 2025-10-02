create TABLE IF NOT EXISTS `mobio_noti_consume_message_log`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `topic` VARCHAR(500) NULL,
    `_partition` INT NULL,
    `offset` BIGINT NULL,
    `_key` VARCHAR(500) NULL,
    `value` TEXT NULL,
    `timestamp` BIGINT NULL,
    `timestamp_type` VARCHAR(50) NULL,
    `consumer_record` TEXT NULL,
    PRIMARY KEY (`uuid`)
);
