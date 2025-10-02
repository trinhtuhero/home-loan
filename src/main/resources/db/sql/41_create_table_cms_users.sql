create TABLE IF NOT EXISTS `cms_users`
(
    `empl_id`         VARCHAR(10)  NOT NULL,
    `full_name`       VARCHAR(150) NOT NULL,
    `email`     VARCHAR(100) NOT NULL,
    `branch_code`     VARCHAR(250),
    `branch_name`     VARCHAR(250),
    `leader_empl_id`         VARCHAR(10),
    `phone`       VARCHAR(50),
    `area`     VARCHAR(250),
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    PRIMARY KEY (`empl_id`)
);
