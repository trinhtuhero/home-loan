package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CreateCmsUserRequest;
import vn.com.msb.homeloan.core.model.CmsUser;

@Mapper
public interface CreateCmsUserRequestMapper {

  CreateCmsUserRequestMapper INSTANCE = Mappers.getMapper(CreateCmsUserRequestMapper.class);

  CmsUser toModel(CreateCmsUserRequest request);
}
