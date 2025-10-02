package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.response.LdpSearchResponse;
import vn.com.msb.homeloan.core.model.LoanApplication;

@Mapper
public interface LdpSearchResponseMapper {

  LdpSearchResponseMapper INSTANCE = Mappers.getMapper(LdpSearchResponseMapper.class);

  LdpSearchResponse toResponse(LoanApplication loanApplication);

  List<LdpSearchResponse> toResponses(List<LoanApplication> loanApplications);
}
