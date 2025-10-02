package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.OrganizationEntity;
import vn.com.msb.homeloan.core.model.Organization;

@Mapper
public interface OrganizationMapper {

  OrganizationMapper INSTANCE = Mappers.getMapper(OrganizationMapper.class);

  Organization toModel(OrganizationEntity entity);

  OrganizationEntity toEntity(Organization modal);

  List<Organization> toModels(List<OrganizationEntity> entities);

  List<OrganizationEntity> toEntities(List<Organization> models);
}
