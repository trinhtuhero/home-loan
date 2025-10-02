alter table `business_incomes`
ADD COLUMN `business_activity`     VARCHAR(250) NULL,
ADD COLUMN `issued_date`     DATETIME NULL,
ADD COLUMN `place_of_issue`     VARCHAR(100) NULL,
ADD COLUMN `capital_ratio`     DECIMAL NULL,
ADD COLUMN `ownership_type`     VARCHAR(20) NULL,
ADD COLUMN `production_process`     VARCHAR(250) NULL,
ADD COLUMN `recording_method`     VARCHAR(250) NULL,
ADD COLUMN `input_source`     VARCHAR(100) NULL,
ADD COLUMN `output_source`     VARCHAR(100) NULL,
ADD COLUMN `business_scale`     VARCHAR(100) NULL,
ADD COLUMN `inventory_receivable_payable`     VARCHAR(250) NULL,
ADD COLUMN `evaluation_period`     VARCHAR(20) NULL,
ADD COLUMN `revenue`     INT NULL,
ADD COLUMN `cost`     INT NULL,
ADD COLUMN `profit`     INT NULL,
ADD COLUMN `profit_margin`     DECIMAL NULL,
ADD COLUMN `profits_yourself`     INT NULL,
ADD COLUMN `business_performance`     VARCHAR(250) NULL;



