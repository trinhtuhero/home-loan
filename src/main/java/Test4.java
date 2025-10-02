import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.model.LoanApplicationReview;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Test4 {
    public static void main(String[] args) throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        JavaTimeModule module = new JavaTimeModule();
        objectMapper.registerModule(module);

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        LoanApplicationReview loanPreApproval = objectMapper.readValue("{\n" +
                "  \"loan_application\": {\n" +
                "    \"uuid\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "    \"profile_id\": \"b221926d-9d60-407a-a62d-ef98d670e6d0\",\n" +
                "    \"phone\": \"0936459000\",\n" +
                "    \"full_name\": \"Tet 2704\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"nationality\": \"VIETNAM\",\n" +
                "    \"nationality_name\": \"Việt Nam\",\n" +
                "    \"email\": \"tungpt2@msb.com.vn\",\n" +
                "    \"id_no\": \"093645900000\",\n" +
                "    \"issued_on\": \"2022-10-13T00:00:00.000+00:00\",\n" +
                "    \"birthday\": \"1981-10-13T00:00:00.000+00:00\",\n" +
                "    \"place_of_issue\": \"2\",\n" +
                "    \"place_of_issue_name\": \"Cục cảnh sát QLHC về TTXH\",\n" +
                "    \"old_id_no\": \"012345666\",\n" +
                "    \"province\": \"01\",\n" +
                "    \"province_name\": \"Thành phố Hà Nội\",\n" +
                "    \"district\": \"006\",\n" +
                "    \"district_name\": \"Quận Đống Đa\",\n" +
                "    \"ward\": \"00187\",\n" +
                "    \"ward_name\": \"Phường Láng Thượng\",\n" +
                "    \"address\": \"27\",\n" +
                "    \"marital_status\": \"SINGLE\",\n" +
                "    \"number_of_dependents\": 1,\n" +
                "    \"ref_code\": \"tungpt2@msb\",\n" +
                "    \"status\": \"ACCEPT_LOAN_APPLICATION\",\n" +
                "    \"current_step\": \"LOAN_CUSTOMER\",\n" +
                "    \"download_status\": 0,\n" +
                "    \"pic_rm\": \"015188\",\n" +
                "    \"receive_date\": \"2023-04-27T05:51:26Z\",\n" +
                "    \"receive_channel\": \"LDP\",\n" +
                "    \"loan_code\": \"LDP270420230015\",\n" +
                "    \"total_income\": 50000000,\n" +
                "    \"created_at\": \"2023-04-27T05:48:15.261Z\",\n" +
                "    \"updated_at\": \"2023-04-27T06:36:03.565958Z\",\n" +
                "    \"mobio_status\": \"WAITING\"\n" +
                "  },\n" +
                "  \"contact_person\": {\n" +
                "    \"uuid\": \"34416f7d-d57a-463d-b73e-0f38505a93e5\",\n" +
                "    \"loan_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "    \"type\": \"FRIEND\",\n" +
                "    \"full_name\": \"Bạn tet\",\n" +
                "    \"phone\": \"0972727273\"\n" +
                "  },\n" +
                "  \"other_incomes\": [\n" +
                "    {\n" +
                "      \"uuid\": \"41b54eef-6cc0-4123-8e9f-2a98dbf8c412\",\n" +
                "      \"loan_application_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "      \"owner_type\": \"ME\",\n" +
                "      \"income_from\": \"OTHERS\",\n" +
                "      \"value\": 50000000,\n" +
                "      \"rm_review\": null,\n" +
                "      \"payer_id\": null,\n" +
                "      \"payer_name\": null,\n" +
                "      \"rent_property\": null,\n" +
                "      \"tenant\": null,\n" +
                "      \"tenant_phone\": null,\n" +
                "      \"tenant_purpose\": null,\n" +
                "      \"tenant_price\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"collaterals\": [\n" +
                "    {\n" +
                "      \"uuid\": \"4e0a81db-b47a-489e-9cbc-76e5212b8ce6\",\n" +
                "      \"loan_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "      \"type\": \"ND\",\n" +
                "      \"status\": \"ME\",\n" +
                "      \"full_name\": null,\n" +
                "      \"relationship\": null,\n" +
                "      \"province\": \"02\",\n" +
                "      \"province_name\": \"Tỉnh Hà Giang\",\n" +
                "      \"district\": \"026\",\n" +
                "      \"district_name\": \"Huyện Đồng Văn\",\n" +
                "      \"ward\": \"00715\",\n" +
                "      \"ward_name\": \"Xã Lũng Cú\",\n" +
                "      \"address\": null,\n" +
                "      \"value\": null,\n" +
                "      \"location\": null,\n" +
                "      \"description\": null,\n" +
                "      \"mvalue_id\": null,\n" +
                "      \"core_id\": null,\n" +
                "      \"valuation_cert\": null,\n" +
                "      \"asset_category\": null,\n" +
                "      \"legal_doc\": null,\n" +
                "      \"doc_issued_on\": null,\n" +
                "      \"doc_place_of_issue\": null,\n" +
                "      \"doc_place_of_issue_name\": null,\n" +
                "      \"guaranteed_value\": null,\n" +
                "      \"ltv_rate\": null,\n" +
                "      \"pricing_date\": null,\n" +
                "      \"next_pricing_date\": null,\n" +
                "      \"officer_name_pricing\": null,\n" +
                "      \"type_of_doc\": null,\n" +
                "      \"chassis_no\": null,\n" +
                "      \"engine_no\": null,\n" +
                "      \"duration_of_used\": null,\n" +
                "      \"status_using\": null,\n" +
                "      \"saving_book_no\": null,\n" +
                "      \"maturity_date\": null,\n" +
                "      \"issued_branch\": null,\n" +
                "      \"interest_rate\": null,\n" +
                "      \"registration_or_contract_no\": null,\n" +
                "      \"collateral_owner_maps\": null,\n" +
                "      \"collateral_owners\": null,\n" +
                "      \"index\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"loan_application_items\": [\n" +
                "    {\n" +
                "      \"uuid\": \"92c761db-f308-4865-9257-41e0c9348efb\",\n" +
                "      \"loan_application_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "      \"debt_payment_method\": null,\n" +
                "      \"grace_period\": null,\n" +
                "      \"interest_rate_program\": null,\n" +
                "      \"product_text_code\": null,\n" +
                "      \"disbursement_method\": null,\n" +
                "      \"disbursement_method_other\": null,\n" +
                "      \"rm_review\": null,\n" +
                "      \"interest_code\": null,\n" +
                "      \"document_number2\": null,\n" +
                "      \"loan_purpose\": \"LAND\",\n" +
                "      \"loan_time\": 120,\n" +
                "      \"loan_asset_value\": 200000000,\n" +
                "      \"loan_amount\": 100000000,\n" +
                "      \"beneficiary_bank\": null,\n" +
                "      \"beneficiary_bank_name\": null,\n" +
                "      \"beneficiary_bank_short_name\": null,\n" +
                "      \"beneficiary_account\": null,\n" +
                "      \"beneficiary_full_name\": null,\n" +
                "      \"loan_purpose_detail\": null,\n" +
                "      \"loan_supplementing_business_capital\": null,\n" +
                "      \"loan_investment_fixed_asset\": null,\n" +
                "      \"debt_acknowledgment_contract_period\": null,\n" +
                "      \"is_private_business_owner\": null,\n" +
                "      \"refinance_loan\": null,\n" +
                "      \"loan_item_collateral_distributions\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"common_income\": {\n" +
                "    \"uuid\": \"8d0352a4-6e7a-4e1b-8d6b-7be26dfa34a5\",\n" +
                "    \"loan_application_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "    \"approval_flow\": \"NORMALLY\",\n" +
                "    \"recognition_method1\": \"ACTUALLY_RECEIVED\",\n" +
                "    \"recognition_method2\": null,\n" +
                "    \"selected_incomes\": [\n" +
                "      \"OTHERS_INCOME\"\n" +
                "    ]\n" +
                "  }\n" +
                "}", LoanApplicationReview.class);
        LoanApplicationReview loanPreApproval2 = objectMapper.readValue("{\n" +
                "  \"loan_application\": {\n" +
                "    \"uuid\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "    \"profile_id\": \"b221926d-9d60-407a-a62d-ef98d670e6d0\",\n" +
                "    \"phone\": \"0936459000\",\n" +
                "    \"full_name\": \"Tet 2704\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"nationality\": \"VIETNAM\",\n" +
                "    \"nationality_name\": \"Việt Nam\",\n" +
                "    \"email\": \"tungpt2@msb.com.vn\",\n" +
                "    \"id_no\": \"093645900000\",\n" +
                "    \"issued_on\": \"2022-10-13T00:00:00.000+00:00\",\n" +
                "    \"birthday\": \"1981-10-13T00:00:00.000+00:00\",\n" +
                "    \"place_of_issue\": \"2\",\n" +
                "    \"place_of_issue_name\": \"Cục cảnh sát QLHC về TTXH\",\n" +
                "    \"old_id_no\": \"012345666\",\n" +
                "    \"province\": \"01\",\n" +
                "    \"province_name\": \"Thành phố Hà Nội\",\n" +
                "    \"district\": \"006\",\n" +
                "    \"district_name\": \"Quận Đống Đa\",\n" +
                "    \"ward\": \"00187\",\n" +
                "    \"ward_name\": \"Phường Láng Thượng\",\n" +
                "    \"address\": \"27\",\n" +
                "    \"marital_status\": \"SINGLE\",\n" +
                "    \"number_of_dependents\": 1,\n" +
                "    \"ref_code\": \"tungpt2@msb\",\n" +
                "    \"status\": \"PROCESSING\",\n" +
                "    \"current_step\": \"LOAN_CUSTOMER\",\n" +
                "    \"download_status\": 1,\n" +
                "    \"pic_rm\": \"015188\",\n" +
                "    \"receive_date\": \"2023-04-27T05:51:26Z\",\n" +
                "    \"receive_channel\": \"LDP\",\n" +
                "    \"loan_code\": \"LDP270420230015\",\n" +
                "    \"total_income\": 50000000,\n" +
                "    \"age\": 42,\n" +
                "    \"residence_province\": \"01\",\n" +
                "    \"residence_province_name\": \"Thành phố Hà Nội\",\n" +
                "    \"residence_district\": \"006\",\n" +
                "    \"residence_district_name\": \"Quận Đống Đa\",\n" +
                "    \"residence_ward\": \"00187\",\n" +
                "    \"residence_ward_name\": \"Phường Láng Thượng\",\n" +
                "    \"residence_address\": \"27\",\n" +
                "    \"cif_no\": \"\",\n" +
                "    \"education\": \"UNIVERSITY\",\n" +
                "    \"customer_segment\": \"HP\",\n" +
                "    \"created_at\": \"2023-04-27T05:48:15.261Z\",\n" +
                "    \"updated_at\": \"2023-04-27T06:37:16.663501Z\",\n" +
                "    \"mobio_status\": \"SUCCESS\"\n" +
                "  },\n" +
                "  \"contact_person\": {\n" +
                "    \"uuid\": \"34416f7d-d57a-463d-b73e-0f38505a93e5\",\n" +
                "    \"loan_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "    \"type\": \"FRIEND\",\n" +
                "    \"full_name\": \"Bạn tet\",\n" +
                "    \"phone\": \"0972727273\"\n" +
                "  },\n" +
                "  \"other_incomes\": [\n" +
                "    {\n" +
                "      \"uuid\": \"41b54eef-6cc0-4123-8e9f-2a98dbf8c412\",\n" +
                "      \"loan_application_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "      \"owner_type\": \"ME\",\n" +
                "      \"income_from\": \"OTHERS\",\n" +
                "      \"value\": 50000000,\n" +
                "      \"rm_review\": null,\n" +
                "      \"payer_id\": null,\n" +
                "      \"payer_name\": null,\n" +
                "      \"rent_property\": null,\n" +
                "      \"tenant\": null,\n" +
                "      \"tenant_phone\": null,\n" +
                "      \"tenant_purpose\": null,\n" +
                "      \"tenant_price\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"collaterals\": [\n" +
                "    {\n" +
                "      \"uuid\": \"4e0a81db-b47a-489e-9cbc-76e5212b8ce6\",\n" +
                "      \"loan_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "      \"type\": \"ND\",\n" +
                "      \"status\": \"ME\",\n" +
                "      \"full_name\": null,\n" +
                "      \"relationship\": null,\n" +
                "      \"province\": \"02\",\n" +
                "      \"province_name\": \"Tỉnh Hà Giang\",\n" +
                "      \"district\": \"026\",\n" +
                "      \"district_name\": \"Huyện Đồng Văn\",\n" +
                "      \"ward\": \"00715\",\n" +
                "      \"ward_name\": \"Xã Lũng Cú\",\n" +
                "      \"address\": \"\",\n" +
                "      \"value\": 500000000,\n" +
                "      \"location\": null,\n" +
                "      \"description\": null,\n" +
                "      \"mvalue_id\": \"\",\n" +
                "      \"core_id\": \"\",\n" +
                "      \"valuation_cert\": \"\",\n" +
                "      \"asset_category\": \"RE5\",\n" +
                "      \"legal_doc\": \"2704\",\n" +
                "      \"doc_issued_on\": \"2023-04-04T00:00:00.000+00:00\",\n" +
                "      \"doc_place_of_issue\": \"02\",\n" +
                "      \"doc_place_of_issue_name\": \"Tỉnh Hà Giang\",\n" +
                "      \"guaranteed_value\": null,\n" +
                "      \"ltv_rate\": 80,\n" +
                "      \"pricing_date\": null,\n" +
                "      \"next_pricing_date\": null,\n" +
                "      \"officer_name_pricing\": \"\",\n" +
                "      \"type_of_doc\": null,\n" +
                "      \"chassis_no\": null,\n" +
                "      \"engine_no\": null,\n" +
                "      \"duration_of_used\": null,\n" +
                "      \"status_using\": null,\n" +
                "      \"saving_book_no\": null,\n" +
                "      \"maturity_date\": null,\n" +
                "      \"issued_branch\": null,\n" +
                "      \"interest_rate\": null,\n" +
                "      \"registration_or_contract_no\": null,\n" +
                "      \"collateral_owner_maps\": null,\n" +
                "      \"collateral_owners\": null,\n" +
                "      \"index\": 0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"loan_application_items\": [\n" +
                "    {\n" +
                "      \"uuid\": \"92c761db-f308-4865-9257-41e0c9348efb\",\n" +
                "      \"loan_application_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "      \"debt_payment_method\": \"DEBT_PAYMENT_1\",\n" +
                "      \"grace_period\": null,\n" +
                "      \"interest_rate_program\": \"PROGRAM_INTEREST_RATE_2\",\n" +
                "      \"product_text_code\": \"LAND\",\n" +
                "      \"disbursement_method\": \"TRANSFER\",\n" +
                "      \"disbursement_method_other\": null,\n" +
                "      \"rm_review\": \"cho vay bds\",\n" +
                "      \"interest_code\": \"6004\",\n" +
                "      \"document_number2\": null,\n" +
                "      \"loan_purpose\": \"LAND\",\n" +
                "      \"loan_time\": 120,\n" +
                "      \"loan_asset_value\": 200000000,\n" +
                "      \"loan_amount\": 100000000,\n" +
                "      \"beneficiary_bank\": null,\n" +
                "      \"beneficiary_bank_name\": null,\n" +
                "      \"beneficiary_bank_short_name\": null,\n" +
                "      \"beneficiary_account\": null,\n" +
                "      \"beneficiary_full_name\": null,\n" +
                "      \"loan_purpose_detail\": null,\n" +
                "      \"loan_supplementing_business_capital\": null,\n" +
                "      \"loan_investment_fixed_asset\": null,\n" +
                "      \"debt_acknowledgment_contract_period\": null,\n" +
                "      \"is_private_business_owner\": null,\n" +
                "      \"refinance_loan\": null,\n" +
                "      \"loan_item_collateral_distributions\": [\n" +
                "        {\n" +
                "          \"uuid\": \"cb593b62-d02d-4582-be66-52d102bcd3b4\",\n" +
                "          \"loan_item_id\": \"92c761db-f308-4865-9257-41e0c9348efb\",\n" +
                "          \"collateral_id\": \"4e0a81db-b47a-489e-9cbc-76e5212b8ce6\",\n" +
                "          \"percent\": 100,\n" +
                "          \"collateral\": {\n" +
                "            \"uuid\": \"4e0a81db-b47a-489e-9cbc-76e5212b8ce6\",\n" +
                "            \"loan_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "            \"type\": \"ND\",\n" +
                "            \"status\": \"ME\",\n" +
                "            \"full_name\": null,\n" +
                "            \"relationship\": null,\n" +
                "            \"province\": \"02\",\n" +
                "            \"province_name\": \"Tỉnh Hà Giang\",\n" +
                "            \"district\": \"026\",\n" +
                "            \"district_name\": \"Huyện Đồng Văn\",\n" +
                "            \"ward\": \"00715\",\n" +
                "            \"ward_name\": \"Xã Lũng Cú\",\n" +
                "            \"address\": \"\",\n" +
                "            \"value\": 500000000,\n" +
                "            \"location\": null,\n" +
                "            \"description\": null,\n" +
                "            \"mvalue_id\": \"\",\n" +
                "            \"core_id\": \"\",\n" +
                "            \"valuation_cert\": \"\",\n" +
                "            \"asset_category\": \"RE5\",\n" +
                "            \"legal_doc\": \"2704\",\n" +
                "            \"doc_issued_on\": \"2023-04-04T00:00:00.000+00:00\",\n" +
                "            \"doc_place_of_issue\": \"02\",\n" +
                "            \"doc_place_of_issue_name\": null,\n" +
                "            \"guaranteed_value\": null,\n" +
                "            \"ltv_rate\": 80,\n" +
                "            \"pricing_date\": null,\n" +
                "            \"next_pricing_date\": null,\n" +
                "            \"officer_name_pricing\": \"\",\n" +
                "            \"type_of_doc\": null,\n" +
                "            \"chassis_no\": null,\n" +
                "            \"engine_no\": null,\n" +
                "            \"duration_of_used\": null,\n" +
                "            \"status_using\": null,\n" +
                "            \"saving_book_no\": null,\n" +
                "            \"maturity_date\": null,\n" +
                "            \"issued_branch\": null,\n" +
                "            \"interest_rate\": null,\n" +
                "            \"registration_or_contract_no\": null,\n" +
                "            \"collateral_owner_maps\": null,\n" +
                "            \"collateral_owners\": null,\n" +
                "            \"index\": 1\n" +
                "          },\n" +
                "          \"index\": 1\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"common_income\": {\n" +
                "    \"uuid\": \"8d0352a4-6e7a-4e1b-8d6b-7be26dfa34a5\",\n" +
                "    \"loan_application_id\": \"803096c9-986a-4adb-a5be-b140e231dd22\",\n" +
                "    \"approval_flow\": \"NORMALLY\",\n" +
                "    \"recognition_method1\": \"ACTUALLY_RECEIVED\",\n" +
                "    \"recognition_method2\": null,\n" +
                "    \"selected_incomes\": [\n" +
                "      \"OTHERS_INCOME\"\n" +
                "    ]\n" +
                "  }\n" +
                "}", LoanApplicationReview.class);
        if (loanPreApproval.equals(loanPreApproval2)) {
            System.out.println("true");
        }
        System.out.println("false");
    }
}
