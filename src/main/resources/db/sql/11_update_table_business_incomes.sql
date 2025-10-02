ALTER TABLE `business_incomes`
ADD COLUMN `province_name` VARCHAR(250) AFTER `province`,
ADD COLUMN `district_name` VARCHAR(250) AFTER `district`,
ADD COLUMN `ward_name` VARCHAR(250) AFTER `ward`;
