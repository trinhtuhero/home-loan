package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.LoanUploadFileUpdateStatusRequest;
import vn.com.msb.homeloan.core.model.LoanUploadFile;

@Mapper
public interface LoanUploadFileUpdateStatusRequestMapper {

  LoanUploadFileUpdateStatusRequestMapper INSTANCE = Mappers.getMapper(
      LoanUploadFileUpdateStatusRequestMapper.class);

  @Mapping(source = "loanUploadFileId", target = "uuid")
  LoanUploadFile toModel(LoanUploadFileUpdateStatusRequest request);

  @Mapping(source = "loanUploadFileId", target = "uuid")
  List<LoanUploadFile> toModels(List<LoanUploadFileUpdateStatusRequest> request);
}
