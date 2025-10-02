package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.DistrictEntity;
import vn.com.msb.homeloan.core.model.District;

@Mapper
public interface DistrictMapper {

  DistrictMapper INSTANCE = Mappers.getMapper(DistrictMapper.class);

  District toModel(DistrictEntity entity);

  List<District> toModels(List<DistrictEntity> entities);
}
