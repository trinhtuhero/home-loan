package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.LoanUploadFileEntity;
import vn.com.msb.homeloan.core.model.LoanUploadFile;

@Mapper
public interface LoanUploadFileMapper {

  LoanUploadFileMapper INSTANCE = Mappers.getMapper(LoanUploadFileMapper.class);

  LoanUploadFile toModel(LoanUploadFileEntity entity);

  LoanUploadFileEntity toEntity(LoanUploadFile modal);

  List<LoanUploadFile> toModels(List<LoanUploadFileEntity> entities);

  List<LoanUploadFileEntity> toEntities(List<LoanUploadFile> models);
}
