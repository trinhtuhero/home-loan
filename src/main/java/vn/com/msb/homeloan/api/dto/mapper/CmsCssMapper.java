package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSCssRequest;
import vn.com.msb.homeloan.core.model.CssParam;

@Mapper
public interface CmsCssMapper {

  CmsCssMapper INSTANCE = Mappers.getMapper(CmsCssMapper.class);

  CssParam toModel(CMSCssRequest request);
}
