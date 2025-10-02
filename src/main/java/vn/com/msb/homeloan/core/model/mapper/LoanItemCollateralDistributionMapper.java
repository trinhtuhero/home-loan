package vn.com.msb.homeloan.core.model.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.com.msb.homeloan.core.entity.LoanItemCollateralDistributionEntity;
import vn.com.msb.homeloan.core.model.LoanItemCollateralDistribution;

@Mapper
public interface LoanItemCollateralDistributionMapper {

  LoanItemCollateralDistributionMapper INSTANCE = Mappers.getMapper(
      LoanItemCollateralDistributionMapper.class);

  LoanItemCollateralDistribution toModel(LoanItemCollateralDistributionEntity entity);

  List<LoanItemCollateralDistribution> toModels(
      List<LoanItemCollateralDistributionEntity> entities);

  LoanItemCollateralDistributionEntity toEntity(LoanItemCollateralDistribution model);

  List<LoanItemCollateralDistributionEntity> toEntities(
      List<LoanItemCollateralDistribution> models);
}
