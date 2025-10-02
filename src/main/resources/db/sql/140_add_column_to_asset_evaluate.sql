alter table `asset_evaluate`
ADD COLUMN `owner_type`     VARCHAR(20) NULL,
ADD COLUMN `payer_id` VARCHAR(36) NULL;
