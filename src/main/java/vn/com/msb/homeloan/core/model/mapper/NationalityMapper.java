package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.NationalityEntity;
import vn.com.msb.homeloan.core.model.Nationality;

@Mapper
public interface NationalityMapper {

  NationalityMapper INSTANCE = Mappers.getMapper(NationalityMapper.class);

  Nationality toModel(NationalityEntity entity);

  List<Nationality> toModels(List<NationalityEntity> entities);
}
