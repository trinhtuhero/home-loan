package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.OverdraftEntity;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;

@Mapper
public interface OverdraftMapper {

  OverdraftMapper INSTANCE = Mappers.getMapper(OverdraftMapper.class);

  Overdraft toModel(OverdraftEntity entity);

  List<Overdraft> toModels(List<OverdraftEntity> entities);

  OverdraftEntity toEntity(Overdraft modal);

  List<OverdraftEntity> toEntities(List<Overdraft> models);
}
