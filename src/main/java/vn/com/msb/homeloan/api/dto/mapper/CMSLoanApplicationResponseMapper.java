package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSCustomerInfoResponse;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;

@Mapper
public interface CMSLoanApplicationResponseMapper {

  CMSLoanApplicationResponseMapper INSTANCE = Mappers.getMapper(
      CMSLoanApplicationResponseMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  CMSCustomerInfoResponse toDTO(LoanApplicationEntity model);
}
