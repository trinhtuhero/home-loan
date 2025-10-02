package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.MtkTrackingRequest;
import vn.com.msb.homeloan.core.model.MtkTracking;

@Mapper
public interface MtkTrackingRequestMapper {

  MtkTrackingRequestMapper INSTANCE = Mappers.getMapper(MtkTrackingRequestMapper.class);

  MtkTracking toModel(MtkTrackingRequest dto);
}
