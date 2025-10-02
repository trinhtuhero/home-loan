package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.LoanPayerResponse;
import vn.com.msb.homeloan.core.model.LoanPayer;

@Mapper
public interface LoanPayerResponseMapper {

  LoanPayerResponseMapper INSTANCE = Mappers.getMapper(LoanPayerResponseMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  LoanPayerResponse toDto(LoanPayer request);

  List<LoanPayerResponse> toDtos(List<LoanPayer> loanPayers);
}
