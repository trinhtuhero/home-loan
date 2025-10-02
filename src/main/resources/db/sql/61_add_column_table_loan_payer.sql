alter table `loan_payers`
ADD COLUMN `cif_no`     VARCHAR(20) NULL,
ADD COLUMN `age`     INT(2) NULL,
ADD COLUMN `education`     VARCHAR(20) NULL,
ADD COLUMN `residence_province`     VARCHAR(30) NULL,
ADD COLUMN `residence_province_name`     VARCHAR(250) NULL,
ADD COLUMN `residence_district`     VARCHAR(30) NULL,
ADD COLUMN `residence_district_name`     VARCHAR(250) NULL,
ADD COLUMN `residence_ward`     VARCHAR(30) NULL,
ADD COLUMN `residence_ward_name`     VARCHAR(250) NULL,
ADD COLUMN `residence_address`     VARCHAR(250) NULL;



