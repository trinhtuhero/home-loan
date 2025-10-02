create TABLE IF NOT EXISTS `m_value_asset_info`
(
    `uuid`         VARCHAR(36)  NOT NULL,
    `created_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    `updated_at` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    `m_value_id`         VARCHAR(36) NULL,
    `asset_id_m_value`         BIGINT(20) NULL,
    `valuation_notice`         VARCHAR(255) NULL,
    `ownership_certificate_no`         TEXT NULL,
    `issued_date`         DATETIME NULL,
    `issued_by`         VARCHAR(500) NULL,
    `province_id`         BIGINT(20) NULL,
    `district_id`         BIGINT(20) NULL,
    `ward_id`         BIGINT(20) NULL,
    `street_name`         VARCHAR(500) NULL,
    `address`         VARCHAR(500) NULL,
    `file_link`         TEXT NULL,
    `valuer`         VARCHAR(255) NULL,
    `valuation_date`         DATETIME NULL,
    PRIMARY KEY (`uuid`)
);
