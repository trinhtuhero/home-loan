package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.AdviseCustomerEntity;
import vn.com.msb.homeloan.core.model.AdviseCustomer;

@Mapper
public interface CustomerAdviseMapper extends
    BaseMapper<AdviseCustomer, AdviseCustomerEntity> {

  CustomerAdviseMapper INSTANCE = Mappers.getMapper(CustomerAdviseMapper.class);
}
