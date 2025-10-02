package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.LandTransactionRequest;
import vn.com.msb.homeloan.api.dto.response.LandTransactionResponse;
import vn.com.msb.homeloan.core.entity.LandTransactionEntity;

@Mapper
public interface LandTransactionMapper {

  LandTransactionMapper INSTANCE = Mappers.getMapper(LandTransactionMapper.class);

  List<LandTransactionEntity> toEntities(List<LandTransactionRequest> requests);

  LandTransactionEntity toEntity(LandTransactionRequest request);

  List<LandTransactionRequest> responseToRequest(List<LandTransactionResponse> responses);

  @Mapping(target = "uuid", ignore = true)
  void toUpdateEntity(@MappingTarget LandTransactionEntity entity,
      LandTransactionRequest request);

  List<LandTransactionResponse> toResponses(List<LandTransactionEntity> requests);

  LandTransactionResponse toResponse(LandTransactionEntity request);
}
