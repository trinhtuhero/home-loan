package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.model.MarriedPerson;

@Mapper
public abstract class MarriedPersonInfoMapper {

  public static final MarriedPersonInfoMapper INSTANCE = Mappers.getMapper(
      MarriedPersonInfoMapper.class);

  public abstract MarriedPerson toModel(MarriedPersonEntity entity);

  public abstract List<MarriedPerson> toModels(List<MarriedPersonEntity> entities);

  public abstract MarriedPersonEntity toEntity(MarriedPerson model);
}
