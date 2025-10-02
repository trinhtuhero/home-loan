package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSOtherEvaluateResponse;
import vn.com.msb.homeloan.core.model.OtherEvaluate;

@Mapper
public interface CMSOtherEvaluateResponseMapper {

  CMSOtherEvaluateResponseMapper INSTANCE = Mappers.getMapper(CMSOtherEvaluateResponseMapper.class);

  CMSOtherEvaluateResponse toCmsResponse(OtherEvaluate response);

  List<CMSOtherEvaluateResponse> toCmsResponses(List<OtherEvaluate> responses);
}
