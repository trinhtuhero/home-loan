alter table `exception_items`
MODIFY COLUMN detail     TEXT NULL,
MODIFY COLUMN recommendation     TEXT NULL,
MODIFY COLUMN `regulation`     TEXT NULL;

alter table `credit_appraisal`
MODIFY COLUMN business_review     TEXT NULL;