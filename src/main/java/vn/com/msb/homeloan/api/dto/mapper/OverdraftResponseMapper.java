package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.overdraft.OverdraftResponse;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;

@Mapper
public interface OverdraftResponseMapper {

  OverdraftResponseMapper INSTANCE = Mappers.getMapper(OverdraftResponseMapper.class);

  // model to response
  OverdraftResponse toLdpResponse(Overdraft model);
}
