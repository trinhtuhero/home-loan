package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSBusinessIncomeRequest;
import vn.com.msb.homeloan.core.model.BusinessIncome;

@Mapper
public interface CMSBusinessIncomeRequestMapper {

  CMSBusinessIncomeRequestMapper INSTANCE = Mappers.getMapper(CMSBusinessIncomeRequestMapper.class);

  @Mapping(source = "issuedDate", target = "issuedDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "registrationChangeDate", target = "registrationChangeDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "businessStartDate", target = "businessStartDate", dateFormat = "yyyyMMdd")
  BusinessIncome toModel(CMSBusinessIncomeRequest request);

  List<BusinessIncome> toModels(List<CMSBusinessIncomeRequest> requests);
}
