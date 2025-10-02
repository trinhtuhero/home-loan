package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.ContactPersonEntity;
import vn.com.msb.homeloan.core.model.ContactPerson;

@Mapper
public abstract class ContactPersonInfoMapper {

  public static final ContactPersonInfoMapper INSTANCE = Mappers.getMapper(
      ContactPersonInfoMapper.class);

  public abstract ContactPerson toModel(ContactPersonEntity entity);

  public abstract List<ContactPerson> toModels(List<ContactPersonEntity> entities);

  public abstract ContactPersonEntity toEntity(ContactPerson model);
}
