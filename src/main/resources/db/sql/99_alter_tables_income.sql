alter table `salary_incomes`
ADD COLUMN `payer_id` VARCHAR(36) NULL;

alter table `business_incomes`
ADD COLUMN `payer_id` VARCHAR(36) NULL;

alter table `other_incomes`
ADD COLUMN `payer_id` VARCHAR(36) NULL;

alter table `other_evaluate`
ADD COLUMN `payer_id` VARCHAR(36) NULL;