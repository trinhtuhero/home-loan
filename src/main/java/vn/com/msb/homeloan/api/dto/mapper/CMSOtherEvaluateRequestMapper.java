package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSOtherEvaluateRequest;
import vn.com.msb.homeloan.core.model.OtherEvaluate;

@Mapper
public interface CMSOtherEvaluateRequestMapper {

  CMSOtherEvaluateRequestMapper INSTANCE = Mappers.getMapper(CMSOtherEvaluateRequestMapper.class);

  OtherEvaluate toModel(CMSOtherEvaluateRequest request);

  List<OtherEvaluate> toModels(List<CMSOtherEvaluateRequest> requests);
}
