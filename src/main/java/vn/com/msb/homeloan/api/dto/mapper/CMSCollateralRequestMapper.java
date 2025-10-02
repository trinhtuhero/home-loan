package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSCollateralRequest;
import vn.com.msb.homeloan.core.model.Collateral;

@Mapper
public interface CMSCollateralRequestMapper {

  CMSCollateralRequestMapper INSTANCE = Mappers.getMapper(CMSCollateralRequestMapper.class);

  @Mapping(source = "docIssuedOn", target = "docIssuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "pricingDate", target = "pricingDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "maturityDate", target = "maturityDate", dateFormat = "yyyyMMdd")
  @Mapping(source = "nextPricingDate", target = "nextPricingDate", dateFormat = "yyyyMMdd")
  Collateral toModel(CMSCollateralRequest dto);

  List<Collateral> toModels(List<CMSCollateralRequest> dto);
}
