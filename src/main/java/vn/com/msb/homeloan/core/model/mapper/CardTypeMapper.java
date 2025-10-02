package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CardTypeEntity;
import vn.com.msb.homeloan.core.model.CardType;

@Mapper
public interface CardTypeMapper {

  CardTypeMapper INSTANCE = Mappers.getMapper(CardTypeMapper.class);

  CardType toModel(CardTypeEntity entity);

  CardTypeEntity toEntity(CardType modal);

  List<CardType> toModels(List<CardTypeEntity> entities);

  List<CardTypeEntity> toEntities(List<CardType> models);
}
