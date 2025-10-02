create TABLE IF NOT EXISTS `common_income`
(
    `created_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    `uuid`       VARCHAR(36) NOT NULL,
    `loan_application_id`       VARCHAR(36) NOT NULL,
    `approval_flow`       VARCHAR(30) NULL,
    `recognition_method_1`       VARCHAR(20) NULL,
    `recognition_method_2`       VARCHAR(50) NULL,
    `selected_incomes`       VARCHAR(500) NULL,
    PRIMARY KEY (`uuid`)
);

CREATE INDEX common_income_loan_application_id_idx
ON common_income (loan_application_id);

insert into common_income(uuid, loan_application_id, approval_flow, recognition_method_1, recognition_method_2, selected_incomes)
select UUID(), uuid, approval_flow, recognition_method_1, recognition_method_2, selected_incomes from loan_applications;

ALTER TABLE loan_applications
DROP COLUMN approval_flow,
DROP COLUMN recognition_method_1,
DROP COLUMN recognition_method_2,
DROP COLUMN selected_incomes;
