package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CreateUploadFileStatusRequest;
import vn.com.msb.homeloan.core.model.UploadFileStatus;

@Mapper
public interface UploadFileStatusMapper {

  UploadFileStatusMapper INSTANCE = Mappers.getMapper(UploadFileStatusMapper.class);

  //request to model
  UploadFileStatus toModel(CreateUploadFileStatusRequest request);
}
