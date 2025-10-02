ALTER TABLE `loan_applications`
ADD COLUMN `old_id_no_2` VARCHAR(12) NULL,
ADD COLUMN `old_id_no_3` VARCHAR(12) NULL;

ALTER TABLE `loan_payers`
ADD COLUMN `old_id_no_2` VARCHAR(12) NULL,
ADD COLUMN `old_id_no_3` VARCHAR(12) NULL;

ALTER TABLE `married_persons`
ADD COLUMN `old_id_no_2` VARCHAR(12) NULL,
ADD COLUMN `old_id_no_3` VARCHAR(12) NULL;