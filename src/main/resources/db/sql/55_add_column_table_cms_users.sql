alter table `cms_users`
ADD COLUMN `created_by` VARCHAR(10) NULL,
ADD COLUMN `updated_by` VARCHAR(10) NULL;
