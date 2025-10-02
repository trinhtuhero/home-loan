alter table `loan_applications`
ADD COLUMN `current_step` VARCHAR(50) NULL AFTER `status`;
