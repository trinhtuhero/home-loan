alter table `loan_application_item`
ADD COLUMN `beneficiary_bank`     VARCHAR(150) NULL,
ADD COLUMN `beneficiary_account` VARCHAR(150) NULL,
ADD COLUMN `beneficiary_full_name` VARCHAR(150) NULL,
ADD COLUMN `disbursement_method_other` TEXT NULL;
