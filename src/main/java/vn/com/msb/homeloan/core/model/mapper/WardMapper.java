package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.WardEntity;
import vn.com.msb.homeloan.core.model.Ward;

@Mapper
public interface WardMapper {

  WardMapper INSTANCE = Mappers.getMapper(WardMapper.class);

  Ward toModel(WardEntity entity);

  List<Ward> toModels(List<WardEntity> entities);
}
