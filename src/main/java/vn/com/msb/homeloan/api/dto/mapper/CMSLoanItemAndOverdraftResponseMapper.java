package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSLoanApplicationItemResponse;
import vn.com.msb.homeloan.api.dto.response.LoanApplicationItemResponse;
import vn.com.msb.homeloan.api.dto.response.overdraft.CMSLoanItemAndOverdraftResponse;
import vn.com.msb.homeloan.core.constant.DisbursementMethodEnum;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;
import vn.com.msb.homeloan.core.model.LoanItemAndOverdraft;

@Mapper
public interface CMSLoanItemAndOverdraftResponseMapper {

  CMSLoanItemAndOverdraftResponseMapper INSTANCE = Mappers.getMapper(
      CMSLoanItemAndOverdraftResponseMapper.class);

  CMSLoanItemAndOverdraftResponse toResponse(LoanItemAndOverdraft loanItemAndOverdraft);

  @AfterMapping
  default void afterMapping(
    @MappingTarget final CMSLoanApplicationItemResponse.CMSLoanApplicationItemResponseBuilder target,
    LoanApplicationItem source) {
    if (source != null && DisbursementMethodEnum.OTHER.equals(source.getDisbursementMethod())) {
      target.disbursementMethod(source.getDisbursementMethodOther());
    }
  }

}
