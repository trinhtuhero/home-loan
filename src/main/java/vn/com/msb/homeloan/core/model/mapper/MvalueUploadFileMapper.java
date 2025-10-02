package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.LoanUploadFileUpdateStatusRequest;
import vn.com.msb.homeloan.api.dto.request.MvalueUploadFileUpdateStatusRequest;
import vn.com.msb.homeloan.core.entity.MvalueUploadFilesEntity;
import vn.com.msb.homeloan.core.model.MvalueUploadFile;

import java.util.List;

@Mapper
public interface MvalueUploadFileMapper {

  MvalueUploadFileMapper INSTANCE = Mappers.getMapper(MvalueUploadFileMapper.class);

  MvalueUploadFile toModel(MvalueUploadFilesEntity entity);

  List<MvalueUploadFile> toModels(List<MvalueUploadFilesEntity> entities);

  @Mapping(source = "id", target = "id")
  List<MvalueUploadFile> requestToDto(List<MvalueUploadFileUpdateStatusRequest> request);



}
