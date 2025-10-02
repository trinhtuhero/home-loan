package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSLoanPayerResponse;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.model.LoanPayer;

@Mapper
public interface CMSLoanPayerResponseMapper {

  CMSLoanPayerResponseMapper INSTANCE = Mappers.getMapper(CMSLoanPayerResponseMapper.class);

  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  CMSLoanPayerResponse toResponse(LoanPayerEntity response);

  List<CMSLoanPayerResponse> toResponses(List<LoanPayerEntity> responses);

  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  CMSLoanPayerResponse modelToResponse(LoanPayer response);

  List<CMSLoanPayerResponse> modelToResponses(List<LoanPayer> responses);
}
