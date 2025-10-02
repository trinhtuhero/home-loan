alter table `collaterals`
ADD COLUMN  `type_of_doc`   VARCHAR(50) NULL,
ADD COLUMN  `chassis_no`   VARCHAR(250) NULL,
ADD COLUMN  `engine_no`   VARCHAR(250) NULL,
ADD COLUMN  `duration_of_used`   INTEGER NULL,
ADD COLUMN  `status_using`   VARCHAR(50) NULL,
ADD COLUMN  `asset_description`   TEXT NULL,
ADD COLUMN  `account_number`   VARCHAR(250) NULL,
ADD COLUMN  `saving_book_no`   VARCHAR(250) NULL,
ADD COLUMN  `maturity_date`   DATETIME NULL,
ADD COLUMN  `issued_branch`   VARCHAR(500) NULL,
ADD COLUMN  `interest_rate`   DECIMAL(5,2) NULL,
ADD COLUMN  `registration_or_contract_no`   VARCHAR(250) NULL
;
