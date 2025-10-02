alter table `business_incomes`
MODIFY COLUMN revenue     BIGINT(30) NULL,
MODIFY COLUMN cost     BIGINT(30) NULL,
MODIFY COLUMN profit     BIGINT(30) NULL,
MODIFY COLUMN profits_yourself     BIGINT(30) NULL;