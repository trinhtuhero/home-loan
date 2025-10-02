alter table `collaterals`
ADD COLUMN `m_value_id`     VARCHAR(100) NULL,
ADD COLUMN `core_id`     VARCHAR(100) NULL,
ADD COLUMN `valuation_cert`     VARCHAR(250) NULL,
ADD COLUMN `asset_category`     VARCHAR(30) NULL,
ADD COLUMN `legal_doc`     VARCHAR(100) NULL,
ADD COLUMN `doc_issued_on`     DATETIME NULL,
ADD COLUMN `doc_place_of_issue`     VARCHAR(30) NULL,
ADD COLUMN `guaranteed_value`     BIGINT(20) NULL,
ADD COLUMN `ltv_rate`     INT(3) NULL;
