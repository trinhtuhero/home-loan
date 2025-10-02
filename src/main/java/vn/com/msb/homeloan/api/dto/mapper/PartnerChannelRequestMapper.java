package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.PartnerChannelRequest;
import vn.com.msb.homeloan.core.model.PartnerChannel;

@Mapper
public interface PartnerChannelRequestMapper {

  PartnerChannelRequestMapper INSTANCE = Mappers.getMapper(PartnerChannelRequestMapper.class);

  PartnerChannel toModel(PartnerChannelRequest request);
}
