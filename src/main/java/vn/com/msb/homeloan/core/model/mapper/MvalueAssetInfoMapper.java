package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.MvalueAssetInfoEntity;
import vn.com.msb.homeloan.core.model.MvalueAssetInfo;

@Mapper
public interface MvalueAssetInfoMapper {

  MvalueAssetInfoMapper INSTANCE = Mappers.getMapper(MvalueAssetInfoMapper.class);

  MvalueAssetInfo toModel(MvalueAssetInfoEntity entity);

  List<MvalueAssetInfo> toModels(List<MvalueAssetInfoEntity> entities);
}
