ALTER TABLE `salary_incomes`
ADD COLUMN `office_province_name` VARCHAR(250) AFTER `office_province`,
ADD COLUMN `office_district_name` VARCHAR(250) AFTER `office_district`,
ADD COLUMN `office_ward_name` VARCHAR(250) AFTER `office_ward`;
