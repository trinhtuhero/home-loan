package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.model.Profile;

@Mapper
public abstract class ProfileMapper {

  public static final ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

  public abstract Profile toModel(ProfileEntity entity);

  public abstract ProfileEntity toEntity(Profile profile);
}
