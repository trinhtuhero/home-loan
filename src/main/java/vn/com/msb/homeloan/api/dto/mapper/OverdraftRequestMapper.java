package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.overdraft.OverdraftRequest;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;

@Mapper
public interface OverdraftRequestMapper {

  OverdraftRequestMapper INSTANCE = Mappers.getMapper(OverdraftRequestMapper.class);

  //request to model
  Overdraft toModel(OverdraftRequest request);
}
