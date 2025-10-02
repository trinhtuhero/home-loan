package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSCreditCardRequest;
import vn.com.msb.homeloan.core.model.CreditCard;

@Mapper
public interface CMSCreditCardRequestMapper {

  CMSCreditCardRequestMapper INSTANCE = Mappers.getMapper(CMSCreditCardRequestMapper.class);

  //request to model
  CreditCard toModel(CMSCreditCardRequest request);
}
