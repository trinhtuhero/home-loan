alter table `op_risk`
MODIFY COLUMN  `pass`       TINYINT(1) NULL,
ADD COLUMN `status` VARCHAR(15) NULL;