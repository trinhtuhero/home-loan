package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.LoanAdviseCustomerEntity;
import vn.com.msb.homeloan.core.model.LoanAdviseCustomer;

@Mapper
public abstract class LoanAdviseCustomerMapper {

  public static final LoanAdviseCustomerMapper INSTANCE = Mappers.getMapper(
      LoanAdviseCustomerMapper.class);

  public abstract LoanAdviseCustomer toModel(LoanAdviseCustomerEntity entity);

  public abstract LoanAdviseCustomerEntity toEntity(LoanAdviseCustomer model);

}
