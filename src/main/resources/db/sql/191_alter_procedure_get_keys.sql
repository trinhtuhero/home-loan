#
CREATE PROCEDURE `get_keys`(loan varchar(36))
BEGIN
select
case when (select count(1) from loan_applications where uuid = loan and marital_status = 'MARRIED') > 0 then true
else false
end as marital_status_married,
case when (select count(1) from loan_applications where uuid = loan and marital_status = 'SINGLE') > 0 then true
else false
end as marital_status_single,
case when (select count(1) from loan_applications where uuid = loan and marital_status = 'DIVORCE') > 0 then true
else false
end as marital_status_divorce,
case when (select count(1) from loan_applications where uuid = loan and marital_status = 'WIDOW') > 0 then true
else false
end as marital_status_widow,
case when (select count(1) from loan_payers where loan_application_id = loan) > 0 then true
else false
end as has_payer,
case when (select count(1) from loan_payers where loan_application_id = loan and marital_status = 'MARRIED') > 0 then true
else false
end as payer_marital_married,
case when (select count(1) from loan_payers where loan_application_id = loan and marital_status = 'SINGLE') > 0 then true
else false
end as payer_marital_single,
case when (select count(1) from loan_payers where loan_application_id = loan and marital_status = 'DIVORCE') > 0 then true
else false
end as payer_marital_divorce,
case when (select count(1) from loan_payers where loan_application_id = loan and marital_status = 'WIDOW') > 0 then true
else false
end as payer_marital_widow,
case when (select count(1) from salary_incomes where loan_application_id = loan) > 0 then true
else false
end as has_salary_income,
case when (select count(1) from salary_incomes where loan_application_id = loan and payment_method = 'OTHERS') > 0 then true
else false
end as payment_method_other,
case when (select count(1) from salary_incomes where loan_application_id = loan and payment_method = 'CASH') > 0 then true
else false
end as payment_method_cash,
case when (select count(1) from business_incomes where loan_application_id = loan) > 0 then true
else false
end as has_business_income,
case when (select count(1) from business_incomes where loan_application_id = loan and business_type = 'ENTERPRISE') > 0 then true
else false
end as business_type_enterprise,
case when (select count(1) from business_incomes where loan_application_id = loan and business_type = 'WITH_REGISTRATION') > 0 then true
else false
end as business_type_with_registration,
case when (select count(1) from business_incomes where loan_application_id = loan and business_type in ('WITH_REGISTRATION', 'WITHOUT_REGISTRATION')) > 0 then true
else false
end as business_type_other,
case when (select count(1) from collaterals where loan_application_id = loan and province in ('01', '79') and type = 'ND' and location != 'OTHERS') > 0 then true
else false
end as collateral_province_name_hn_or_hcm,
case when (select count(1) from collaterals where loan_application_id = loan and status in ('ME', 'COUPLE')) > 0 then true
else false
end as collateral_status_me_or_couple,
case when (select count(1) from collaterals where loan_application_id = loan and status in ('THIRD_PARTY', 'OTHERS')) > 0 then true
else false
end as collateral_status_3rd_or_other,
case when (select count(1) from other_incomes where loan_application_id = loan) > 0 then true
else false
end as has_other_income,
case when (select count(1) from loan_application_item where loan_application_id = loan and loan_purpose = 'LAND') > 0 then true
else false
end as loan_purpose_land,
case when (select count(1) from loan_application_item where loan_application_id = loan and loan_purpose = 'TIEU_DUNG') > 0 then true
else false
end as loan_purpose_tieu_dung,
case when (select count(1) from loan_application_item where loan_application_id = loan and loan_purpose = 'XAY_SUA_NHA') > 0 then true
else false
end as loan_purpose_xay_sua_nha,
case when (select count(1) from collaterals where loan_application_id = loan and type in ('ND', 'CC')) > 0 then true
else false
end as collateral_type_nd_or_cc,
case when (select count(1) from collaterals where loan_application_id = loan and type = 'PTVT') > 0 then true
else false
end as collateral_type_ptvt,
case when (select count(1) from collaterals where loan_application_id = loan and type = 'GTCG') > 0 then true
else false
end as collateral_type_gtcg
;
END
#
