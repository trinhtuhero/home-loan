package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CRUCreditCardRequest;
import vn.com.msb.homeloan.core.model.CreditCard;

@Mapper
public interface CreditCardRequestMapper {

  CreditCardRequestMapper INSTANCE = Mappers.getMapper(CreditCardRequestMapper.class);

  //request to model
  CreditCard toModel(CRUCreditCardRequest request);
}
