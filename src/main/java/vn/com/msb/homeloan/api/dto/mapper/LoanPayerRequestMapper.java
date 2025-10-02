package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.LoanPayerRequest;
import vn.com.msb.homeloan.api.dto.response.LoanPayerResponse;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.model.LoanPayer;

@Mapper
public interface LoanPayerRequestMapper {

  LoanPayerRequestMapper INSTANCE = Mappers.getMapper(LoanPayerRequestMapper.class);

  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  LoanPayer toModel(LoanPayerRequest request);

  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  LoanPayerResponse toResponse(LoanPayer loanPayer);

  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  LoanPayerResponse toResponse(LoanPayerEntity loanPayerEntity);
}
