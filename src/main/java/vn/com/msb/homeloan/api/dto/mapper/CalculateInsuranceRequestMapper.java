package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.insurance.CJ4CalculateInsuranceRequest;

@Mapper
public interface CalculateInsuranceRequestMapper {

  CalculateInsuranceRequestMapper INSTANCE = Mappers.getMapper(
      CalculateInsuranceRequestMapper.class);

  //request to model
  vn.com.msb.homeloan.core.model.request.integration.cj4.CalculateInsuranceRequest toModel(
      CJ4CalculateInsuranceRequest request);
}
