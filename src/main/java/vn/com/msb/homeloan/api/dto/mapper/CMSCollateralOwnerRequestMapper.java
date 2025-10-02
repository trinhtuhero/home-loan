package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSCollateralOwnerRequest;
import vn.com.msb.homeloan.core.model.CollateralOwner;

@Mapper
public interface CMSCollateralOwnerRequestMapper {

  CMSCollateralOwnerRequestMapper INSTANCE = Mappers.getMapper(
      CMSCollateralOwnerRequestMapper.class);

  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  CollateralOwner toModel(CMSCollateralOwnerRequest dto);

  List<CollateralOwner> toModels(List<CMSCollateralOwnerRequest> requests);
}
