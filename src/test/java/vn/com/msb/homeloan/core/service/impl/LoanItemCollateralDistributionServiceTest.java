package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.LoanItemCollateralDistributionEntity;
import vn.com.msb.homeloan.core.model.LoanItemCollateralDistribution;
import vn.com.msb.homeloan.core.model.mapper.LoanItemCollateralDistributionMapper;
import vn.com.msb.homeloan.core.repository.CollateralRepository;
import vn.com.msb.homeloan.core.repository.LoanItemCollateralDistributeRepository;
import vn.com.msb.homeloan.core.service.LoanItemCollateralDistributionService;

@ExtendWith(MockitoExtension.class)
public class LoanItemCollateralDistributionServiceTest {

  LoanItemCollateralDistributionService loanItemCollateralDistributionService;

  @Mock
  LoanItemCollateralDistributeRepository loanItemCollateralDistributeRepository;

  @Mock
  CollateralRepository collateralRepository;

  @BeforeEach
  void setUp() {
    this.loanItemCollateralDistributionService = new LoanItemCollateralDistributionServiceImpl(
        collateralRepository, loanItemCollateralDistributeRepository
    );
  }

  @Test
  void givenValidInput_ThenGetByLoanId_shouldReturnResult() {

    String loanId = "test";

    List<LoanItemCollateralDistributionEntity> entities = new ArrayList<>();

    LoanItemCollateralDistributionEntity entity = new LoanItemCollateralDistributionEntity();

    entities.add(entity);

    doReturn(entities).when(loanItemCollateralDistributeRepository).getByLoanApplicationId(loanId);

    List<LoanItemCollateralDistribution> result = loanItemCollateralDistributionService.getByLoanId(
        loanId);

    assertEquals(1, result.size());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnResult() {

    LoanItemCollateralDistribution model = new LoanItemCollateralDistribution();

    LoanItemCollateralDistributionEntity saveEntity = new LoanItemCollateralDistributionEntity();

    doReturn(saveEntity).when(loanItemCollateralDistributeRepository)
        .save(LoanItemCollateralDistributionMapper.INSTANCE.toEntity(model));

    LoanItemCollateralDistribution saveModel = loanItemCollateralDistributionService.save(model);

    assertEquals(saveModel.getUuid(), model.getUuid());
  }

  @Test
  void givenValidInput_ThenSaves_shouldReturnResult() {

    LoanItemCollateralDistribution model = new LoanItemCollateralDistribution();
    model.setType("KHOAN_VAY");

    List<LoanItemCollateralDistribution> models = new ArrayList<>();
    models.add(model);

    List<LoanItemCollateralDistribution> tempList = new ArrayList<>();
    for (LoanItemCollateralDistribution item : models) {
      LoanItemCollateralDistributionEntity entity = new LoanItemCollateralDistributionEntity();

      doReturn(entity).when(loanItemCollateralDistributeRepository)
          .save(LoanItemCollateralDistributionMapper.INSTANCE.toEntity(item));

      tempList.add(LoanItemCollateralDistributionMapper.INSTANCE.toModel(entity));
    }

    List<LoanItemCollateralDistribution> result = loanItemCollateralDistributionService.saves(
        models, "KHOAN_VAY");

    assertTrue(result.size() == tempList.size());
  }

  @Test
  void givenValidInput_ThenDeleteAll_shouldSuccess() {

    List<LoanItemCollateralDistribution> models = new ArrayList<>();

    LoanItemCollateralDistribution model = new LoanItemCollateralDistribution();

    models.add(model);

    doNothing().when(loanItemCollateralDistributeRepository)
        .deleteAll(LoanItemCollateralDistributionMapper.INSTANCE.toEntities(models));

    loanItemCollateralDistributionService.deleteAll(models);
  }
}
