package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CreditworthinessItemEntity;
import vn.com.msb.homeloan.core.model.CreditworthinessItem;

@Mapper
public interface CreditworthinessItemMapper {

  CreditworthinessItemMapper INSTANCE = Mappers.getMapper(CreditworthinessItemMapper.class);

  CreditworthinessItem toModel(CreditworthinessItemEntity entity);

  CreditworthinessItemEntity toEntity(CreditworthinessItem model);

  List<CreditworthinessItem> toModels(List<CreditworthinessItemEntity> entities);

  List<CreditworthinessItemEntity> toEntities(List<CreditworthinessItem> models);
}
