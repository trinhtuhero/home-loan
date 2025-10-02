alter table `collaterals`
ADD COLUMN `pricing_date` DATETIME NULL,
ADD COLUMN `next_pricing_date` DATETIME NULL,
ADD COLUMN `officer_name_pricing` VARCHAR(250) NULL;
