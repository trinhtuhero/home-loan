package vn.com.msb.homeloan.api.dto.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.CMSLoanApplicationItemRequest;
import vn.com.msb.homeloan.api.dto.request.LoanApplicationItemRequest;
import vn.com.msb.homeloan.core.constant.DisbursementMethodEnum;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;

@Mapper
public interface LoanApplicationItemRequestMapper {

  LoanApplicationItemRequestMapper INSTANCE = Mappers.getMapper(
      LoanApplicationItemRequestMapper.class);

  @Mapping(source = "disbursementMethod", target = "disbursementMethod", qualifiedByName = "setDisbursementMethodOfModel")
  @Mapping(source = "disbursementMethod", target = "disbursementMethodOther", qualifiedByName = "setDisbursementMethodOtherOfModel")
  LoanApplicationItem toCmsModel(CMSLoanApplicationItemRequest request);

  LoanApplicationItem toLdpModel(LoanApplicationItemRequest request);

  List<LoanApplicationItem> toCmsModels(List<CMSLoanApplicationItemRequest> request);

  List<LoanApplicationItem> toLdpModels(List<LoanApplicationItemRequest> request);

  // request to model
  @Named("setDisbursementMethodOfModel")
  default DisbursementMethodEnum setDisbursementMethodOfModel(String disbursementMethod) {
    if (DisbursementMethodEnum.toMapExcludeOther.containsKey(disbursementMethod)) {
      return DisbursementMethodEnum.toMapExcludeOther.get(disbursementMethod);
    } else {
      return DisbursementMethodEnum.OTHER;
    }
  }

  @Named("setDisbursementMethodOtherOfModel")
  default String setDisbursementMethodOtherOfModel(String disbursementMethod) {
    if (!DisbursementMethodEnum.toMapExcludeOther.containsKey(disbursementMethod)) {
      return disbursementMethod;
    } else {
      return null;
    }
  }
}
