alter table `loan_applications`
ADD COLUMN  `interest_code`     VARCHAR(25) NULL,
ADD COLUMN  `document_number_2`     VARCHAR(150) NULL,
ADD COLUMN  `officer_code`     VARCHAR(150) NULL;