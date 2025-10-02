package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSLoanApplicationExportExcelRequest;
import vn.com.msb.homeloan.core.model.CmsLoanApplicationExportExcelParam;

@Mapper
public interface CmsLoanApplicationExportExcelRequestMapper {

  CmsLoanApplicationExportExcelRequestMapper INSTANCE = Mappers.getMapper(
      CmsLoanApplicationExportExcelRequestMapper.class);

  @Mapping(source = "receptionDateFrom", target = "receptionDateFrom", dateFormat = "yyyyMMdd")
  @Mapping(source = "receptionDateTo", target = "receptionDateTo", dateFormat = "yyyyMMdd")
  CmsLoanApplicationExportExcelParam toModel(CMSLoanApplicationExportExcelRequest request);
}
