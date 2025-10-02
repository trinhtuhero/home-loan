/*
--disbursement_method
*/
update loan_application_item set disbursement_method_other = disbursement_method;
update loan_application_item set disbursement_method = 'OTHER';

/*
--officer_code
*/
insert into credit_appraisal(uuid, loan_application_id, officer_code)
(select UUID(), loan_application_id, officer_code from loan_application_item where officer_code is not null and loan_application_id not in (select loan_application_id from credit_appraisal));

UPDATE credit_appraisal AS ca, loan_application_item AS lai
SET ca.officer_code = lai.officer_code
WHERE ca.loan_application_id = lai.loan_application_id;

/*
--common_income
*/
update common_income set approval_flow = 'NORMALLY', recognition_method_1 = 'ACTUALLY_RECEIVED', selected_incomes = 'SALARY_INCOME, PERSONAL_BUSINESS_INCOME, BUSINESS_INCOME, OTHERS_INCOME' where recognition_method_1 is null;
update common_income set selected_incomes = 'SALARY_INCOME, PERSONAL_BUSINESS_INCOME, BUSINESS_INCOME, OTHERS_INCOME' where recognition_method_1 in ('ACTUALLY_RECEIVED', 'DECLARATION');
update common_income set selected_incomes = 'SALARY_INCOME, PERSONAL_BUSINESS_INCOME, BUSINESS_INCOME, OTHERS_INCOME, ASSUMING_TOTAL_ASSETS_INCOME, ASSUMING_OTHERS_INCOME' where recognition_method_1 = 'EXCHANGE';

/*
--loan_item_collateral_distribution
*/
insert into loan_item_collateral_distribution(uuid, loan_item_id, collateral_id, percent)
(select UUID(), lai.uuid, c.uuid, 100 from loan_application_item lai join collaterals c on lai.loan_application_id = c.loan_application_id);

/*
--mobio status
*/
update loan_applications set mobio_status = 'WAITING' where status in ('SUBMIT_LOAN_REQUEST', 'SUBMIT_LOAN_APPLICATION');
