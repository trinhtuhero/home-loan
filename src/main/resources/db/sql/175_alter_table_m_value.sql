alter table `m_value`
ADD COLUMN `meta_data`     TEXT NULL;

alter table `m_value_asset_info`
ADD COLUMN `status`     VARCHAR(100) NULL;