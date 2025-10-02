package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSBusinessIncomeResponse;
import vn.com.msb.homeloan.api.dto.response.CMSCollateralResponse;
import vn.com.msb.homeloan.api.dto.response.CMSLoanApplicationReviewResponse;
import vn.com.msb.homeloan.api.dto.response.CMSLoanPayerResponse;
import vn.com.msb.homeloan.api.dto.response.CMSSalaryIncomeResponse;
import vn.com.msb.homeloan.api.dto.response.LoanApplicationItemResponse;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.CreditAppraisalResponse;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.FieldSurveyItemResponse;
import vn.com.msb.homeloan.core.constant.DisbursementMethodEnum;
import vn.com.msb.homeloan.core.model.BusinessIncome;
import vn.com.msb.homeloan.core.model.CMSLoanApplicationReview;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.CreditAppraisal;
import vn.com.msb.homeloan.core.model.FieldSurveyItem;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;
import vn.com.msb.homeloan.core.model.LoanPayer;
import vn.com.msb.homeloan.core.model.SalaryIncome;
import vn.com.msb.homeloan.core.util.StringUtils;

@Mapper
public interface CMSLoanApplicationReviewResponseMapper {

  CMSLoanApplicationReviewResponseMapper INSTANCE = Mappers.getMapper(
      CMSLoanApplicationReviewResponseMapper.class);

  @Mapping(source = "loanApplication.birthday", target = "loanApplication.birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "loanApplication.issuedOn", target = "loanApplication.issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "marriedPerson.birthday", target = "marriedPerson.birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "marriedPerson.issuedOn", target = "marriedPerson.issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "loanApplication.refCode", target = "loanApplication.refCode", qualifiedByName = "getRefCodeCustom")
  @Mapping(source = "loanApplication.cj4InterestedDate", target = "loanApplication.cj4InterestedDate", dateFormat = "yyyyMMddHHmmss")
  @Mapping(source = "loanApplication.nationality.name", target = "loanApplication.nationalityName")
  @Mapping(source = "marriedPerson.nationality.name", target = "marriedPerson.nationalityName")
  CMSLoanApplicationReviewResponse toDTO(CMSLoanApplicationReview model);

  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "nationality.name", target = "nationalityName")
  CMSLoanPayerResponse toLoanPayerResponse(LoanPayer model);

  @Mapping(source = "startWorkDate", target = "startWorkDate", dateFormat = "yyyyMMdd")
  CMSSalaryIncomeResponse toSalaryIncomeResponse(SalaryIncome salaryIncome);

  @Mapping(source = "issuedDate", target = "issuedDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "registrationChangeDate", target = "registrationChangeDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "businessStartDate", target = "businessStartDate", dateFormat = "yyyyMMdd")
  CMSBusinessIncomeResponse toBusinessIncomeResponse(BusinessIncome businessIncome);

  @Mapping(source = "time", target = "time", dateFormat = "yyyyMMdd")
  FieldSurveyItemResponse toFieldSurveyItemResponse(FieldSurveyItem fieldSurveyItem);

  @Mapping(source = "docIssuedOn", target = "docIssuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "pricingDate", target = "pricingDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "nextPricingDate", target = "nextPricingDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "maturityDate", target = "maturityDate", dateFormat = "yyyyMMdd")
  CMSCollateralResponse toCollateralResponse(Collateral collateral);

  @Mapping(source = "scoringDate", target = "scoringDate", dateFormat = "yyyyMMdd")
  CreditAppraisalResponse toCreditAppraisalResponse(CreditAppraisal creditAppraisal);

  @Named("getRefCodeCustom")
  default String getRefCodeCustom(String refCode) {
    return StringUtils.isEmpty(refCode) ? null : (refCode + ".com.vn");
  }

  @AfterMapping
  default void afterMapping(
      @MappingTarget final LoanApplicationItemResponse.LoanApplicationItemResponseBuilder target,
      LoanApplicationItem source) {
    if (source != null && DisbursementMethodEnum.OTHER.equals(source.getDisbursementMethod())) {
      target.disbursementMethod(source.getDisbursementMethodOther());
    }
  }
}
