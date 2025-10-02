package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.CMSOtherIncomeResponse;
import vn.com.msb.homeloan.core.model.OtherIncome;

@Mapper
public interface CMSOtherIncomeResponseMapper {

  CMSOtherIncomeResponseMapper INSTANCE = Mappers.getMapper(CMSOtherIncomeResponseMapper.class);

  CMSOtherIncomeResponse toCmsResponse(OtherIncome response);

  List<CMSOtherIncomeResponse> toCmsResponses(List<OtherIncome> responses);
}
