package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.OrganizationResponse;
import vn.com.msb.homeloan.core.model.Organization;

@Mapper
public interface OrganizationResponseMapper {

  OrganizationResponseMapper INSTANCE = Mappers.getMapper(OrganizationResponseMapper.class);

  OrganizationResponse toDTO(Organization model);

  List<OrganizationResponse> toDTOs(List<Organization> models);
}
