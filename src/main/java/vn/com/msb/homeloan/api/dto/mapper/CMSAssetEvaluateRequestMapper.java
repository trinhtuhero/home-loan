package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSAssetEvaluateRequest;
import vn.com.msb.homeloan.core.model.AssetEvaluate;

@Mapper
public interface CMSAssetEvaluateRequestMapper {

  CMSAssetEvaluateRequestMapper INSTANCE = Mappers.getMapper(CMSAssetEvaluateRequestMapper.class);

  AssetEvaluate toModel(CMSAssetEvaluateRequest request);
}
