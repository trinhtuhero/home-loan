package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSCollateralOwnerResponse;
import vn.com.msb.homeloan.core.model.CollateralOwner;

@Mapper
public interface CMSCollateralOwnerResponseMapper {

  CMSCollateralOwnerResponseMapper INSTANCE = Mappers.getMapper(
      CMSCollateralOwnerResponseMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  CMSCollateralOwnerResponse toCmsResponse(CollateralOwner model);

  List<CMSCollateralOwnerResponse> toCmsResponses(List<CollateralOwner> model);
}
