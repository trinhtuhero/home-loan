create TABLE IF NOT EXISTS `asset_evaluate_item`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `asset_eval_id`       VARCHAR(36) NOT NULL,
    `asset_type`     VARCHAR(20) NOT NULL,
    `legal_record`     VARCHAR(100) NOT NULL,
    `value`     BIGINT(30) NOT NULL,
    `asset_description`     VARCHAR(250) NOT NULL,
    PRIMARY KEY (`uuid`, `asset_eval_id`)
);