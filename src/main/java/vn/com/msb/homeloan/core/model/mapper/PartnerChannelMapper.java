package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.PartnerChannelEntity;
import vn.com.msb.homeloan.core.model.PartnerChannel;

@Mapper
public interface PartnerChannelMapper {

  PartnerChannelMapper INSTANCE = Mappers.getMapper(PartnerChannelMapper.class);

  PartnerChannel toModel(PartnerChannelEntity entity);

  PartnerChannelEntity toEntity(PartnerChannel modal);

  List<PartnerChannel> toModels(List<PartnerChannelEntity> entities);

  List<PartnerChannelEntity> toEntities(List<PartnerChannel> models);
}
