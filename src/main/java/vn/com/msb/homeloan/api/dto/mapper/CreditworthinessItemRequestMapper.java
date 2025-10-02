package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.creditappraisal.CreditworthinessItemRequest;
import vn.com.msb.homeloan.core.model.CreditworthinessItem;

@Mapper
public interface CreditworthinessItemRequestMapper {

  CreditworthinessItemRequestMapper INSTANCE = Mappers.getMapper(
      CreditworthinessItemRequestMapper.class);

  CreditworthinessItem toModel(CreditworthinessItemRequest request);

  List<CreditworthinessItem> toModels(List<CreditworthinessItemRequest> requests);
}
