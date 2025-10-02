package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.CreditAppraisalResponse;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.CreditRatioResponse;
import vn.com.msb.homeloan.core.model.CreditAppraisal;

@Mapper
public interface CreditAppraisalResponseMapper {

  CreditAppraisalResponseMapper INSTANCE = Mappers.getMapper(CreditAppraisalResponseMapper.class);

  CreditAppraisalResponse toDTO(CreditAppraisal model);

  CreditRatioResponse toCreditRatioResponse(CreditAppraisal model);
}
