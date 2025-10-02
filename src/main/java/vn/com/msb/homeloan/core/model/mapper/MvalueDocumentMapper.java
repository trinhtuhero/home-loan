package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.DocumentMappingEntity;
import vn.com.msb.homeloan.core.model.MvalueDocument;

import java.util.List;

@Mapper
public interface MvalueDocumentMapper {

  MvalueDocumentMapper INSTANCE = Mappers.getMapper(MvalueDocumentMapper.class);

  List<MvalueDocument> toModels(List<DocumentMappingEntity> entities);
}
