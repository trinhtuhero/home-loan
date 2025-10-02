package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.model.LoginProfileInfo;

@Mapper
public abstract class LoginProfileInfoMapper {

  public static final LoginProfileInfoMapper INSTANCE = Mappers.getMapper(
      LoginProfileInfoMapper.class);

  public abstract LoginProfileInfo toModel(ProfileEntity entity);
}
