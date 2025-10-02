ALTER TABLE loan_applications
MODIFY COLUMN  `cj4_interested_date`       DATETIME NULL,
ADD COLUMN  `cj4_cms_interested`    VARCHAR(45) NULL,
ADD COLUMN  `cj4_cms_interested_date` DATETIME NULL,
ADD COLUMN  `cj4_cms_send_lead_status` VARCHAR(45) NULL
;