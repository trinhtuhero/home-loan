package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSLoanPayerRequest;
import vn.com.msb.homeloan.core.model.LoanPayer;

@Mapper
public interface CMSLoanPayerRequestMapper {

  CMSLoanPayerRequestMapper INSTANCE = Mappers.getMapper(CMSLoanPayerRequestMapper.class);

  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  LoanPayer toModel(CMSLoanPayerRequest request);

  List<LoanPayer> toModels(List<CMSLoanPayerRequest> requests);
}
