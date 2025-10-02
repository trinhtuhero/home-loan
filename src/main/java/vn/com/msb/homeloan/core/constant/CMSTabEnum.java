package vn.com.msb.homeloan.core.constant;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum CMSTabEnum {

  CUSTOMER_INFO("CUSTOMER_INFO", "Thông tin khách hàng"),
  MARRIED_PERSON("MARRIED_PERSON", "Thông tin vợ chòng"),
  CONTACT_PERSON("CONTACT_PERSON", "Thông tin người liên hệ"),
  LOAN_PAYER("LOAN_PAYER", "Thông tin người đồng trả nợ"),
  LOAN_INCOME_INFO("LOAN_INCOME_INFO", "Thông tin nguồn thu"),

  SALARY_INCOME("SALARY_INCOME", "Thu nhập từ lương"),
  PERSONAL_BUSINESS_INCOME("PERSONAL_BUSINESS_INCOME", "Thu nhập từ cá nhân/Hộ kinh doanh"),
  BUSINESS_INCOME("BUSINESS_INCOME", "Thu nhập từ doanh nghiệp"),
  OTHERS_INCOME("OTHERS_INCOME", "Thu nhập từ nguồn thu khác"),

  ASSUMING_TOTAL_ASSETS_INCOME("ASSUMING_TOTAL_ASSETS_INCOME", "Nguồn thu giả định tổng tài sản"),
  ASSUMING_OTHERS_INCOME("ASSUMING_OTHERS_INCOME", "Nguồn thu giả định khác"),

  COLLATERAL_INFO("COLLATERAL_INFO", "Thông tin tài sản đảm bảo"),
  COLLATERAL_OWNER("COLLATERAL_OWNER", "Thông tin chủ tài sản"),
  LOAN_INFO("LOAN_INFO", "Thông tin khoản vay"),
  CUSTOMER_CHECK("CUSTOMER_CHECK", "Kiểm tra thông tin khách hàng"),
  EXPERTISE("EXPERTISE", "Thẩm định"),
  FILE_UPLOAD("FILE_UPLOAD", "Hồ sơ vay");

  private String code;
  private String name;

  CMSTabEnum(String code, String name) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }


  public static Map<String, String> toMap = Stream.of(values())
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getCode()));

  public static Map<String, String> toIncomeMap = Stream.of(values()).filter(
          cmsTabEnum -> Arrays.asList(SALARY_INCOME, PERSONAL_BUSINESS_INCOME, BUSINESS_INCOME,
              OTHERS_INCOME, ASSUMING_TOTAL_ASSETS_INCOME, ASSUMING_OTHERS_INCOME).contains(cmsTabEnum))
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getCode()));

  public static String toIncomeString = Stream.of(values()).filter(
          cmsTabEnum -> Arrays.asList(SALARY_INCOME, PERSONAL_BUSINESS_INCOME, BUSINESS_INCOME,
              OTHERS_INCOME, ASSUMING_TOTAL_ASSETS_INCOME, ASSUMING_OTHERS_INCOME).contains(cmsTabEnum))
      .map(CMSTabEnum::getCode)
      .collect(Collectors.joining(", "));

  // Thực nhận hoặc kê khai
  public static Map<String, String> toIncomeMap1 = Stream.of(values()).filter(
          cmsTabEnum -> Arrays.asList(SALARY_INCOME, PERSONAL_BUSINESS_INCOME, BUSINESS_INCOME,
              OTHERS_INCOME).contains(cmsTabEnum))
      .collect(Collectors.toMap(k -> k.getCode(), v -> v.getCode()));

  public static String toIncomeString1 = Stream.of(values()).filter(
          cmsTabEnum -> Arrays.asList(SALARY_INCOME, PERSONAL_BUSINESS_INCOME, BUSINESS_INCOME,
              OTHERS_INCOME).contains(cmsTabEnum)).map(CMSTabEnum::getCode)
      .collect(Collectors.joining(", "));
}
