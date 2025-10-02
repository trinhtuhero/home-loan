package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CmsUserSearchRequest;
import vn.com.msb.homeloan.core.model.CmsUserSearchParam;

@Mapper
public interface CmsUserSearchRequestMapper {

  CmsUserSearchRequestMapper INSTANCE = Mappers.getMapper(CmsUserSearchRequestMapper.class);

  CmsUserSearchParam toModel(CmsUserSearchRequest request);
}
