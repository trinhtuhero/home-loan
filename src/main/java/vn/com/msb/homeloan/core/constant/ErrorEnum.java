package vn.com.msb.homeloan.core.constant;

import lombok.Getter;

import java.util.Map;

@Getter
public enum ErrorEnum {

  /**
   * Cac ma loi dung chung
   */
  //400
  INVALID_FORM(400001, "invalid form"),
  LOAN_APPLICATION_ID_IS_NULL(400004, "%s must be not null"),
  PHONE_INVALID(400002, "must match pattern"),
  VALUE_INVALID(400003, "not a number"),
  DATE_FORMAT_INVALID(400006, "invalid date format"),
  IMPORT_TEMPLATE_FILE_INVALID(400007, "file template invalid"),
  UUID_INVALID(400010, "invalid uuid"),
  RESOURCE_EDIT_NOT_ALLOW(400100, "Resource can not edit"),
  DATA_INVALID(400101, "data invalid %s : %s"),

  //401
  TOKEN_INVALID(401001, "token invalid"),
  //403
  ACCESS_DENIED(403002, "access denied"),
  //404
  RESOURCE_NOT_FOUND(404001, "resource not found %s:%s"),
  LOAN_APPLICATION_NOT_FOUND(404002, "loan application not found"),
  MARRIED_PERSON_NOT_FOUND(404008, "married person Not Found"),
  LOAN_PAYER_NOT_FOUND(404009, "loan payer not found"),
  PROFILE_NOT_FOUND(404010, "profile not found by id %s"),
  COLLATERAL_NOT_FOUND(404011, "collateral not found by id %s"),
  CONTACT_PERSON_NOT_FOUND(404012, "contact person Not Found"),
  LOAN_UPLOAD_FILE_NOT_FOUND(404013, "Loan upload file Not Found by id %s"),
  UPLOAD_FILE_NOT_FOUND(404017, "Upload file Not Found"),
  CMS_USER_EMAIL_FOUND(404014, "cms user's email does not Found"),
  CMS_USER_EMAIL_EXIST(404015, "cms user's email already exists"),
  COLLATERAL_OWNER_NOT_FOUND(404016, "collateral owner not found by id %s"),

  LOAN_APPLICATION_ID_NOT_UPDATE(400037, "Loan application id Not Update"),
  OTHER_EVALUATE_NOT_FOUND(404038, "other evaluate not found"),
  FIELD_SURVEY_ITEM_NOT_FOUND(404039, "field survey item not found"),

  FIELD_SURVEY_ITEM_NOT_EMPTY(404040, "field survey item list must not be empty"),
  EXCEPTION_ITEM_ITEM_NOT_EMPTY(404041, "exception item list must not be empty"),
  BUSINESS_INCOME_NOT_EMPTY(404042, "business income list must not be empty"),
  SALARY_INCOME_NOT_EMPTY(404043, "salary income list must not be empty"),
  OTHER_INCOME_NOT_EMPTY(404044, "other income list must not be empty"),
  COLLATERAL_NOT_EMPTY(404045, "Collateral list must not be empty"),
  LOAN_APPLICATION_ITEM_NOT_EMPTY(404046, "Loan application item list must not be empty"),

  //500
  SERVER_ERROR(500001, "internal server error"),

  /**
   * Cac ma loi dung rieng cho tung APIs khong trung voi cac ma loi dung chung
   */
  //send OTP
  //400
  OTP_PHONE_INVALID(400002,
      "incorrect mobile number. please make sure you enter the correct number for your mobile phone"),
  OTP_SEND_FAILED(400005, "send OTP error, please contact customer service for support"),
  OTP_OVER_LIMIT(400011,
      "you entered the wrong OTP more than the allowed number of times. please re-send the verification code to try again"),
  OTP_EXPIRED(400012, "the OTP has expired. please re-send the verification code to try again"),
  //406
  OTP_BLOCKED(406001,
      "you have been temporarily blocked from performing this action. please try again %s seconds later"),
  LOAN_DETAIL_NOT_ALLOW(406002, "You Don't Have Access this record"),
  //verify OTP
  //400
  OTP_VERIFY_FAILED(400004, "the OTP entered is incorrect. Please check again"),
  OTP_UUID_INVALID(400008, "uuid invalid"),
  API_CSS_FAIL(400009, "Api css fail"),

