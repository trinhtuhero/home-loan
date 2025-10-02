#
CREATE PROCEDURE `get_cms_keys`(loan varchar(36))
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
case when (select count(1) from contact_persons where loan_application_id = loan) > 0 then true
else false
end as has_contact_person,
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
true as collateral_province_name_hn_or_hcm,
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
end as collateral_type_gtcg,

case when (select count(1) from loan_application_item where loan_application_id = loan and loan_purpose = 'HO_KINH_DOANH') > 0 then true
else false
end as loan_purpose_ho_kinh_doanh,
case when (select count(1) from loan_application_item where loan_application_id = loan and loan_purpose_detail = 'VAY_BO_SUNG_VON_KINH_DOANH') > 0 then true
else false
end as vay_bo_sung_von_kinh_doanh,
case when (select count(1) from loan_application_item where loan_application_id = loan and loan_investment_fixed_asset = 'VAY_MUA_BĐS_CO_SAN') > 0 then true
else false
end as vay_mua_bđs_co_san,
case when (select count(1) from loan_application_item where loan_application_id = loan and loan_investment_fixed_asset = 'VAY_HOAN_VON_MUA_BĐS') > 0 then true
else false
end as vay_hoan_von_mua_bđs,
case when (select count(1) from loan_application_item where loan_application_id = loan and loan_investment_fixed_asset = 'VAY_MUA_BĐS_QUA_DAU_GIA_PHAT_MAI') > 0 then true
else false
end as vay_mua_bđs_qua_dau_gia_phat_mai,
case when (select count(1) from loan_application_item where loan_application_id = loan and loan_investment_fixed_asset = 'MUA_PTVT_MMTB_TSCĐ_PHUC_VU_KD') > 0 then true
else false
end as mua_ptvt_mmtb_tscđ_phuc_vu_kd,
case when (select count(1) from loan_application_item where loan_application_id = loan and loan_investment_fixed_asset = 'XD_SUA_CHUA_NHA_XUONG_BĐS_KD') > 0 then true
else false
end as xd_sua_chua_nha_xuong_bđs_kd,
case when (select count(1) from loan_application_item where loan_application_id = loan and loan_investment_fixed_asset = 'VAY_HOAN_VON_XD_SUA_CHUA_NHA_XUONG_BĐS_KD') > 0 then true
else false
end as abc
;
END
#
