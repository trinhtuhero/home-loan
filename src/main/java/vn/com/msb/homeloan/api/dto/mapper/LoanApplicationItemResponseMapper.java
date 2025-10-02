package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSLoanApplicationItemResponse;
import vn.com.msb.homeloan.api.dto.response.LoanApplicationItemResponse;
import vn.com.msb.homeloan.core.constant.DisbursementMethodEnum;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;

import java.util.List;

@Mapper
public interface LoanApplicationItemResponseMapper {

  LoanApplicationItemResponseMapper INSTANCE = Mappers.getMapper(
      LoanApplicationItemResponseMapper.class);

  CMSLoanApplicationItemResponse toCmsResponse(LoanApplicationItem model);

  List<CMSLoanApplicationItemResponse> toCmsResponse(List<LoanApplicationItem> models);

  LoanApplicationItemResponse toLdpResponse(LoanApplicationItem model);

  List<LoanApplicationItemResponse> toLdpResponse(List<LoanApplicationItem> models);

  @AfterMapping
  default void afterMapping(
      @MappingTarget final CMSLoanApplicationItemResponse.CMSLoanApplicationItemResponseBuilder target,
      LoanApplicationItem source) {
    if (DisbursementMethodEnum.OTHER.equals(source.getDisbursementMethod())) {
      target.disbursementMethod(source.getDisbursementMethodOther());
    }
  }
}
