package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSLoanApplicationSearchRequest;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationSearchParam;

@Mapper
public interface CmsLoanApplicationSearchRequestMapper {

  CmsLoanApplicationSearchRequestMapper INSTANCE = Mappers.getMapper(
      CmsLoanApplicationSearchRequestMapper.class);

  @Mapping(source = "receptionDateFrom", target = "receptionDateFrom", dateFormat = "yyyyMMdd")
  @Mapping(source = "receptionDateTo", target = "receptionDateTo", dateFormat = "yyyyMMdd")
  CmsLoanApplicationSearchParam toModel(CMSLoanApplicationSearchRequest request);
}
