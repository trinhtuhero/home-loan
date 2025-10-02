package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import vn.com.msb.homeloan.core.entity.LoanItemCollateralDistributionEntity;
import vn.com.msb.homeloan.core.model.Collateral;
import vn.com.msb.homeloan.core.model.LoanItemCollateralDistribution;
import vn.com.msb.homeloan.core.model.mapper.CollateralMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanItemCollateralDistributionMapper;
import vn.com.msb.homeloan.core.repository.CollateralRepository;
import vn.com.msb.homeloan.core.repository.LoanItemCollateralDistributeRepository;
import vn.com.msb.homeloan.core.service.LoanItemCollateralDistributionService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class LoanItemCollateralDistributionServiceImpl implements
    LoanItemCollateralDistributionService {

  private final CollateralRepository collateralRepository;
  LoanItemCollateralDistributeRepository loanItemCollateralDistributeRepository;

  @Override
  public List<LoanItemCollateralDistribution> getByLoanId(String loanId) {
    return LoanItemCollateralDistributionMapper.INSTANCE.toModels(
        loanItemCollateralDistributeRepository.getByLoanApplicationId(loanId));
  }

  @Override
  public List<LoanItemCollateralDistribution> getByLoanIdAndType(String loanId, String type) {
    return LoanItemCollateralDistributionMapper.INSTANCE.toModels(
        loanItemCollateralDistributeRepository.getByLoanApplicationIdAndType(loanId, type));
  }

  @Override
  public List<LoanItemCollateralDistribution> getByLoanIdAndCollateralId(String loanId,
      String collateralId) {
    return LoanItemCollateralDistributionMapper.INSTANCE.toModels(
        loanItemCollateralDistributeRepository.getByLoanApplicationIdAndCollateralId(loanId,
            collateralId));
  }

  @Override
  public List<LoanItemCollateralDistribution> getCollateralAndDistributionByLoanIdAndType(
      String loanId, String type) {
    log.info("Start getCollateralAndDistributionByLoanIdAndType: {} and {}", loanId, type);
    List<LoanItemCollateralDistributionEntity> collateralDistributionEntities = loanItemCollateralDistributeRepository.getByLoanApplicationIdAndType(
        loanId, type);
    log.info("collateralDistributionEntities found: {}", collateralDistributionEntities);
    List<LoanItemCollateralDistribution> collateralDistributions = collateralDistributionEntities.stream()
        .map(
            loanItemCollateralDistributionEntity -> {
              LoanItemCollateralDistribution collateralDistribution = LoanItemCollateralDistributionMapper.INSTANCE.toModel(
                  loanItemCollateralDistributionEntity);
              log.info("Start getCollateral by collateralDistributionId: {}",
                  collateralDistribution.getCollateralId());
              Collateral collateral = CollateralMapper.INSTANCE.toModel(
                  collateralRepository.findById(collateralDistribution.getCollateralId())
                      .orElseThrow(() -> new EntityNotFoundException("Collateral not found by ID "
                          + collateralDistribution.getCollateralId())));
              collateralDistribution.setCollateral(collateral);
              log.info("Collateral found: {}", collateral);
              return collateralDistribution;
            }).collect(Collectors.toList());
    log.info("End getCollateralAndDistributionByLoanIdAndType: {}", collateralDistributions);
    return collateralDistributions;
  }

  @Override
  public List<LoanItemCollateralDistribution> saves(List<LoanItemCollateralDistribution> models,
      String type) {
    Assert.notNull(models, "Entities must not be null!");
    List<LoanItemCollateralDistribution> result = new ArrayList();
    Iterator<LoanItemCollateralDistribution> var3 = models.iterator();

    while (var3.hasNext()) {
      LoanItemCollateralDistribution entity = var3.next();
      entity.setType(type);
      result.add(save(entity));
    }

    return result;
  }

  @Override
  public LoanItemCollateralDistribution save(LoanItemCollateralDistribution model) {
    Assert.notNull(model, "Entity must not be null.");
    LoanItemCollateralDistributionEntity entity = loanItemCollateralDistributeRepository.save(
        LoanItemCollateralDistributionMapper.INSTANCE.toEntity(model));
    return LoanItemCollateralDistributionMapper.INSTANCE.toModel(entity);
  }

  @Override
  public void deleteAll(List<LoanItemCollateralDistribution> models) {
    loanItemCollateralDistributeRepository.deleteAll(
        LoanItemCollateralDistributionMapper.INSTANCE.toEntities(models));
  }

  @Override
  public void deleteAllByOverdraftId(String overdraftId) {
    loanItemCollateralDistributeRepository.deleteByLoanItemId(overdraftId);
  }
}
