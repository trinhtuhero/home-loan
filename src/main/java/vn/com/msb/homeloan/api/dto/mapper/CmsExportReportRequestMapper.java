package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSExportReportRequest;
import vn.com.msb.homeloan.core.model.CmsExportReportParam;

@Mapper
public interface CmsExportReportRequestMapper {

  CmsExportReportRequestMapper INSTANCE = Mappers.getMapper(CmsExportReportRequestMapper.class);

  @Mapping(source = "dateFrom", target = "dateFrom", dateFormat = "yyyyMMdd")
  @Mapping(source = "dateTo", target = "dateTo", dateFormat = "yyyyMMdd")
  CmsExportReportParam toModel(CMSExportReportRequest request);
}
