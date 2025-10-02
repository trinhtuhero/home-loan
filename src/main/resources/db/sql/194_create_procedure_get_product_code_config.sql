#
CREATE PROCEDURE `get_product_code_config`(loan_id varchar(36), loan_item_id varchar(36))
BEGIN
select loan_purpose_detail, loan_supplementing_business_capital, is_private_business_owner, refinance_loan, loan_time,
(select approval_flow from common_income where loan_application_id = loan_id) as approval_flow,
(select recognition_method_1 from common_income where loan_application_id = loan_id) as recognition_method_1
from loan_application_item where uuid = loan_item_id;
END
#
