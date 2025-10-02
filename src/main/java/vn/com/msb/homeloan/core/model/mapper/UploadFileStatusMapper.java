package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.UploadFileStatusEntity;
import vn.com.msb.homeloan.core.model.UploadFileStatus;

@Mapper
public abstract class UploadFileStatusMapper {

  public static final UploadFileStatusMapper INSTANCE = Mappers.getMapper(
      UploadFileStatusMapper.class);

  public abstract UploadFileStatus toModel(UploadFileStatusEntity entity);

  public abstract List<UploadFileStatus> toModels(List<UploadFileStatusEntity> entities);

  public abstract UploadFileStatusEntity toEntity(UploadFileStatus model);

  public abstract List<UploadFileStatusEntity> toEntitys(List<UploadFileStatus> entities);
}
