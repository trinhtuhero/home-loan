package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSCreditCardResponse;
import vn.com.msb.homeloan.core.model.CreditCard;

@Mapper
public interface CMSCreditCardResponseMapper {

  CMSCreditCardResponseMapper INSTANCE = Mappers.getMapper(CMSCreditCardResponseMapper.class);

  CMSCreditCardResponse toCmsResponse(CreditCard model);

  List<CMSCreditCardResponse> toCmsResponses(List<CreditCard> responses);
}
