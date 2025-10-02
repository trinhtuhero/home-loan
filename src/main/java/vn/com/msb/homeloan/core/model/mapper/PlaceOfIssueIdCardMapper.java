package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;
import vn.com.msb.homeloan.core.model.PlaceOfIssueIdCard;

@Mapper
public interface PlaceOfIssueIdCardMapper {

  PlaceOfIssueIdCardMapper INSTANCE = Mappers.getMapper(PlaceOfIssueIdCardMapper.class);

  PlaceOfIssueIdCard toModel(PlaceOfIssueIdCardEntity entity);

  PlaceOfIssueIdCardEntity toEntity(PlaceOfIssueIdCard modal);

  List<PlaceOfIssueIdCard> toModels(List<PlaceOfIssueIdCardEntity> entities);

  List<PlaceOfIssueIdCardEntity> toEntities(List<PlaceOfIssueIdCard> models);
}
