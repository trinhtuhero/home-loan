package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSBusinessIncomeResponse;
import vn.com.msb.homeloan.core.model.BusinessIncome;

@Mapper
public interface CMSBusinessIncomeResponseMapper {

  CMSBusinessIncomeResponseMapper INSTANCE = Mappers.getMapper(
      CMSBusinessIncomeResponseMapper.class);

  @Mapping(source = "issuedDate", target = "issuedDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "registrationChangeDate", target = "registrationChangeDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "businessStartDate", target = "businessStartDate", dateFormat = "yyyyMMdd")
  CMSBusinessIncomeResponse toCmsResponse(BusinessIncome response);

  List<CMSBusinessIncomeResponse> toCmsResponses(List<BusinessIncome> responses);
}
