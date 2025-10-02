package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.CollateralOwnerEntity;
import vn.com.msb.homeloan.core.model.CollateralOwner;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.MarriedPerson;

@Mapper
public interface CollateralOwnerMapper {

  CollateralOwnerMapper INSTANCE = Mappers.getMapper(CollateralOwnerMapper.class);

  CollateralOwner fromLoanApp(LoanApplication loanApplication);

  CollateralOwner fromMarriedPerson(MarriedPerson marriedPerson);

  CollateralOwner toModel(CollateralOwnerEntity entity);

  CollateralOwnerEntity toEntity(CollateralOwner model);

  List<CollateralOwner> toModels(List<CollateralOwnerEntity> entities);

  List<CollateralOwnerEntity> toEntities(List<CollateralOwner> models);
}
