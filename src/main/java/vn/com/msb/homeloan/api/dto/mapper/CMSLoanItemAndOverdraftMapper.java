package vn.com.msb.homeloan.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.overdraft.CMSLoanItemAndOverdraftRequest;
import vn.com.msb.homeloan.core.model.LoanItemAndOverdraft;

@Mapper
public interface CMSLoanItemAndOverdraftMapper {

  CMSLoanItemAndOverdraftMapper INSTANCE = Mappers.getMapper(CMSLoanItemAndOverdraftMapper.class);

  LoanItemAndOverdraft toModel(CMSLoanItemAndOverdraftRequest request);
}
