alter table `asset_evaluate`
MODIFY COLUMN total_value     BIGINT(30) NULL,
MODIFY COLUMN debit_balance     BIGINT(30) NULL,
MODIFY COLUMN income_value     BIGINT(30) NULL,
MODIFY COLUMN rm_input_value     BIGINT(30) NULL;