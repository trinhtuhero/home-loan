package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CreditCardEntity;
import vn.com.msb.homeloan.core.model.CreditCard;

@Mapper
public interface CreditCardMapper {

  CreditCardMapper INSTANCE = Mappers.getMapper(CreditCardMapper.class);

  CreditCard toModels(CreditCardEntity entities);

}
