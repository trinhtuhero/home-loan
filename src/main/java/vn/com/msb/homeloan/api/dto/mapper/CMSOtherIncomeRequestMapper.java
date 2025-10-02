package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSOtherIncomeRequest;
import vn.com.msb.homeloan.core.model.OtherIncome;

@Mapper
public interface CMSOtherIncomeRequestMapper {

  CMSOtherIncomeRequestMapper INSTANCE = Mappers.getMapper(CMSOtherIncomeRequestMapper.class);

  OtherIncome toModel(CMSOtherIncomeRequest request);

  List<OtherIncome> toModels(List<CMSOtherIncomeRequest> requests);
}
