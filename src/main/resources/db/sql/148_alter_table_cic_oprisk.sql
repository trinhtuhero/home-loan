alter table `cic_item`
ADD COLUMN `active`     TINYINT NULL;

UPDATE `cic_item`
SET
    `active` = true;

alter table `op_risk`
ADD COLUMN `active`     TINYINT NULL;

UPDATE `op_risk`
SET
    `active` = true;


