alter table `business_incomes`
ADD COLUMN `charter_capital`     BIGINT(30) NULL,
MODIFY COLUMN recording_method     TEXT NULL,
MODIFY COLUMN production_process     TEXT NULL,
MODIFY COLUMN input_source     TEXT NULL,
MODIFY COLUMN output_source     TEXT NULL,
MODIFY COLUMN business_scale     TEXT NULL,
MODIFY COLUMN inventory_receivable_payable     TEXT NULL,
MODIFY COLUMN business_performance     TEXT NULL;