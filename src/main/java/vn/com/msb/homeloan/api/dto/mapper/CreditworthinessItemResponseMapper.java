package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.CreditworthinessItemResponse;
import vn.com.msb.homeloan.core.model.CreditworthinessItem;

@Mapper
public interface CreditworthinessItemResponseMapper {

  CreditworthinessItemResponseMapper INSTANCE = Mappers.getMapper(
      CreditworthinessItemResponseMapper.class);

  CreditworthinessItemResponse toDTO(CreditworthinessItem model);

  List<CreditworthinessItemResponse> toDTOs(List<CreditworthinessItem> models);
}
