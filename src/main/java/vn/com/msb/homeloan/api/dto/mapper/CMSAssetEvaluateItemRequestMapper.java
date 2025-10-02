package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSAssetEvaluateItemRequest;
import vn.com.msb.homeloan.core.model.AssetEvaluateItem;

@Mapper
public interface CMSAssetEvaluateItemRequestMapper {

  CMSAssetEvaluateItemRequestMapper INSTANCE = Mappers.getMapper(
      CMSAssetEvaluateItemRequestMapper.class);

  AssetEvaluateItem toModel(CMSAssetEvaluateItemRequest request);

  List<AssetEvaluateItem> toModel(List<CMSAssetEvaluateItemRequest> requests);
}
