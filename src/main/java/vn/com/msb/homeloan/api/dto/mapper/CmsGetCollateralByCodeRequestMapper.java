package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSGetCollateralByCodeRequest;
import vn.com.msb.homeloan.core.model.request.mvalue.GetCollateralByCodeRequest;

@Mapper
public interface CmsGetCollateralByCodeRequestMapper {

  CmsGetCollateralByCodeRequestMapper INSTANCE = Mappers.getMapper(
      CmsGetCollateralByCodeRequestMapper.class);

  GetCollateralByCodeRequest toModelP(CMSGetCollateralByCodeRequest request);
}
