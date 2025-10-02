package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CRUOtherIncomeRequest;
import vn.com.msb.homeloan.api.dto.response.CRUOtherIncomeResponse;
import vn.com.msb.homeloan.core.model.OtherIncome;

@Mapper
public interface OtherIncomeMapper {

  OtherIncomeMapper INSTANCE = Mappers.getMapper(OtherIncomeMapper.class);

  //request to model
  OtherIncome toModel(CRUOtherIncomeRequest request);

  //model to response
  CRUOtherIncomeResponse toDTO(OtherIncome model);

  List<CRUOtherIncomeResponse> toDTOs(List<OtherIncome> models);
}
