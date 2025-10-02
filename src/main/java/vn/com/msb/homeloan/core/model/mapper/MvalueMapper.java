package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.MvalueEntity;
import vn.com.msb.homeloan.core.model.Mvalue;
import vn.com.msb.homeloan.core.model.request.mvalue.CMSCreateProfileRequest;
import vn.com.msb.homeloan.core.model.request.mvalue.CreateProfileRequest;

@Mapper
public interface MvalueMapper {

  MvalueMapper INSTANCE = Mappers.getMapper(MvalueMapper.class);

  Mvalue toModel(MvalueEntity entity);

  CreateProfileRequest toClientRequest(CMSCreateProfileRequest request);
}
