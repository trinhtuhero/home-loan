/*
-- drop officer_code on loan_application_item
*/

ALTER TABLE loan_application_item
DROP COLUMN officer_code;

/*
--loan_item_collateral_distribution
*/
CREATE INDEX loan_item_collateral_distribution_idx1
ON loan_item_collateral_distribution (loan_item_id);

/*
--loan_pre_approval
*/
alter table `loan_pre_approval`
MODIFY COLUMN `meta_data`     MEDIUMBLOB NULL;
