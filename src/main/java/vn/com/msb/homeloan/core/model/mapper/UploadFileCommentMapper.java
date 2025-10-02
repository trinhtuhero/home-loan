package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.UploadFileCommentEntity;
import vn.com.msb.homeloan.core.model.UploadFileComment;

@Mapper
public abstract class UploadFileCommentMapper {

  public static final UploadFileCommentMapper INSTANCE = Mappers.getMapper(
      UploadFileCommentMapper.class);

  public abstract UploadFileComment toModel(UploadFileCommentEntity entity);

  public abstract List<UploadFileComment> toModels(List<UploadFileCommentEntity> entities);

  public abstract UploadFileCommentEntity toEntity(UploadFileComment model);

  public abstract List<UploadFileCommentEntity> toEntitys(List<UploadFileComment> entities);
}
