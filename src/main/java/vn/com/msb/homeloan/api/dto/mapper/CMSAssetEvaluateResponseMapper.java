package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSAssetEvaluateResponse;
import vn.com.msb.homeloan.core.model.AssetEvaluate;

@Mapper
public interface CMSAssetEvaluateResponseMapper {

  CMSAssetEvaluateResponseMapper INSTANCE = Mappers.getMapper(CMSAssetEvaluateResponseMapper.class);

  CMSAssetEvaluateResponse toCmsResponse(AssetEvaluate response);
}
