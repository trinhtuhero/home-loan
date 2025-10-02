package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CRUCreditCardResponse;
import vn.com.msb.homeloan.core.model.CreditCard;

@Mapper
public interface CreditCardResponseMapper {

  CreditCardResponseMapper INSTANCE = Mappers.getMapper(CreditCardResponseMapper.class);

  CRUCreditCardResponse toDTO(CreditCard model);
}
