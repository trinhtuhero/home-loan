alter table `loan_applications`
MODIFY COLUMN approval_flow     VARCHAR(30) NULL,
MODIFY COLUMN `rm_review`     TEXT NULL;