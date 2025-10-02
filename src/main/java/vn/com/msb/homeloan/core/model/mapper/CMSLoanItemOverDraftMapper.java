package vn.com.msb.homeloan.core.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;
import vn.com.msb.homeloan.core.model.LoanItemOverDraftDto;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CMSLoanItemOverDraftMapper {

  LoanItemOverDraftDto mapLoanItemToDto(LoanApplicationItem item);

  LoanItemOverDraftDto mapOverDraftToDto(Overdraft overdraft);
}
