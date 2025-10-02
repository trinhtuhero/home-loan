package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CreditAppraisalEntity;
import vn.com.msb.homeloan.core.model.CreditAppraisal;

@Mapper
public interface CreditAppraisalMapper {

  CreditAppraisalMapper INSTANCE = Mappers.getMapper(CreditAppraisalMapper.class);

  CreditAppraisal toModel(CreditAppraisalEntity entity);

  CreditAppraisalEntity toEntity(CreditAppraisal model);

  List<CreditAppraisal> toModels(List<CreditAppraisalEntity> entities);

  List<CreditAppraisalEntity> toEntities(List<CreditAppraisal> models);
}
