alter table `other_incomes`
ADD COLUMN `rent_property` VARCHAR(250) NULL,
ADD COLUMN `tenant` VARCHAR(250) NULL,
ADD COLUMN `tenant_phone` VARCHAR(20) NULL,
ADD COLUMN `tenant_purpose` VARCHAR(250) NULL,
ADD COLUMN `tenant_price` BIGINT(30) NULL;