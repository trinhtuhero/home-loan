package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.MtkTrackingEntity;
import vn.com.msb.homeloan.core.model.MtkTracking;

@Mapper
public interface MtkTrackingMapper {

  MtkTrackingMapper INSTANCE = Mappers.getMapper(MtkTrackingMapper.class);

  MtkTracking toModel(MtkTrackingEntity entity);

  List<MtkTracking> toModels(List<MtkTrackingEntity> entities);

  MtkTrackingEntity toEntity(MtkTracking model);

  List<MtkTrackingEntity> toEntities(List<MtkTracking> models);
}
