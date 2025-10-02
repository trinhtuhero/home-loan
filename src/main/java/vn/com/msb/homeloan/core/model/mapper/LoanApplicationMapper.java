package vn.com.msb.homeloan.core.model.mapper;

import io.jsonwebtoken.lang.Objects;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.api.dto.request.RMUpdateCustomerInfoRequest;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.model.LoanApplication;

@Mapper
public abstract class LoanApplicationMapper {

  public static final LoanApplicationMapper INSTANCE = Mappers.getMapper(
      LoanApplicationMapper.class);

  // @Mapping(source = "selectedIncomes", target = "selectedIncomes", qualifiedByName = "selectedIncomesFromString2Array")
  public abstract LoanApplication toModel(LoanApplicationEntity entity);

  public abstract List<LoanApplication> toModels(List<LoanApplicationEntity> entities);

  // @Mapping(source = "selectedIncomes", target = "selectedIncomes", qualifiedByName = "selectedIncomesArray2String")
  public abstract LoanApplicationEntity toEntity(LoanApplication model);

  public abstract ProfileEntity toProfileEntity(LoanApplication model);

  @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyyMMdd")
  @Mapping(source = "issuedOn", target = "issuedOn", dateFormat = "yyyyMMdd")
  public abstract LoanApplication toLoanApplication(RMUpdateCustomerInfoRequest model);

  // entity to model
  @Named("selectedIncomesFromString2Array")
  public static String[] selectedIncomesFromString2Array(String selectedIncomes) {
    if (StringUtils.isEmpty(selectedIncomes)) {
      return new String[0];
    }
    return selectedIncomes.split(", ");
  }

  // model to entity
  @Named("selectedIncomesArray2String")
  public static String selectedIncomesArray2String(String[] selectedIncomes) {
    if (Objects.isEmpty(selectedIncomes)) {
      return null;
    }
    return String.join(", ", selectedIncomes);
  }
}
