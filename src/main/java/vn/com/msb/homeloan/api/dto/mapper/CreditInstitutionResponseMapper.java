package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CreditInstitutionResponse;
import vn.com.msb.homeloan.core.model.CreditInstitution;

@Mapper
public interface CreditInstitutionResponseMapper {

  CreditInstitutionResponseMapper INSTANCE = Mappers.getMapper(
      CreditInstitutionResponseMapper.class);

  CreditInstitutionResponse toDto(CreditInstitution model);

  List<CreditInstitutionResponse> toDtos(List<CreditInstitution> models);
}
