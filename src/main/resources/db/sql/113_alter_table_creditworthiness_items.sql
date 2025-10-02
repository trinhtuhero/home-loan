alter table `creditworthiness_items`
ADD COLUMN `interest_rate` DOUBLE NULL,
ADD COLUMN `first_period` int NULL,
ADD COLUMN `remaining_period` int NULL,
ADD COLUMN `first_limit` BIGINT(30) NULL,
ADD COLUMN `debt_payment_method` VARCHAR(20) NULL;
