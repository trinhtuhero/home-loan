alter table `file_config`
ADD COLUMN `zip_prefix` VARCHAR(100) NULL AFTER `order_number`;
