package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSReportSearchRequest;
import vn.com.msb.homeloan.core.model.CmsReportSearchParam;

@Mapper
public interface CmsReportSearchRequestMapper {

  CmsReportSearchRequestMapper INSTANCE = Mappers.getMapper(CmsReportSearchRequestMapper.class);

  @Mapping(source = "dateFrom", target = "dateFrom", dateFormat = "yyyyMMdd")
  @Mapping(source = "dateTo", target = "dateTo", dateFormat = "yyyyMMdd")
  CmsReportSearchParam toModel(CMSReportSearchRequest request);
}
