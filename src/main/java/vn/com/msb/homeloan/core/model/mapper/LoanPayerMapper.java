package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.model.LoanPayer;

@Mapper
public abstract class LoanPayerMapper {

  public static final LoanPayerMapper INSTANCE = Mappers.getMapper(LoanPayerMapper.class);

  public abstract LoanPayer toModel(LoanPayerEntity entity);

  public abstract LoanPayerEntity toEntity(LoanPayer model);

  public abstract List<LoanPayer> toModels(List<LoanPayerEntity> entities);

  public abstract List<LoanPayerEntity> toEntities(List<LoanPayer> models);
}
