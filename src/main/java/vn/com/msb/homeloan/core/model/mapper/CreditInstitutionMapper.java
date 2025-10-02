package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CreditInstitutionEntity;
import vn.com.msb.homeloan.core.model.CreditInstitution;

@Mapper
public interface CreditInstitutionMapper {

  CreditInstitutionMapper INSTANCE = Mappers.getMapper(CreditInstitutionMapper.class);

  CreditInstitution toModel(CreditInstitutionEntity entity);

  CreditInstitutionEntity toEntity(CreditInstitution model);

  List<CreditInstitution> toModels(List<CreditInstitutionEntity> entities);

  List<CreditInstitutionEntity> toEntities(List<CreditInstitution> models);
}
