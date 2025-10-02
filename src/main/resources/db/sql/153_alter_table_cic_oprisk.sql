alter table `cic_item`
ADD COLUMN `check_date`     TIMESTAMP(6) NULL;

UPDATE `cic_item`
SET
    `check_date` = `updated_at`;

alter table `op_risk`
ADD COLUMN `check_date`     TIMESTAMP(6) NULL;

UPDATE `op_risk`
SET
    `check_date` = `updated_at`;


