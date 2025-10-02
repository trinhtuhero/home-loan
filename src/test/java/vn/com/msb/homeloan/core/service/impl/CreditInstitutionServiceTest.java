package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.entity.CreditInstitutionEntity;
import vn.com.msb.homeloan.core.model.CreditInstitution;
import vn.com.msb.homeloan.core.repository.CreditInstitutionRepository;
import vn.com.msb.homeloan.core.service.CreditInstitutionService;

@ExtendWith(MockitoExtension.class)
class CreditInstitutionServiceTest {

  CreditInstitutionService creditInstitutionService;

  @Mock
  CreditInstitutionRepository creditInstitutionRepository;

  @BeforeEach
  void setUp() {
    this.creditInstitutionService = new CreditInstitutionServiceImpl(creditInstitutionRepository);
  }

  @Test
  void givenValidInput_ThenGetAll_shouldReturnSuccess() {
    List<CreditInstitutionEntity> creditInstitutionEntities = new ArrayList<>();
    CreditInstitutionEntity creditInstitutionEntity = CreditInstitutionEntity.builder()
        .build();
    creditInstitutionEntities.add(creditInstitutionEntity);
    doReturn(creditInstitutionEntities).when(creditInstitutionRepository).findAll();

    List<CreditInstitution> results = creditInstitutionService.getAll();

    assertTrue(results.size() == 1);
  }

  @Test
  void givenValidInput_ThenGetByCodes_shouldReturnSuccess() {
    List<CreditInstitutionEntity> creditInstitutionEntities = new ArrayList<>();
    CreditInstitutionEntity creditInstitutionEntity = CreditInstitutionEntity.builder()
        .code("code")
        .name("name")
        .build();
    creditInstitutionEntities.add(creditInstitutionEntity);
    doReturn(creditInstitutionEntities).when(creditInstitutionRepository).findByCodes(any());

    Map<String, CreditInstitutionEntity> results = creditInstitutionService.getMapByCodes(any());

    assertTrue(results.size() == 1);
  }

  @Test
  void givenValidInput_ThenGetByCodes_shouldReturnEmpty() {
    List<CreditInstitutionEntity> creditInstitutionEntities = new ArrayList<>();
    doReturn(creditInstitutionEntities).when(creditInstitutionRepository).findByCodes(any());

    Map<String, CreditInstitutionEntity> results = creditInstitutionService.getMapByCodes(any());

    assertTrue(results.size() == 0);
  }
}