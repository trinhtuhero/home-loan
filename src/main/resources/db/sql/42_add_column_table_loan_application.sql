alter table `loan_applications`
ADD COLUMN `receive_date` DATETIME NULL,
ADD COLUMN `receive_channel` VARCHAR(50) NULL,
ADD COLUMN `loan_product` VARCHAR(50) NULL;
