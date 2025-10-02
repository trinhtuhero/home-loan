create TABLE IF NOT EXISTS `married_persons`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `full_name`     VARCHAR(150) NULL,
    `gender`     VARCHAR(20) NULL,
    `id_no`     VARCHAR(20) NULL,
    `issued_on`     DATETIME NULL,
    `place_of_issue`     VARCHAR(50) NULL,
    `old_id_no`     VARCHAR(20) NULL,
    `phone`     VARCHAR(20) NULL,
    `email`     VARCHAR(20) NULL,
    `nationality`     VARCHAR(50) NULL,
    `province`     VARCHAR(50) NULL,
    `province_name`     VARCHAR(200) NULL,
    `district`     VARCHAR(20) NULL,
    `district_name`     VARCHAR(200) NULL,
    `ward`     VARCHAR(20) NULL,
    `ward_name`     VARCHAR(200) NULL,
    `address`     VARCHAR(255) NULL,
    PRIMARY KEY (`uuid`)
);