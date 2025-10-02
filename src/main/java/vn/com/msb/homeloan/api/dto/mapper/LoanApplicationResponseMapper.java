package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.loanApplication.LoanApplicationResponse;
import vn.com.msb.homeloan.core.model.LoanApplication;

@Mapper
public interface LoanApplicationResponseMapper {

  LoanApplicationResponseMapper INSTANCE = Mappers.getMapper(LoanApplicationResponseMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  LoanApplicationResponse toDTO(LoanApplication model);
}
