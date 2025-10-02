package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CRUBusinessIncomeRequest;
import vn.com.msb.homeloan.api.dto.response.CRUBusinessIncomeResponse;
import vn.com.msb.homeloan.core.model.BusinessIncome;

@Mapper
public interface BusinessIncomeMapper {

  BusinessIncomeMapper INSTANCE = Mappers.getMapper(BusinessIncomeMapper.class);

  //request to model
  BusinessIncome toModel(CRUBusinessIncomeRequest request);

  //model to response
  CRUBusinessIncomeResponse toDTO(BusinessIncome model);

  List<CRUBusinessIncomeResponse> toDTOs(List<BusinessIncome> models);
}