  //loan info
  LOAN_INFO_AMOUNT_INVALID(400013, "must be less than or equal to %s"),
  LOAN_UPLOAD_FILE_EMPTY(400014, "File must be not empty"),
  LOAN_UPLOAD_FILE_EX_NOT_ALLOW(400015, "File extension not allowed (%s)"),
  LOAN_UPLOAD_FILE_MAX_SIZE(400016, "size must be less than or equal to %MB"),
  LOAN_UPLOAD_FILE_EXIST(400017, "File %s already exists"),
  LOAN_UPLOAD_FILE_MAX_LIMIT(400019,
      "Total file upload for file config id(%s) must be less than or equal to %s"),
  LOAN_UPLOAD_FILE_UPDATE_STATUS_EMPTY(400020, "File list must be not empty"),
  LOAN_UPLOAD_FILE_MAX_CHOOSE(400021, "Total file choose must be less than or equal to %s"),
  LOAN_APPLICATION_EDIT_NOT_ALLOW(400022, "You do not have permission to edit this record"),
  LOAN_APPLICATION_SUBMIT_ERROR(400023, "Gen loan code fail, Please try again"),
  LOAN_APPLICATION_SUBMIT_STATUS_NOT_ALLOW(400024, "Status invalid, please send %s"),
  ZIP_NOT_READY(400025, "Zip not ready for download"),
  ZIP_NOT_FOUND(400026, "Zip not found"),
  LOAN_APPLICATIONS_CLOSE_FAIL(400027, "Close loan applications fail"),
  LOAN_APPLICATIONS_CLOSE_NOT_ALLOW(400028, "Close loan applications not allow"),
  CMS_USER_NOT_EXIST(400029, "User %s dose not exist"),
  BUSINESS_CODE_IS_REQUIRED(400030, "Business code is required"),
  LOAN_APPLICATION_GEN_LOAN_CODE_FAIL(400031, "Gen loan code fail, Please try again"),
  LOAN_APPLICATION_INSERT_NOT_ALLOW(400032,
      "Create loan not allow, please complete the loan first"),
  LOAN_APPLICATION_COMMENT_CODE_MUST_MATCH_PATTERN(400033,
      "must match CUSTOMER_INFO|INCOME_INFO|COLLATERAL_INFO|LOAN_INFO"),
  ORGANIZATION_MUST_MATCH_PATTERN(400034, "must match DVKD|AREA"),
  NO_COMMENTS_FOUND(400035, "No comments found"),
  BRANCH_CODE_NOT_FOUND(400036, "Branch code Not Found"),
  ROLE_CMS_USER_NOT_FOUND(400037, "Role %s Not Found"),
  LOAN_APPLICATION_ID_CAN_NOT_CHANGE(400037, "Loan application id Not Change"),
  ASSET_EVAL_ID_NOT_UPDATE(400038, "Asset eval id Not Update"),
  LOAN_APPLICATION_UPDATE_STATUS_NOT_ALLOW(400039, "Do not change the state from %s to %s"),
  CAN_NOT_COMPLETE_TO_TRINH(400040, "Need to save tabs information before this action"),
  COMPLETE_TO_TRINH_ERROR_1(4000401, "Customer info"),
  COMPLETE_TO_TRINH_ERROR_2(4000402, "Married person"),
  COMPLETE_TO_TRINH_ERROR_3(4000403, "Contact person"),
  COMPLETE_TO_TRINH_ERROR_4(4000404, "Loan income info"),
  COMPLETE_TO_TRINH_ERROR_5(4000405, "Assuming total assets income or Assuming others income"),
  COMPLETE_TO_TRINH_ERROR_6(4000406,
      "Salary income or Personal business income or Business income or Other income"),
  COMPLETE_TO_TRINH_ERROR_7(4000407, "Collateral info"),
  COMPLETE_TO_TRINH_ERROR_8(4000408, "Loan info"),
  COMPLETE_TO_TRINH_ERROR_9(4000409, "Upload file"),
  COMPLETE_TO_TRINH_ERROR_10(4000410, "Appraisal"),
  ALREADY_EXIST_IN_SYSTEM(400041, "Already exist in the system"),
  UPLOAD_AT_LEAST_ONE(400043, "Upload at least one"),
  FILE_UPLOAD_REQUIRED(400042, "Please upload the required files"),

  //credit card
  MAIN_CREDIT_CARD_ALREADY_EXIST(400044, "Main card already exists"),
  EXCEED_NUMBER_CREDIT_CARD(400045, "Exceed the number of cards"),
  CARD_POLICY_CODE_NOT_EXIST(400046, "Card policy code not exist"),
  CARD_LIMIT_ERROR(400047, "Supplementary card limit <= Primary card limit"),
  PRIMARY_CARD_NOT_EXIST(400048, "Primary card does not exist"),
  CARD_TYPE_CODE_NOT_EXIST(400049, "Card type does not exist"),

  ITEM_LIMIT(400018, "The number of %s must be less than or equal to %s"),

  // Bạn chưa chọn tài sản bảo đảm cho khoản vay <x>
  COLLATERAL_DISTRIBUTION_ERROR_1(400050,
      "You have not chosen collateral for the loan application item"),
  // TSBĐ <x> chưa được phân bổ vào khoản vay
  COLLATERAL_DISTRIBUTION_ERROR_2(400051,
      "The collateral %s has not been distributed to any loan application items"),
  // Tổng tỉ lệ phân bổ của Tài sản bảo đảm <x> phải nhỏ hơn hoặc bằng 100%
  COLLATERAL_DISTRIBUTION_ERROR_3(400052,
      "The total distributions of the collateral %s must be less than or equal to 100"),

  EXPORT_PROPOSAL_LETTER_NOT_ALLOW(400053,
      "You are only allowed to download the report in status %s"),

  COLLATERAL_SAVES_ERROR_1(400054, "Must have at least one collateral of type (ND/CC)"),
  INSUFFICIENT_FINANCIAL_CAPACITY(400055, "Outstanding balance beyond your financial capacity"),
  MINIMUM_LOAN_AMOUNT(400056, "Minimum loan amount is 100 million VND"),
  MAXIMUM_LOAN_AMOUNT(400057, "The maximum amount to loan is 20 billion VND"),
  DEBT_METHOD_MONTH(400058, "loan time is not available with this repayment method"),
  LAND_TRANSACTION_NOTFOUND(400059, "Land Transaction not found"),

  //500
  LOAN_UPLOAD_FILE_ERROR(500002, "Upload file error"),
  CALL_API_CIC_ERROR(500003, "Call api cic error"),
  CALL_API_OPRISK_CHECK_P_ERROR(500004, "Call api oprisk check-p error"),
  CALL_API_OPRISK_CHECK_C_ERROR(500005, "Call api oprisk check-c error"),

  ;
  private final int code;
  private final String message;
  private Map<String, String> detail;

  ErrorEnum(int code, String message) {
    this.code = code;
    this.message = message;
  }

  ErrorEnum(int code, String message, Map<String, String> detail) {
    this.code = code;
    this.message = message;
    this.detail = detail;
  }

}
