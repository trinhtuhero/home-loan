create TABLE IF NOT EXISTS `collateral_owner`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `loan_id`        VARCHAR(36) NOT NULL,
    `full_name`       VARCHAR(150) NULL,
    `gender`    VARCHAR(20) NULL,
    `birthday`    DATETIME NULL,
    `phone`       VARCHAR(50) NULL,
    `email`     VARCHAR(100) NULL,
    `age`    INT(3) NULL,
    `education_level`    VARCHAR(50) NULL,
    `id_no`     VARCHAR(20) NULL,
    `issued_on`     VARCHAR(100) NULL,
    `place_of_issue`     VARCHAR(100) NULL,
    `old_id_no`     VARCHAR(20) NULL,
    `province`     VARCHAR(30),
    `province_name`     VARCHAR(250),
    `district`     VARCHAR(30),
    `district_name`     VARCHAR(250),
    `ward`     VARCHAR(30),
    `ward_name`     VARCHAR(250),
    `address`     VARCHAR(250) NULL,
    `contact_province`     VARCHAR(30),
    `contact_province_name`     VARCHAR(250),
    `contact_district`     VARCHAR(30),
    `contact_district_name`     VARCHAR(250),
    `contact_ward`     VARCHAR(30),
    `contact_ward_name`     VARCHAR(250),
    `contact_address`     VARCHAR(250) NULL,
    `marital_status`     VARCHAR(20) NULL,
    `relationship`     VARCHAR(30) NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX collateral_owner_loan_id_idx
ON collateral_owner (loan_id);