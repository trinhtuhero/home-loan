package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSCollateralResponse;
import vn.com.msb.homeloan.api.dto.response.CMSMvalueCollateralResponse;
import vn.com.msb.homeloan.core.model.Collateral;

import java.util.List;

@Mapper
public interface CMSCollateralResponseMapper {

  CMSCollateralResponseMapper INSTANCE = Mappers.getMapper(CMSCollateralResponseMapper.class);

  @Mapping(source = "docIssuedOn", target = "docIssuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "pricingDate", target = "pricingDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "nextPricingDate", target = "nextPricingDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "maturityDate", target = "maturityDate", dateFormat = "yyyyMMdd")
  CMSCollateralResponse toCmsResponse(Collateral model);

  List<CMSCollateralResponse> toCmsResponses(List<Collateral> model);

  CMSMvalueCollateralResponse toMvalueResponse(Collateral model);
}
