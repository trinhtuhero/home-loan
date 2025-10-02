ALTER TABLE loan_applications
ADD COLUMN  `cj4_send_lead_status` VARCHAR(45) NULL,
DROP COLUMN cj4_cms_send_lead_status
;
