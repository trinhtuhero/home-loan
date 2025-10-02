ALTER TABLE `product_code_config`
CHANGE COLUMN `product_code` `product_code` VARCHAR(100) NULL ,
CHANGE COLUMN `loan_time_from` `loan_time_from` INT NULL ,
CHANGE COLUMN `loan_time_to` `loan_time_to` INT NULL ,
CHANGE COLUMN `approval_flow` `approval_flow` VARCHAR(50) NULL ,
CHANGE COLUMN `income_method` `income_method` VARCHAR(50) NULL ,
ADD COLUMN `expression`       TEXT NULL
;
