alter table `loan_applications`
ADD COLUMN `debt_payment_method`     VARCHAR(15) NULL,
ADD COLUMN `grace_period`     INT(3) NULL,
ADD COLUMN `program_interest_rates`     VARCHAR(25) NULL,
ADD COLUMN `product_text_code`     VARCHAR(10) NULL,
ADD COLUMN `disbursement_method`     VARCHAR(20) NULL,
ADD COLUMN `rm_review`     VARCHAR(255) NULL;



