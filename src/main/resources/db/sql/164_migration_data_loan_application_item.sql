insert into loan_application_item(uuid, loan_application_id, product_text_code, loan_purpose, loan_amount) 
select uuid, loan_id, 'CREDIT_CARD', 'CREDIT_CARD', credit_limit from credit_card where card_priority = 'PRIMARY_CARD';

CREATE INDEX loan_application_item_loan_id_idx
ON loan_application_item (loan_application_id);

CREATE INDEX loan_applications_loan_code_idx
ON loan_applications (loan_code);