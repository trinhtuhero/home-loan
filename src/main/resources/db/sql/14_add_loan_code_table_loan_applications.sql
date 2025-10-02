alter table `loan_applications`
ADD COLUMN `loan_code` VARCHAR(50) NULL AFTER `number_of_dependents`;
