package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CreateUploadFileCommentRequest;
import vn.com.msb.homeloan.api.dto.request.UpdateUploadFileCommentRequest;
import vn.com.msb.homeloan.core.model.UploadFileComment;

@Mapper
public interface UploadFileCommentMapper {

  UploadFileCommentMapper INSTANCE = Mappers.getMapper(UploadFileCommentMapper.class);

  //request to model
  UploadFileComment toModel(CreateUploadFileCommentRequest request);

  //request to model
  UploadFileComment toModel(UpdateUploadFileCommentRequest request);
}
