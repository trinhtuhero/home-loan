package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CardPolicyEntity;
import vn.com.msb.homeloan.core.model.CardPolicy;

@Mapper
public interface CardPolicyMapper {

  CardPolicyMapper INSTANCE = Mappers.getMapper(CardPolicyMapper.class);

  CardPolicy toModel(CardPolicyEntity entity);

  CardPolicyEntity toEntity(CardPolicy modal);

  List<CardPolicy> toModels(List<CardPolicyEntity> entities);

  List<CardPolicyEntity> toEntities(List<CardPolicy> models);
}
