package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.LoanStatusChangeEntity;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.model.LoanStatusChange;

@Mapper
public abstract class LoanStatusChangeMapper {

  public static final LoanStatusChangeMapper INSTANCE = Mappers.getMapper(
      LoanStatusChangeMapper.class);

  public abstract LoanStatusChange toModel(LoanStatusChangeEntity entity);

  public abstract LoanStatusChangeEntity toEntity(LoanStatusChange model);

  public abstract ProfileEntity toProfileEntity(LoanStatusChange model);

}
