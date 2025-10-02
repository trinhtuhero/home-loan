package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.model.CustomerInfo;

@Mapper
public abstract class CustomerInfoMapper {

  public static final CustomerInfoMapper INSTANCE = Mappers.getMapper(CustomerInfoMapper.class);

  public abstract CustomerInfo toModel(LoanApplicationEntity entity);

  public abstract LoanApplicationEntity toEntity(CustomerInfo model);
}
