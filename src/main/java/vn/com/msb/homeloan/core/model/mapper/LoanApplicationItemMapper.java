package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.LoanApplicationItemEntity;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;

@Mapper
public abstract class LoanApplicationItemMapper {

  public static final LoanApplicationItemMapper INSTANCE = Mappers.getMapper(
      LoanApplicationItemMapper.class);

  public abstract LoanApplicationItem toModel(LoanApplicationItemEntity entity);

  public abstract List<LoanApplicationItem> toModels(List<LoanApplicationItemEntity> entities);

  public abstract LoanApplicationItemEntity toEntity(LoanApplicationItem model);

  public abstract List<LoanApplicationItemEntity> toEntities(List<LoanApplicationItem> models);
}
