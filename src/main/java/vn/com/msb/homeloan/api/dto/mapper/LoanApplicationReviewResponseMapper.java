package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.LoanApplicationReviewResponse;
import vn.com.msb.homeloan.api.dto.response.LoanPayerResponse;
import vn.com.msb.homeloan.core.model.LoanApplicationReview;
import vn.com.msb.homeloan.core.model.LoanPayer;
import vn.com.msb.homeloan.core.util.StringUtils;

@Mapper
public interface LoanApplicationReviewResponseMapper {

  LoanApplicationReviewResponseMapper INSTANCE = Mappers.getMapper(
      LoanApplicationReviewResponseMapper.class);

  @Mapping(source = "loanApplication.birthday", target = "loanApplication.birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "loanApplication.issuedOn", target = "loanApplication.issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "marriedPerson.birthday", target = "marriedPerson.birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "marriedPerson.issuedOn", target = "marriedPerson.issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "loanApplication.refCode", target = "loanApplication.refCode", qualifiedByName = "getRefCodeCustom")
  LoanApplicationReviewResponse toDTO(LoanApplicationReview model);

  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  LoanPayerResponse toLoanPayerResponse(LoanPayer model);

  @Named("getRefCodeCustom")
  default String getRefCodeCustom(String refCode) {
    return StringUtils.isEmpty(refCode) ? null : (refCode + ".com.vn");
  }
}
