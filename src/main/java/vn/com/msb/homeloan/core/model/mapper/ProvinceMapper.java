package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.ProvinceEntity;
import vn.com.msb.homeloan.core.model.Province;

@Mapper
public interface ProvinceMapper {

  ProvinceMapper INSTANCE = Mappers.getMapper(ProvinceMapper.class);

  Province toModel(ProvinceEntity entity);

  List<Province> toModels(List<ProvinceEntity> entities);
}
